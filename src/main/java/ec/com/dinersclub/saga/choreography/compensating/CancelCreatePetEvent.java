package ec.com.dinersclub.saga.choreography.compensating;

import java.util.Set;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.eclipse.microprofile.reactive.messaging.Acknowledgment;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eclipse.microprofile.reactive.messaging.Message;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import ec.com.dinersclub.saga.services.PetstoreService;
import ec.com.dinersclub.saga.services.models.Petstore;
import ec.com.dinersclub.saga.services.models.PetstoreDelete;
import io.vertx.core.json.JsonObject;

@ApplicationScoped
public class CancelCreatePetEvent implements ICancelCreatePetEvent {
	
	@Inject
    @RestClient
    PetstoreService petstoreService;
	
	@Inject @Channel("cancelPet") Emitter<Petstore> retry;
	
	//@Incoming("cancelPet")
    //@Acknowledgment(Acknowledgment.Strategy.PRE_PROCESSING)
	public void compensatingCreatePet(JsonObject pet) {
		
		Petstore petstore = new Petstore(pet);
		Set<PetstoreDelete> response = petstoreService.deleteByPetId(petstore.id);
		if(response.isEmpty()) {
			this.generateEventHandler(petstore);
		}
		
	}
	
    public void generateEventHandler(Petstore pet) {
    	retry.send(Message.of(pet));
    }

}
