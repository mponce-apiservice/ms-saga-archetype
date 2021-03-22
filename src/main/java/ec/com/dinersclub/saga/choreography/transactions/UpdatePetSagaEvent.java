package ec.com.dinersclub.saga.choreography.transactions;

import java.util.Iterator;
import java.util.Set;

import javax.inject.Inject;

import org.eclipse.microprofile.reactive.messaging.Acknowledgment;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eclipse.microprofile.reactive.messaging.Message;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import ec.com.dinersclub.saga.choreography.compensating.ICancelCreatePetEvent;
import ec.com.dinersclub.saga.services.CountriesService;
import ec.com.dinersclub.saga.services.clients.PetstoreClient;
import ec.com.dinersclub.saga.services.models.Country;
import ec.com.dinersclub.saga.services.models.Petstore;
import io.vertx.core.json.JsonObject;

public class UpdatePetSagaEvent implements IUpdatePetSagaEvent {

	
	@Inject
    @RestClient
    PetstoreClient petstoreClient;
	
	@Inject
    @RestClient
    CountriesService countriesService;
	
	@Inject
	ICancelCreatePetEvent cancel;
	
	//@Inject @Channel("updatePet") Emitter<Petstore> retry;
	
	//@Incoming("updatePet")
    //@Acknowledgment(Acknowledgment.Strategy.PRE_PROCESSING)
	public void updatePet(JsonObject pet) {
		
		Petstore petstore = new Petstore(pet);
		
		Set<Country> country = countriesService.getByName("greece");
		
		if(!country.isEmpty()) {
			try {
				petstore.addTags(country);
				Petstore response = petstoreClient.updatePet(petstore);
				if(response == null) {
					cancel.generateEventHandler(petstore);
				}
	        } catch(Exception ex) {
				cancel.generateEventHandler(petstore);
			}
		}else {
			this.generateEventHandler(petstore);
		}
		
	}
	
	public void generateEventHandler(Petstore pet) {
    	//retry.send(Message.of(pet));
    }
	
}
