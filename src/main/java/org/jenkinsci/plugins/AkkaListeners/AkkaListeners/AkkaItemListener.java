package org.jenkinsci.plugins.AkkaListeners.AkkaListeners;

import akka.actor.*;
import hudson.Extension;
import hudson.model.*;
import hudson.model.listeners.ItemListener;
import lombok.Getter;
import lombok.Setter;
import org.jenkinsci.plugins.AkkaListeners.AkkaPlugin;
import org.jenkinsci.plugins.AkkaListeners.Message.ForwardedMessage;

import java.util.List;


/**
 * Created by eerilio on 5/14/15.
 */
@Extension
public class AkkaItemListener extends ItemListener {

    @Getter
    @Setter
    private ActorRef itemActorRef = AkkaPlugin.getItemListenerActorRef();

    @Getter
    @Setter
    private ActorRef clusterActorRef = AkkaPlugin.getClusterListener();


    @Override
    public void onCreated(Item item) {
        super.onCreated(item);
        itemActorRef.tell("Item Created", itemActorRef);
        clusterActorRef.tell(new ForwardedMessage("Item Created"), itemActorRef);
    }

    @Override
    public void onDeleted(Item item) {
        super.onDeleted(item);
        itemActorRef.tell("Item Deleted", ActorRef.noSender());
        clusterActorRef.tell(new ForwardedMessage("Item Deleted"), itemActorRef);

    }

    @Override
    public void onRenamed(Item item, String oldName, String newName) {
        super.onRenamed(item, oldName, newName);
        itemActorRef.tell("Item Renamed", ActorRef.noSender());
        clusterActorRef.tell(new ForwardedMessage("Item Renamed"), itemActorRef);
    }
}
