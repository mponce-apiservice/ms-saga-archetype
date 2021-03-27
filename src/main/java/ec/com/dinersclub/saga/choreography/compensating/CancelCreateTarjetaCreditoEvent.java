package ec.com.dinersclub.saga.choreography.compensating;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.eclipse.microprofile.reactive.messaging.Acknowledgment;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eclipse.microprofile.reactive.messaging.Message;

import ec.com.dinersclub.saga.services.ITarjetaCreditoService;
import ec.com.dinersclub.saga.services.models.TarjetaCredito;
import io.vertx.core.json.JsonObject;

@ApplicationScoped
public class CancelCreateTarjetaCreditoEvent implements ICancelCreateTarjetaCreditoEvent {
	
	@Inject
	ITarjetaCreditoService tarjetaCreditoService;
	
	@Inject @Channel("cancelTarjetaCredito") Emitter<TarjetaCredito> retry;
	
	@Incoming("cancelTarjetaCredito")
    @Acknowledgment(Acknowledgment.Strategy.PRE_PROCESSING)
	public void compensatingCreateTarjetaCredito(JsonObject tarjeta) {
		
		TarjetaCredito tc = new TarjetaCredito(tarjeta);
		TarjetaCredito response = tarjetaCreditoService.removeTarjetaCredito(tc);
		if(response == null) {
			this.generateEventHandler(tc);
		}
		
	}
	
    public void generateEventHandler(TarjetaCredito tarjeta) {
    	retry.send(Message.of(tarjeta));
    }

}
