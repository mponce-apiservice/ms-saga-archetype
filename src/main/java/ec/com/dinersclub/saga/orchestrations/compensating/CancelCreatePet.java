package ec.com.dinersclub.saga.orchestrations.compensating;

import java.util.Set;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.eclipse.microprofile.rest.client.inject.RestClient;

import ec.com.dinersclub.saga.choreography.compensating.ICancelCreatePetEvent;
import ec.com.dinersclub.saga.services.clients.PetstoreClient;
import ec.com.dinersclub.saga.services.models.Petstore;
import ec.com.dinersclub.saga.services.models.PetstoreDelete;

@ApplicationScoped
public class CancelCreatePet implements ICancelCreatePet {
	
	@Inject
    @RestClient
    PetstoreClient petstoreClient;
	
	@Inject
	ICancelCreatePetEvent eventRollback;
	
	public void compensatingCreatePet(Petstore pet) {
		PetstoreDelete response = petstoreClient.deleteByPetId(pet.id);
		if(response == null) {
			eventRollback.generateEventHandler(pet);
		}
	}

}
