package ec.com.dinersclub.saga.orchestrations.compensating;

public interface ICancelCreatePet {
	
	void compensatingCreatePet(int id);

}
