package org.jenkinsci.plugins.AkkaListeners.ActorRefs;

import akka.actor.UntypedActor;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;


/**
 * Created by eerilio on 5/14/15.
 */
@Log4j2
public class SavableListenerActor extends UntypedActor {

    @Getter
    private Object lastMessage;

    @Override
    public void onReceive(Object o) throws Exception {
        log.info("Savable Listener Recieved: Saved");
        lastMessage = o;
        getSender().tell("Done", getSelf());
    }
}
