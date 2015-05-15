package org.jenkinsci.plugins.AkkaListeners.ActorRefs;

import akka.actor.UntypedActor;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;


/**
 * Created by eerilio on 5/14/15.
 */
@Log4j2
public class BuildListenerActor extends UntypedActor {

    @Getter
    private Object message;

    @Override
    public void onReceive(Object o) throws Exception {
        if(o.equals("Started")){
            log.info("Build Listener Triggered: Build Started");
            message=o;
            getSender().tell("Done", getSelf());
        }
        else if (o.equals("Finished")){
            log.info("Build Listener Triggered: Build Finished");
            message=o;
            getSender().tell("Done", getSelf());
        }
    }
}
