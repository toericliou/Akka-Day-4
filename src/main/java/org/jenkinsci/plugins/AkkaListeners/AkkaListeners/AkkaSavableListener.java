package org.jenkinsci.plugins.AkkaListeners.AkkaListeners;

import akka.actor.ActorRef;
import hudson.Extension;
import hudson.XmlFile;
import hudson.model.Saveable;
import hudson.model.listeners.SaveableListener;
import lombok.Getter;
import lombok.Setter;
import org.jenkinsci.plugins.AkkaListeners.AkkaPlugin;


/**
 * Created by eerilio on 5/14/15.
 */
@Extension
public class AkkaSavableListener extends SaveableListener {

    @Getter
    @Setter
    private ActorRef savableActorRef = AkkaPlugin.getSavableListenerActorRef();

    @Override
    public void onChange(final Saveable o, final XmlFile file) {
        savableActorRef.tell("onChange", ActorRef.noSender());
    }
}
