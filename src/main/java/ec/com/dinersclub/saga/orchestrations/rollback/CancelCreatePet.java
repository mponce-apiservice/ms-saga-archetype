package ec.com.dinersclub.saga.orchestrations.rollback;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.eclipse.microprofile.rest.client.inject.RestClient;

import ec.com.dinersclub.saga.services.PetstoreService;

@ApplicationScoped
public class CancelCreatePet {
	
	@Inject
    @RestClient
    PetstoreService petstoreService;
	
	public void rollbackCreatePet(int id) {
		petstoreService.deleteByPetId(id);
	}

}
