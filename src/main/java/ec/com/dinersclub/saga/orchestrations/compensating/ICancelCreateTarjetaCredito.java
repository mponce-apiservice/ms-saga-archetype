package ec.com.dinersclub.saga.orchestrations.compensating;

import ec.com.dinersclub.saga.services.models.TarjetaCredito;

public interface ICancelCreateTarjetaCredito {
	
	void compensatingCreateTarjeta(TarjetaCredito tarjetaCredito);

}
