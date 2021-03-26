package ec.com.dinersclub.saga.choreography.transactions;

import java.util.Set;

import javax.inject.Inject;

import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.eclipse.microprofile.reactive.messaging.Message;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import ec.com.dinersclub.saga.choreography.compensating.ICancelCreateTarjetaCreditoEvent;
import ec.com.dinersclub.saga.services.CountriesService;
import ec.com.dinersclub.saga.services.TarjetaCreditoService;
import ec.com.dinersclub.saga.services.models.Country;
import ec.com.dinersclub.saga.services.models.TarjetaCredito;
import io.vertx.core.json.JsonObject;

public class UpdateTarjetaCreditoSagaEvent implements IUpdateTarjetaCreditoSagaEvent {

	
	@Inject
    @RestClient
    TarjetaCreditoService tarjetaCreditoService;
	
	@Inject
    @RestClient
    CountriesService countriesService;
	
	@Inject
	ICancelCreateTarjetaCreditoEvent cancel;
	
	@Inject @Channel("updateTarjeta") Emitter<TarjetaCredito> retry;
	
	//@Incoming("updateTarjeta")
    //@Acknowledgment(Acknowledgment.Strategy.PRE_PROCESSING)
	public void updateTarjeta(JsonObject jsonObject) {
		
		TarjetaCredito tarjetaCredito = new TarjetaCredito(jsonObject);
		
		Set<Country> country = countriesService.getByName("greece");
		
		if(!country.isEmpty()) {
			try {
				tarjetaCredito.addTags(country);
	        	tarjetaCreditoService.updateTarjetaCredito(tarjetaCredito);
	        } catch(Exception ex) {
				cancel.generateEventHandler(tarjetaCredito);
			}
		}else {
			this.generateEventHandler(tarjetaCredito);
		}
		
	}
	
	public void generateEventHandler(TarjetaCredito tarjetaCredito) {
    	retry.send(Message.of(tarjetaCredito));
    }
	
}
