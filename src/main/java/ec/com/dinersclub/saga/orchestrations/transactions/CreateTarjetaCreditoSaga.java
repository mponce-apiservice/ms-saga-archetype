package ec.com.dinersclub.saga.orchestrations.transactions;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.saga.InMemorySagaService;
import org.apache.camel.model.SagaPropagation;

import ec.com.dinersclub.saga.services.ITarjetaCreditoService;
import ec.com.dinersclub.saga.services.models.TarjetaCredito;

@ApplicationScoped
public class CreateTarjetaCreditoSaga  extends RouteBuilder {
	
	@Inject
	ITarjetaCreditoService tarjetaCreditoService;

	@Override
	public void configure() throws Exception {
		
		getContext().addService(new InMemorySagaService(), true);
		
		from("direct:tarjeta")
			.saga().propagation(SagaPropagation.REQUIRES_NEW)
            .to("direct:newTarjetaCredito")
            .to("direct:updateTarjetaCredito");

		from("direct:newTarjetaCredito")
		  	.saga()
		  	.propagation(SagaPropagation.MANDATORY)
		  	.compensation("direct:cancelTarjetaCredito")
		    .transform()
		    .body(TarjetaCredito.class)
		    .bean(tarjetaCreditoService,"createTarjetaCredito")
		    .log("TarjetaCredito ${body} created");
		
		from("direct:updateTarjetaCredito")
		  	.saga()
		  	.propagation(SagaPropagation.MANDATORY)
		  	.compensation("direct:cancelTarjetaCredito")
		    .transform()
		    .body(TarjetaCredito.class)
		    .bean(tarjetaCreditoService,"updateTarjetaCredito")
		    .log("TarjetaCredito ${body} update");
		
		from("direct:cancelTarjetaCredito")
			.transform()
			.body(TarjetaCredito.class)
			.bean(tarjetaCreditoService,"removeTarjetaCredito")
		    .log("TarjetaCredito ${body} cancelled");
		
	}

}
