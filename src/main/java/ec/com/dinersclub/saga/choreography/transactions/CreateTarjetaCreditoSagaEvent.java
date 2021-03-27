package ec.com.dinersclub.saga.choreography.transactions;

import javax.inject.Inject;

import org.eclipse.microprofile.reactive.messaging.Acknowledgment;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eclipse.microprofile.reactive.messaging.Message;
import org.eclipse.microprofile.reactive.messaging.Outgoing;

import ec.com.dinersclub.saga.services.ITarjetaCreditoService;
import ec.com.dinersclub.saga.services.models.TarjetaCredito;
import io.vertx.core.json.JsonObject;

public class CreateTarjetaCreditoSagaEvent implements ICreateTarjetaCreditoSagaEvent {
	
	@Inject
	ITarjetaCreditoService tarjetaCreditoService;
	
	@Inject @Channel("createTarjetaCredito") Emitter<TarjetaCredito> retry;
	
    @Incoming("createTarjetaCredito")
    @Outgoing("updateTarjetaCredito")
    @Acknowledgment(Acknowledgment.Strategy.PRE_PROCESSING)
	public TarjetaCredito createTarjetaCredito(JsonObject tarjeta) {
		
		TarjetaCredito tc = new TarjetaCredito(tarjeta);
		TarjetaCredito response = tarjetaCreditoService.createTarjetaCredito(tc);
		if(response == null) {
			this.generateEventHandler(tc);
		}
		return tc;
		
	}
    
    public void generateEventHandler(TarjetaCredito tarjeta) {
    	retry.send(Message.of(tarjeta));
    }

}
