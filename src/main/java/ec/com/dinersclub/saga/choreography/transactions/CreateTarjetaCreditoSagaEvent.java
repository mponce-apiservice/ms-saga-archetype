package ec.com.dinersclub.saga.choreography.transactions;

import javax.inject.Inject;

import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.eclipse.microprofile.reactive.messaging.Message;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import ec.com.dinersclub.saga.services.TarjetaCreditoService;
import ec.com.dinersclub.saga.services.models.TarjetaCredito;
import io.vertx.core.json.JsonObject;

public class CreateTarjetaCreditoSagaEvent implements ICreateTarjetaCreditoSagaEvent {
	
	@Inject
    @RestClient
    TarjetaCreditoService tarjetaCreditoService;
	
	@Inject @Channel("createTarjeta") Emitter<TarjetaCredito> retry;
	
    //@Incoming("createTarjeta")
    //@Outgoing("updateTarjeta")
    //@Acknowledgment(Acknowledgment.Strategy.PRE_PROCESSING)
	public TarjetaCredito createTarjeta(JsonObject jsonObject) {
		
		TarjetaCredito tarjetaCredito = new TarjetaCredito(jsonObject);
		tarjetaCreditoService.createTarjetaCredito(tarjetaCredito);
		return tarjetaCredito;
		
	}
    
    public void generateEventHandler(TarjetaCredito tarjetaCredito) {
    	retry.send(Message.of(tarjetaCredito));
    }

}
