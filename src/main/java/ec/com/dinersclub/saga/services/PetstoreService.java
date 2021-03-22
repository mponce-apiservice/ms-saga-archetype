package ec.com.dinersclub.saga.services;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.apache.camel.Body;
import org.apache.camel.Header;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import ec.com.dinersclub.saga.orchestrations.transactions.models.Pet;
import ec.com.dinersclub.saga.services.clients.PetstoreClient;
import ec.com.dinersclub.saga.services.models.Petstore;

@ApplicationScoped
public class PetstoreService implements IPetstoreService {

	@Inject
    @RestClient
    PetstoreClient petstoreClient;
	
	@Override
	public void createPet(@Body Pet pet) {
		//System.out.println("id : "+id);
		//System.out.println("petId : "+petId);
		//Petstore petstore = new Petstore(pet);
		//petstoreClient.createPet(petstore);
    }
	
	@Override
	public void removePet(String id, @Header("petstoreId") String petId) {
		//petstoreClient.deleteByPetId(petId);
    }
    
}