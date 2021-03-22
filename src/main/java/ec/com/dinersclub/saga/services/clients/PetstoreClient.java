package ec.com.dinersclub.saga.services.clients;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.eclipse.microprofile.faulttolerance.ExecutionContext;
import org.eclipse.microprofile.faulttolerance.Fallback;
import org.eclipse.microprofile.faulttolerance.FallbackHandler;
import org.eclipse.microprofile.faulttolerance.Retry;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import org.jboss.resteasy.annotations.jaxrs.PathParam;

import ec.com.dinersclub.saga.services.models.Petstore;
import ec.com.dinersclub.saga.services.models.PetstoreDelete;

@Path("/v2")
@RegisterRestClient
public interface PetstoreClient {

    @GET
    @Path("/pet/{petId}")
    @Produces("application/json")
    @Retry(maxRetries = 3, delay = 2000)
    @Fallback(RollbackFallback.class)
    Petstore getByPetId(@PathParam int petId);
    
    
    @POST
    @Path("/pet")
    @Produces("application/json")
    @Consumes("application/json")
    @Retry(maxRetries = 3, delay = 2000)
    //@Fallback(RollbackFallback.class)
    Petstore createPet(Petstore pet);
    
    @PUT
    @Path("/pet")
    @Produces("application/json")
    @Consumes("application/json")
    @Retry(maxRetries = 3, delay = 2000)
    @Fallback(RollbackFallback.class)
    Petstore updatePet(Petstore pet);
    
    @DELETE
    @Path("/pet/{petId}")
    @Produces("application/json")
    @Retry(maxRetries = 3, delay = 2000)
    @Fallback(AsyncFallback.class)
    PetstoreDelete deleteByPetId(@PathParam int petId);
    
    public static class AsyncFallback implements FallbackHandler<PetstoreDelete> {

        private static final PetstoreDelete EMPTY_PETSTORE = new PetstoreDelete();
        @Override
        public PetstoreDelete handle(ExecutionContext context) {
            return EMPTY_PETSTORE;
        }

    }
    
    public static class RollbackFallback implements FallbackHandler<Petstore> {

        private static final Petstore EMPTY_PETSTORE = new Petstore();
        @Override
        public Petstore handle(ExecutionContext context) {
            return EMPTY_PETSTORE;
        }

    }
    
}