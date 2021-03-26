package ec.com.dinersclub.saga.services;

import java.util.HashSet;
import java.util.Set;

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

import ec.com.dinersclub.saga.services.models.TarjetaCredito;
import ec.com.dinersclub.saga.services.models.TarjetaCreditoDelete;

@Path("/v2")
@RegisterRestClient
public interface TarjetaCreditoService {

    @GET
    @Path("/tarjeta/{tarjetaId}")
    @Produces("application/json")
    @Retry(maxRetries = 3, delay = 2000)
    @Fallback(RollbackFallback.class)
    Set<TarjetaCredito> getByTarjetaCreditoId(@PathParam int tarjetaId);
    
    
    @POST
    @Path("/tarjeta")
    @Produces("application/json")
    @Consumes("application/json")
    @Retry(maxRetries = 3, delay = 2000)
    @Fallback(RollbackFallback.class)
    Set<TarjetaCredito> createTarjetaCredito(TarjetaCredito pet);
    
    @PUT
    @Path("/tarjeta")
    @Produces("application/json")
    @Consumes("application/json")
    @Retry(maxRetries = 3, delay = 2000)
    @Fallback(RollbackFallback.class)
    Set<TarjetaCredito> updateTarjetaCredito(TarjetaCredito pet);
    
    @DELETE
    @Path("/tarjeta/{tarjetaId}")
    @Produces("application/json")
    @Retry(maxRetries = 3, delay = 2000)
    @Fallback(AsyncFallback.class)
    Set<TarjetaCreditoDelete> deleteByTarjetaCreditoId(@PathParam int tarjetaId);
    
    public static class AsyncFallback implements FallbackHandler<Set<TarjetaCreditoDelete>> {

        private static final Set<TarjetaCreditoDelete> EMPTY_COUNTRY = new HashSet<TarjetaCreditoDelete>();
        @Override
        public Set<TarjetaCreditoDelete> handle(ExecutionContext context) {
            return EMPTY_COUNTRY;
        }

    }
    
    public static class RollbackFallback implements FallbackHandler<Set<TarjetaCredito>> {

        private static final Set<TarjetaCredito> EMPTY_COUNTRY = new HashSet<TarjetaCredito>();
        @Override
        public Set<TarjetaCredito> handle(ExecutionContext context) {
            return EMPTY_COUNTRY;
        }

    }
    
}
