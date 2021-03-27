package ec.com.dinersclub.saga.services;

import ec.com.dinersclub.saga.orchestrations.transactions.models.Tarjeta;
import ec.com.dinersclub.saga.services.models.TarjetaCredito;

public interface ITarjetaCreditoService {
	
	public TarjetaCredito createTarjetaCredito(Tarjeta tarjeta);
	
	public TarjetaCredito createTarjetaCredito(TarjetaCredito tarjeta);
	
	public TarjetaCredito getCountry(Tarjeta tarjeta);
	
	public TarjetaCredito getCountry(TarjetaCredito tarjeta);
	
	public TarjetaCredito updateTarjetaCredito(Tarjeta tarjeta);
	
	public TarjetaCredito updateTarjetaCredito(TarjetaCredito tarjeta);
	
	public TarjetaCredito removeTarjetaCredito(Tarjeta tarjeta);
	
	public TarjetaCredito removeTarjetaCredito(TarjetaCredito tarjeta);

}
