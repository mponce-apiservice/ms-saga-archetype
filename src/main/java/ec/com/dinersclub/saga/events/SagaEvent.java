package ec.com.dinersclub.saga.events;

import javax.inject.Inject;

import org.eclipse.microprofile.reactive.messaging.Acknowledgment;
import org.eclipse.microprofile.reactive.messaging.Incoming;

import ec.com.dinersclub.saga.choreography.transactions.ICreateTarjetaCreditoSagaEvent;
import ec.com.dinersclub.saga.services.models.TarjetaCredito;
import io.vertx.core.json.JsonObject;

public class SagaEvent {
	
	@Inject
	ICreateTarjetaCreditoSagaEvent createTarjetaCredito;
	
    @Incoming("saga")
    @Acknowledgment(Acknowledgment.Strategy.PRE_PROCESSING)
    public void process(JsonObject json) {
        if(json.getString("method").equalsIgnoreCase("create-tarjeta")) {
        	createTarjetaCredito.generateEventHandler(new TarjetaCredito(json.getJsonObject("data")));
        }
    }

}
