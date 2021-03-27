package ec.com.dinersclub.saga.choreography.compensating;

import ec.com.dinersclub.saga.services.models.TarjetaCredito;

public interface ICancelCreateTarjetaCreditoEvent {
	
	void generateEventHandler(TarjetaCredito tarjeta);

}
