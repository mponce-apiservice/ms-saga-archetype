package ec.com.dinersclub.saga.orchestrations.transactions;

import ec.com.dinersclub.saga.orchestrations.transactions.models.Pet;
import ec.com.dinersclub.saga.services.models.Petstore;

public interface ICreatePetSaga {

	Petstore createPet(Pet pet);
	
}
