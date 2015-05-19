package org.jenkinsci.plugins.AkkaListeners.AkkaListeners;

import akka.actor.ActorRef;
import hudson.Extension;
import hudson.XmlFile;
import hudson.model.Saveable;
import hudson.model.listeners.SaveableListener;
import lombok.Getter;
import lombok.Setter;
import org.jenkinsci.plugins.AkkaListeners.AkkaPlugin;
import org.jenkinsci.plugins.AkkaListeners.Message.ForwardedMessage;


/**
 * Created by eerilio on 5/14/15.
 */
@Extension
public class AkkaSavableListener extends SaveableListener {

    @Getter
    @Setter
    private ActorRef savableActorRef = AkkaPlugin.getSavableListenerActorRef();

    @Getter
    @Setter
    private ActorRef clusterActorRef = AkkaPlugin.getClusterListener();

    @Override
    public void onChange(final Saveable o, final XmlFile file) {
        savableActorRef.tell("onChange", ActorRef.noSender());
        clusterActorRef.tell(new ForwardedMessage("Items SAVED"), savableActorRef);

    }
}
