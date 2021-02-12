package ec.com.dinersclub.saga.choreography.transactions;

import ec.com.dinersclub.saga.services.models.Petstore;

public interface ICreatePetSagaEvent {

	void generateEventHandler(Petstore pet);
	
}
