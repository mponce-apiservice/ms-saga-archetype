package ec.com.dinersclub.saga.choreography.compensating;

import ec.com.dinersclub.saga.services.models.Petstore;

public interface ICancelCreatePetEvent {
	
	void generateEventHandler(Petstore pet);

}
