package ec.com.dinersclub.saga.rest;

import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.eclipse.microprofile.faulttolerance.CircuitBreaker;
import org.eclipse.microprofile.faulttolerance.Timeout;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.resteasy.annotations.jaxrs.PathParam;

import ec.com.dinersclub.saga.services.CountriesService;
import ec.com.dinersclub.saga.services.models.Country;

@Path("/country")
public class CountriesResource {
	
	private AtomicLong counter = new AtomicLong(0);

	@Inject
    @RestClient
    CountriesService countriesService;

    @GET
    @Timeout(60000)
    @Path("/name/{name}")
    @Produces(MediaType.APPLICATION_JSON)
    @CircuitBreaker(requestVolumeThreshold = 4, failureRatio = 0.75, delay = 5000)
    public Set<Country> name(@PathParam String name) {
    	maybeFail();
        return countriesService.getByName(name);
    }
    
    private void maybeFail() {
        // introduce some artificial failures
        final Long invocationNumber = counter.getAndIncrement();
        if (invocationNumber % 4 > 1) { // alternate 2 successful and 2 failing invocations
            throw new RuntimeException("Service failed.");
        }
    }
    
}