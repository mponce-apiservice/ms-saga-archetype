package ec.com.dinersclub.saga.choreography.transactions;

import ec.com.dinersclub.saga.services.models.TarjetaCredito;

public interface ICreateTarjetaCreditoSagaEvent {

	void generateEventHandler(TarjetaCredito tarjeta);
	
}
