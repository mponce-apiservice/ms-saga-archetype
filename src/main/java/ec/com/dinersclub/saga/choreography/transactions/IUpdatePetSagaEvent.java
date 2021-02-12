package ec.com.dinersclub.saga.choreography.transactions;

import ec.com.dinersclub.saga.services.models.Petstore;

public interface IUpdatePetSagaEvent {

	void generateEventHandler(Petstore pet);
	
}
