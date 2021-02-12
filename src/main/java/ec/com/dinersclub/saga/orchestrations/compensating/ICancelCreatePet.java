package ec.com.dinersclub.saga.orchestrations.compensating;

import ec.com.dinersclub.saga.services.models.Petstore;

public interface ICancelCreatePet {
	
	void compensatingCreatePet(Petstore pet);

}
