package ec.com.dinersclub.saga.orchestrations.transactions;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.eclipse.microprofile.rest.client.inject.RestClient;

import ec.com.dinersclub.saga.events.ICancelCreatePetEventRollback;
import ec.com.dinersclub.saga.orchestrations.rollback.CancelCreatePet;
import ec.com.dinersclub.saga.orchestrations.transactions.models.Pet;
import ec.com.dinersclub.saga.orchestrations.transactions.models.PetTags;
import ec.com.dinersclub.saga.services.CountriesService;
import ec.com.dinersclub.saga.services.PetstoreService;
import ec.com.dinersclub.saga.services.models.Country;
import ec.com.dinersclub.saga.services.models.Petstore;
import ec.com.dinersclub.saga.services.models.Petstore.PetstoreCategory;
import ec.com.dinersclub.saga.services.models.Petstore.PetstoreTags;

@ApplicationScoped
public class CreatePetSaga {
	
	@Inject
    @RestClient
    PetstoreService petstoreService;
	
	@Inject
    @RestClient
    CountriesService countriesService;
	
	@Inject
	CancelCreatePet cancel;
	
	public Petstore createPet(Pet pet) {
		
		Petstore petstore = new Petstore();
		petstore.id = pet.getId();
		
		PetstoreCategory category = new PetstoreCategory();
		category.id = pet.getCategory().getId();
		category.name = pet.getCategory().getName();
		
		petstore.category = category;
		petstore.name = pet.getName();
		petstore.photoUrls = pet.getPhotoUrls();
		
		List<PetstoreTags> listaTags = new ArrayList<PetstoreTags>();
		for(PetTags tag : pet.getTags()) {
			PetstoreTags tags = new PetstoreTags();
			tags.id = tag.getId();
			tags.name = tag.getName();
			listaTags.add(tags);
		}
		
		petstore.tags = listaTags;
		petstore.status = pet.getStatus();
		
		petstoreService.createPet(petstore);
		Set<Country> country = new HashSet<Country>();
		
		if(pet.getStatus().equalsIgnoreCase("rollback")) {
			country = countriesService.getByName("xxx");
		}else {
			country = countriesService.getByName("greece");
		}
		if(!country.isEmpty()) {
			PetstoreTags pt = new PetstoreTags();
			pt.id = 99;
			Iterator<Country> itr = country.iterator();
	        while (itr.hasNext()) { 
	        	Country c = itr.next();
	        	pt.name = c.name;
	        }
			petstore.tags.add(pt);
			petstoreService.updatePet(petstore);
			return petstore;
		}else {
			cancel.rollbackCreatePet(pet.getId());
			return null;
		}
	}

}
