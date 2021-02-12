package ec.com.dinersclub.saga.events;

import javax.inject.Inject;

import org.eclipse.microprofile.reactive.messaging.Acknowledgment;
import org.eclipse.microprofile.reactive.messaging.Incoming;

import ec.com.dinersclub.saga.choreography.transactions.ICreatePetSagaEvent;
import ec.com.dinersclub.saga.services.models.Petstore;
import io.vertx.core.json.JsonObject;

public class SagaEvent {
	
	@Inject
	ICreatePetSagaEvent createPet;
	
    //@Incoming("saga")
    //@Acknowledgment(Acknowledgment.Strategy.PRE_PROCESSING)
    public void process(JsonObject json) {
        if(json.getString("method").equalsIgnoreCase("create-pet")) {
        	createPet.generateEventHandler(new Petstore(json.getJsonObject("data")));
        }
    }

}
