package ec.com.dinersclub.saga.choreography.transactions;

import java.util.Set;

import javax.inject.Inject;

import org.eclipse.microprofile.reactive.messaging.Acknowledgment;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eclipse.microprofile.reactive.messaging.Message;
import org.eclipse.microprofile.reactive.messaging.Outgoing;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import ec.com.dinersclub.saga.services.clients.PetstoreClient;
import ec.com.dinersclub.saga.services.models.Petstore;
import io.vertx.core.json.JsonObject;

public class CreatePetSagaEvent implements ICreatePetSagaEvent {
	
	@Inject
    @RestClient
    PetstoreClient petstoreClient;
	
	//@Inject @Channel("createPet") Emitter<Petstore> retry;
	
    //@Incoming("createPet")
    //@Outgoing("updatePet")
    //@Acknowledgment(Acknowledgment.Strategy.PRE_PROCESSING)
	public Petstore createPet(JsonObject pet) {
		
		Petstore petstore = new Petstore(pet);
		/*Petstore response = petstoreService.createPet(petstore);
		if(response == null) {
			this.generateEventHandler(petstore);
		}*/
		return petstore;
		
	}
    
    public void generateEventHandler(Petstore pet) {
    	//retry.send(Message.of(pet));
    }

}
