package ec.com.dinersclub.saga.orchestrations.transactions;

import java.util.HashSet;
import java.util.Set;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.eclipse.microprofile.rest.client.inject.RestClient;

import ec.com.dinersclub.saga.orchestrations.compensating.CancelCreateTarjetaCredito;
import ec.com.dinersclub.saga.orchestrations.transactions.models.Tarjeta;
import ec.com.dinersclub.saga.services.CountriesService;
import ec.com.dinersclub.saga.services.TarjetaCreditoService;
import ec.com.dinersclub.saga.services.models.Country;
import ec.com.dinersclub.saga.services.models.TarjetaCredito;

@ApplicationScoped
public class CreateTarjetaCreditoSaga implements ICreateTarjetaCreditoSaga {
	
	@Inject
    @RestClient
    TarjetaCreditoService petstoreService;
	
	@Inject
    @RestClient
    CountriesService countriesService;
	
	@Inject
	CancelCreateTarjetaCredito cancel;
	
	public TarjetaCredito createTarjetaCredito(Tarjeta tarjeta) {

		TarjetaCredito tarjetaCredito = new TarjetaCredito(tarjeta);
		petstoreService.createTarjetaCredito(tarjetaCredito);

		Set<Country> country = new HashSet<Country>();
		if(tarjeta.getStatus().equalsIgnoreCase("rollback")) {
			country = countriesService.getByName("xxx");
		}else {
			country = countriesService.getByName("greece");
		}
		
		if(!country.isEmpty()) {
	        tarjetaCredito.addTags(country);
			petstoreService.updateTarjetaCredito(tarjetaCredito);
			return tarjetaCredito;
		}else {
			cancel.compensatingCreateTarjeta(tarjetaCredito);
			return null;
		}
		
	}

}
