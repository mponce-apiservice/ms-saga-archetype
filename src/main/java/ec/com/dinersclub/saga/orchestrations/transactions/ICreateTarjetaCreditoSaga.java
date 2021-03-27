package ec.com.dinersclub.saga.orchestrations.transactions;

import ec.com.dinersclub.saga.orchestrations.transactions.models.Tarjeta;
import ec.com.dinersclub.saga.services.models.TarjetaCredito;

public interface ICreateTarjetaCreditoSaga {

	TarjetaCredito createPet(Tarjeta pet);
	
}
