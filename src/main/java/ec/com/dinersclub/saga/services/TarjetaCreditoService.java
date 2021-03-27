package ec.com.dinersclub.saga.services;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.eclipse.microprofile.rest.client.inject.RestClient;

import ec.com.dinersclub.saga.orchestrations.transactions.models.Tarjeta;
import ec.com.dinersclub.saga.services.models.Country;
import ec.com.dinersclub.saga.services.models.TarjetaCredito;

@ApplicationScoped
public class TarjetaCreditoService implements ITarjetaCreditoService {
	
	private Set<TarjetaCredito> tarjetaCredito = Collections.synchronizedSet(new HashSet<>());
	
	@Inject
    @RestClient
    CountriesService countriesService;
	
	public TarjetaCredito createTarjetaCredito(Tarjeta tarjeta) {
		System.out.println("id createTarjetaCredito : "+tarjeta.getId());
		TarjetaCredito tc = new TarjetaCredito(tarjeta);
		tarjetaCredito.add(tc);
		return tc;
	}
	
	public TarjetaCredito createTarjetaCredito(TarjetaCredito tarjeta) {
		System.out.println("id createTarjetaCredito : "+tarjeta.id);
		tarjetaCredito.add(tarjeta);
		return tarjeta;
	}
	
	public TarjetaCredito getCountry(Tarjeta tarjeta) {
		System.out.println("id getCountry : "+tarjeta.getId());
		Set<Country> country = countriesService.getByName("greece");
		TarjetaCredito tc = new TarjetaCredito(tarjeta);
		tc.addTags(country);
		return tc;
	}
	
	public TarjetaCredito getCountry(TarjetaCredito tarjeta) {
		System.out.println("id getCountry : "+tarjeta.id);
		Set<Country> country = countriesService.getByName("greece");
		tarjeta.addTags(country);
		return tarjeta;
	}
	
	public TarjetaCredito updateTarjetaCredito(Tarjeta tarjeta) {
		System.out.println("id updateTarjetaCredito : "+tarjeta.getId());
		TarjetaCredito tc = this.getCountry(tarjeta);
		if(tarjetaCredito.contains(tc)) {
			tarjetaCredito.remove(tc);
			tarjetaCredito.add(tc);
		}
		return tc;
	}
	
	public TarjetaCredito updateTarjetaCredito(TarjetaCredito tarjeta) {
		System.out.println("id updateTarjetaCredito : "+tarjeta.id);
		TarjetaCredito tc = this.getCountry(tarjeta);
		if(tarjetaCredito.contains(tc)) {
			tarjetaCredito.remove(tc);
			tarjetaCredito.add(tc);
		}
		return tc;
	}
	
	public TarjetaCredito removeTarjetaCredito(Tarjeta tarjeta) {
		System.out.println("id removeTarjetaCredito : "+tarjeta.getId());
		TarjetaCredito tc = new TarjetaCredito(tarjeta);
		tarjetaCredito.remove(tc);
		return tc;
    }
	
	public TarjetaCredito removeTarjetaCredito(TarjetaCredito tarjeta) {
		System.out.println("id removeTarjetaCredito : "+tarjeta.id);
		tarjetaCredito.remove(tarjeta);
		return tarjeta;
    }
	
}