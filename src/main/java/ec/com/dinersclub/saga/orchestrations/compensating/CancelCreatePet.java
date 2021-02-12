package ec.com.dinersclub.saga.orchestrations.compensating;

import java.util.Set;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.eclipse.microprofile.rest.client.inject.RestClient;

import ec.com.dinersclub.saga.events.ICancelCreatePetEventRollback;
import ec.com.dinersclub.saga.events.models.Rollback;
import ec.com.dinersclub.saga.services.PetstoreService;
import ec.com.dinersclub.saga.services.models.PetstoreDelete;

@ApplicationScoped
public class CancelCreatePet implements ICancelCreatePet {
	
	@Inject
    @RestClient
    PetstoreService petstoreService;
	
	@Inject
	ICancelCreatePetEventRollback eventRollback;
	
	public void compensatingCreatePet(int id) {
		Set<PetstoreDelete> response = petstoreService.deleteByPetId(id);
		if(response.isEmpty()) {
			Rollback rb = new Rollback();
			rb.setSaga("CreatePetSaga");
			rb.setMethod("createPet");
			rb.setId(id);
			eventRollback.generateEventHandler(rb);
		}
	}

}
