package ec.com.dinersclub.saga.rest;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.camel.CamelContext;
import org.eclipse.microprofile.faulttolerance.CircuitBreaker;
import org.eclipse.microprofile.faulttolerance.Timeout;

import ec.com.dinersclub.saga.orchestrations.transactions.CreatePetSaga;
import ec.com.dinersclub.saga.orchestrations.transactions.ICreatePetSaga;
import ec.com.dinersclub.saga.orchestrations.transactions.models.Pet;
import ec.com.dinersclub.saga.services.models.Petstore;

@Path("/saga")
public class SagaResource {
	
	private AtomicLong counter = new AtomicLong(0);
	
	@Inject
    CamelContext context;

	@Inject
	CreatePetSaga saga;

    @POST
    @Timeout(60000)
    @Path("/pet/create")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @CircuitBreaker(requestVolumeThreshold = 4, failureRatio = 0.75, delay = 5000)
    public Response create(Pet pet) {
    	maybeFail();
    	pet(pet);
    	return Response.status(201).build();
    }
    
    private void maybeFail() {
        // introduce some artificial failures
        final Long invocationNumber = counter.getAndIncrement();
        if (invocationNumber % 4 > 1) { // alternate 2 successful and 2 failing invocations
            throw new RuntimeException("Service failed.");
        }
    }
    
    private void pet(Pet pet) {
    	Petstore petstore = new Petstore(pet);
    	context.createFluentProducerTemplate()
    	.to("direct:pet")
    	.withHeader("petstoreId", String.valueOf(pet.getId()))
    	.withBodyAs(petstore, Petstore.class)
    	.request();
    }
    
}