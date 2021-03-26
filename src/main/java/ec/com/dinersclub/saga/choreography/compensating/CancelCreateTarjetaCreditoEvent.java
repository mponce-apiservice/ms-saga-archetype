package ec.com.dinersclub.saga.choreography.compensating;

import java.util.Set;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.eclipse.microprofile.reactive.messaging.Message;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import ec.com.dinersclub.saga.services.TarjetaCreditoService;
import ec.com.dinersclub.saga.services.models.TarjetaCredito;
import ec.com.dinersclub.saga.services.models.TarjetaCreditoDelete;
import io.vertx.core.json.JsonObject;

@ApplicationScoped
public class CancelCreateTarjetaCreditoEvent implements ICancelCreateTarjetaCreditoEvent {
	
	@Inject
    @RestClient
	TarjetaCreditoService tarjetaCreditoService;
	
	@Inject @Channel("cancelTarjeta") Emitter<TarjetaCredito> retry;
	
	//@Incoming("cancelTarjeta")
    //@Acknowledgment(Acknowledgment.Strategy.PRE_PROCESSING)
	public void compensatingCreateTarjetaCredito(JsonObject jsonObject) {
		
		TarjetaCredito tarjetaCredito = new TarjetaCredito(jsonObject);
		Set<TarjetaCreditoDelete> response = tarjetaCreditoService.deleteByTarjetaCreditoId(tarjetaCredito.id);
		if(response.isEmpty()) {
			this.generateEventHandler(tarjetaCredito);
		}
		
	}
	
    public void generateEventHandler(TarjetaCredito tarjetaCredito) {
    	retry.send(Message.of(tarjetaCredito));
    }

}
