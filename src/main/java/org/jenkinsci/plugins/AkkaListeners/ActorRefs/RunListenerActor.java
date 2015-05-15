package org.jenkinsci.plugins.AkkaListeners.ActorRefs;

import akka.actor.UntypedActor;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;


/**
 * Created by eerilio on 5/14/15.
 */
@Log4j2
public class RunListenerActor extends UntypedActor {
    @Getter
    private Object message;
    @Override
    public void onReceive(Object o) throws Exception {

        if(o.equals("Run Started")) {
            log.info("Run Listener Triggered and Started");
            message=o;
            getSender().tell("Done", getSelf());
        }

        if(o.equals("Run Completed")) {
            log.info("Run Listener Completed");
            message=o;
            getSender().tell("Done", getSelf());
        }

    }
}
