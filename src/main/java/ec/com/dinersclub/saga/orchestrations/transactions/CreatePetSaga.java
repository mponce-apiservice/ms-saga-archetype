package ec.com.dinersclub.saga.orchestrations.transactions;

import java.util.Set;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.SagaPropagation;
import org.apache.camel.saga.InMemorySagaService;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import ec.com.dinersclub.saga.orchestrations.compensating.CancelCreatePet;
import ec.com.dinersclub.saga.services.CountriesService;
import ec.com.dinersclub.saga.services.clients.PetstoreClient;
import ec.com.dinersclub.saga.services.models.Country;
import ec.com.dinersclub.saga.services.models.Petstore;
import ec.com.dinersclub.saga.services.models.PetstoreDelete;

@ApplicationScoped
public class CreatePetSaga  extends RouteBuilder {
	
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

	@Override
	public void configure() throws Exception {
		
		getContext().addService(new InMemorySagaService(), true);
		
		from("direct:pet")
			.saga().propagation(SagaPropagation.REQUIRES_NEW)
            .to("direct:newPet")
            .to("direct:updatePet");

		from("direct:newPet")
		  	.saga()
		  	.propagation(SagaPropagation.MANDATORY)
		  	.compensation("direct:cancelPet")
		    .transform()
		    .body(Petstore.class, this::createPet)
		    .log("Pet ${body} created");
		
		from("direct:updatePet")
		  	.saga()
		  	.propagation(SagaPropagation.MANDATORY)
		  	.compensation("direct:cancelPet")
		    .transform()
		    .body(Petstore.class, this::updatePet)
		    .log("Pet ${body} update");
		
		from("direct:cancelPet")
			.transform()
			.body(Petstore.class, this::removePet)
		    .log("Pet ${body} cancelled");
		
	}

}
