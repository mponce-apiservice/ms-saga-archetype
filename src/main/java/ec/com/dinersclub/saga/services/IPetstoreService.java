package ec.com.dinersclub.saga.services;

import ec.com.dinersclub.saga.services.models.Petstore;
import ec.com.dinersclub.saga.services.models.PetstoreDelete;

public interface IPetstoreService {
	
	public Petstore createPet(Petstore pet);
	
	public void getCountry(Petstore pet);
	
	public Petstore updatePet(Petstore pet);
	
	public PetstoreDelete removePet(Petstore pet);

}
