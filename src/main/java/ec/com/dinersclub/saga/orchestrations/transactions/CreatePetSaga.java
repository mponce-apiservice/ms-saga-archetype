package ec.com.dinersclub.saga.orchestrations.transactions;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.SagaPropagation;
import org.apache.camel.saga.InMemorySagaService;

import ec.com.dinersclub.saga.services.IPetstoreService;
import ec.com.dinersclub.saga.services.models.Petstore;

@ApplicationScoped
public class CreatePetSaga  extends RouteBuilder {
	
	@Inject
	IPetstoreService petstoreService;

	@Override
	public void configure() throws Exception {
		
		getContext().addService(new InMemorySagaService(), true);
		
		from("direct:pet")
			.saga().propagation(SagaPropagation.REQUIRES_NEW)
            .to("direct:newPet")
            .to("direct:updatePet");

		from("direct:newPet")
		  	.saga()
		  	.propagation(SagaPropagation.MANDATORY)
		  	.compensation("direct:cancelPet")
		    .transform()
		    .body(Petstore.class)
		    .bean(petstoreService,"createPet")
		    .log("Pet ${body} created");
		
		from("direct:updatePet")
		  	.saga()
		  	.propagation(SagaPropagation.MANDATORY)
		  	.compensation("direct:cancelPet")
		    .transform()
		    .body(Petstore.class)
		    .bean(petstoreService,"updatePet")
		    .log("Pet ${body} update");
		
		from("direct:cancelPet")
			.transform()
			.body(Petstore.class)
			.bean(petstoreService,"removePet")
		    .log("Pet ${body} cancelled");
		
	}

}
