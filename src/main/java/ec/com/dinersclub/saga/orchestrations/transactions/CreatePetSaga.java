package ec.com.dinersclub.saga.orchestrations.transactions;

import java.util.HashSet;
import java.util.Set;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.eclipse.microprofile.rest.client.inject.RestClient;

import ec.com.dinersclub.saga.orchestrations.compensating.CancelCreatePet;
import ec.com.dinersclub.saga.orchestrations.transactions.models.Pet;
import ec.com.dinersclub.saga.services.CountriesService;
import ec.com.dinersclub.saga.services.PetstoreService;
import ec.com.dinersclub.saga.services.models.Country;
import ec.com.dinersclub.saga.services.models.Petstore;

@ApplicationScoped
public class CreatePetSaga implements ICreatePetSaga {
	
	@Inject
    @RestClient
    PetstoreService petstoreService;
	
	@Inject
    @RestClient
    CountriesService countriesService;
	
	@Inject
	CancelCreatePet cancel;
	
	public Petstore createPet(Pet pet) {

		Petstore petstore = new Petstore(pet);
		petstoreService.createPet(petstore);

		Set<Country> country = new HashSet<Country>();
		if(pet.getStatus().equalsIgnoreCase("rollback")) {
			country = countriesService.getByName("xxx");
		}else {
			country = countriesService.getByName("greece");
		}
		
		if(!country.isEmpty()) {
	        petstore.addTags(country);
			petstoreService.updatePet(petstore);
			return petstore;
		}else {
			cancel.compensatingCreatePet(petstore);
			return null;
		}
		
	}

}
