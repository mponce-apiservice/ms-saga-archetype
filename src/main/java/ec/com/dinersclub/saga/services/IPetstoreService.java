package ec.com.dinersclub.saga.services;

import ec.com.dinersclub.saga.orchestrations.transactions.models.Pet;

public interface IPetstoreService {
	
	void createPet(Pet pet);
	
	void removePet(String id, String petId);

}
