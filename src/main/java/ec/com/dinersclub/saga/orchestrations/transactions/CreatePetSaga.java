package ec.com.dinersclub.saga.orchestrations.transactions;

import java.util.HashSet;
import java.util.Iterator;
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
import ec.com.dinersclub.saga.services.models.Petstore.PetstoreTags;

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
			Iterator<Country> itr = country.iterator();
	        while (itr.hasNext()) { 
	        	Country c = itr.next();
	        	petstore.addTags(99, c.name);
	        }
			petstoreService.updatePet(petstore);
		}else {
			cancel.compensatingCreatePet(pet.getId());
			return null;
		}
		return petstore;
	}

}
