package org.jenkinsci.plugins.AkkaListeners.ActorRefs;

import akka.actor.UntypedActor;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;


/**
 * Created by eerilio on 5/14/15.
 */

@Log4j2
public class ItemListenerActor extends UntypedActor {

    @Getter
    private Object message;

    @Override
    public void onReceive(Object o) throws Exception {
        //LOG.info("Item Listener Received");
        if(o.equals("Item Created")){
            log.info("Item Listener Recieved: Item Created");
            message=o;
            getSender().tell("Done", getSelf());
        }
        else if(o.equals("Item Deleted")){
            log.info("Item Listener Recieved: Item Deleted");
            message=o;
            getSender().tell("Done", getSelf());
        }
        if(o.equals("Item Renamed")){
            log.info("Item Listener Recieved: Item Renamed");
            message=o;
            getSender().tell("Done", getSelf());
        }
    }
}
