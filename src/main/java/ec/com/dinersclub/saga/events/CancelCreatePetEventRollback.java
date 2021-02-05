package ec.com.dinersclub.saga.events;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.eclipse.microprofile.reactive.messaging.Message;

import ec.com.dinersclub.saga.events.models.Rollback;

@ApplicationScoped
public class CancelCreatePetEventRollback implements ICancelCreatePetEventRollback {
	
	@Inject @Channel("generated-saga-rollback") Emitter<Rollback> rollbackEmitter;
	
    public void generateEventHandler(Rollback rollback) {
    	rollbackEmitter.send(Message.of(rollback));
    }

}
