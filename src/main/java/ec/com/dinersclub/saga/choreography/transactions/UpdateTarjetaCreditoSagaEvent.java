package ec.com.dinersclub.saga.choreography.transactions;

import java.util.Set;

import javax.inject.Inject;

import org.eclipse.microprofile.reactive.messaging.Acknowledgment;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eclipse.microprofile.reactive.messaging.Message;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import ec.com.dinersclub.saga.choreography.compensating.ICancelCreateTarjetaCreditoEvent;
import ec.com.dinersclub.saga.services.CountriesService;
import ec.com.dinersclub.saga.services.ITarjetaCreditoService;
import ec.com.dinersclub.saga.services.models.Country;
import ec.com.dinersclub.saga.services.models.TarjetaCredito;
import io.vertx.core.json.JsonObject;

public class UpdateTarjetaCreditoSagaEvent implements IUpdateTarjetaCreditoSagaEvent {

	
	@Inject
	ITarjetaCreditoService tarjetaCreditoService;
	
	@Inject
    @RestClient
    CountriesService countriesService;
	
	@Inject
	ICancelCreateTarjetaCreditoEvent cancel;
	
	@Inject @Channel("updateTarjetaCredito") Emitter<TarjetaCredito> retry;
	
	@Incoming("updateTarjetaCredito")
    @Acknowledgment(Acknowledgment.Strategy.PRE_PROCESSING)
	public void updateTarjetaCredito(JsonObject tarjeta) {
		
		TarjetaCredito tc = new TarjetaCredito(tarjeta);
		
		Set<Country> country = countriesService.getByName("greece");
		
		if(!country.isEmpty()) {
			try {
				tc.addTags(country);
				TarjetaCredito response = tarjetaCreditoService.updateTarjetaCredito(tc);
				if(response == null) {
					cancel.generateEventHandler(tc);
				}
	        } catch(Exception ex) {
				cancel.generateEventHandler(tc);
			}
		}else {
			this.generateEventHandler(tc);
		}
		
	}
	
	public void generateEventHandler(TarjetaCredito tarjeta) {
    	retry.send(Message.of(tarjeta));
    }
	
}
