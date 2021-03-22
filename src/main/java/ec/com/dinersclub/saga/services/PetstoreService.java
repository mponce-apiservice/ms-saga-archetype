package ec.com.dinersclub.saga.services;

import java.util.Set;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.apache.camel.Body;
import org.apache.camel.Header;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import ec.com.dinersclub.saga.orchestrations.compensating.CancelCreatePet;
import ec.com.dinersclub.saga.orchestrations.transactions.models.Pet;
import ec.com.dinersclub.saga.services.clients.PetstoreClient;
import ec.com.dinersclub.saga.services.models.Country;
import ec.com.dinersclub.saga.services.models.Petstore;
import ec.com.dinersclub.saga.services.models.PetstoreDelete;

@ApplicationScoped
public class PetstoreService implements IPetstoreService {

	@Inject
    @RestClient
    PetstoreClient petstoreClient;
	
	@Inject
    @RestClient
    CountriesService countriesService;
	
	@Inject
	CancelCreatePet cancel;
	
	public Petstore createPet(Petstore pet) {
		System.out.println("id createPet : "+pet.id);
		Petstore petstore = petstoreClient.createPet(pet);
		return petstore;
	}
	
	public void getCountry(Petstore pet) {
		System.out.println("id getCountry : "+pet.id);
		Set<Country> country = countriesService.getByName("greece");
	    pet.addTags(country);
	}
	
	public Petstore updatePet(Petstore pet) {
		System.out.println("id updatePet : "+pet.id);
		this.getCountry(pet);
		Petstore petstore = petstoreClient.updatePet(pet);
		return petstore;
	}
	
	public PetstoreDelete removePet(Petstore pet) {
		System.out.println("id removePet : "+pet.id);
		PetstoreDelete deletePet = petstoreClient.deleteByPetId(pet.id);
		return deletePet;
    }
	
}