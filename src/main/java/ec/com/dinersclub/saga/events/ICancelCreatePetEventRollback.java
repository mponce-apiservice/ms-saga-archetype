package ec.com.dinersclub.saga.events;

import ec.com.dinersclub.saga.events.models.Rollback;

public interface ICancelCreatePetEventRollback {
	
	void generateEventHandler(Rollback rollback);

}
