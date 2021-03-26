package ec.com.dinersclub.saga.orchestrations.compensating;

import java.util.Set;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.eclipse.microprofile.rest.client.inject.RestClient;

import ec.com.dinersclub.saga.choreography.compensating.ICancelCreateTarjetaCreditoEvent;
import ec.com.dinersclub.saga.services.TarjetaCreditoService;
import ec.com.dinersclub.saga.services.models.TarjetaCredito;
import ec.com.dinersclub.saga.services.models.TarjetaCreditoDelete;

@ApplicationScoped
public class CancelCreateTarjetaCredito implements ICancelCreateTarjetaCredito {
	
	@Inject
    @RestClient
	TarjetaCreditoService tarjetaCreditoService;
	
	@Inject
	ICancelCreateTarjetaCreditoEvent eventRollback;
	
	public void compensatingCreateTarjeta(TarjetaCredito tarjetaCredito) {
		Set<TarjetaCreditoDelete> response = tarjetaCreditoService.deleteByTarjetaCreditoId(tarjetaCredito.id);
		if(response.isEmpty()) {
			eventRollback.generateEventHandler(tarjetaCredito);
		}
	}

}
