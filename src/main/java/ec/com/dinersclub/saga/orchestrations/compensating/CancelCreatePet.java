package ec.com.dinersclub.saga.orchestrations.compensating;

import java.util.Set;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.eclipse.microprofile.rest.client.inject.RestClient;

import ec.com.dinersclub.saga.choreography.compensating.ICancelCreatePetEvent;
import ec.com.dinersclub.saga.services.PetstoreService;
import ec.com.dinersclub.saga.services.models.Petstore;
import ec.com.dinersclub.saga.services.models.PetstoreDelete;

@ApplicationScoped
public class CancelCreatePet implements ICancelCreatePet {
	
	@Inject
    @RestClient
    PetstoreService petstoreService;
	
	@Inject
	ICancelCreatePetEvent eventRollback;
	
	public void compensatingCreatePet(Petstore pet) {
		Set<PetstoreDelete> response = petstoreService.deleteByPetId(pet.id);
		if(response.isEmpty()) {
			eventRollback.generateEventHandler(pet);
		}
	}

}
