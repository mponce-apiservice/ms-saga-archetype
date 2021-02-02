package ec.com.dinersclub.saga.services;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.eclipse.microprofile.faulttolerance.ExecutionContext;
import org.eclipse.microprofile.faulttolerance.Fallback;
import org.eclipse.microprofile.faulttolerance.FallbackHandler;
import org.eclipse.microprofile.faulttolerance.Retry;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import org.jboss.resteasy.annotations.jaxrs.PathParam;

import ec.com.dinersclub.saga.services.models.Country;

@Path("/v2")
@RegisterRestClient
public interface CountriesService {

    @GET
    @Path("/name/{name}")
    @Produces("application/json")
    @Retry(maxRetries = 3, delay = 2000)
    @Fallback(CountriesFallback.class)
    Set<Country> getByName(@PathParam String name);
    
    public static class CountriesFallback implements FallbackHandler<Set<Country>> {

        private static final Set<Country> EMPTY_COUNTRY = new HashSet<Country>();
        @Override
        public Set<Country> handle(ExecutionContext context) {
            return EMPTY_COUNTRY;
        }

    }
    
    @GET
    @Path("/region/{name}")
    @Produces("application/json")
    @Retry(maxRetries = 3, delay = 2000)
    @Fallback(RegionsFallback.class)
    Set<Country> getByRegion(@PathParam String name);
    
    public static class RegionsFallback implements FallbackHandler<Set<Country>> {

        private static final Set<Country> EMPTY_COUNTRY = new HashSet<Country>();
        @Override
        public Set<Country> handle(ExecutionContext context) {
            return EMPTY_COUNTRY;
        }

    }
    
}