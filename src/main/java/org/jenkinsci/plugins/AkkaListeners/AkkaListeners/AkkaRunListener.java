package org.jenkinsci.plugins.AkkaListeners.AkkaListeners;

import akka.actor.ActorRef;
import hudson.Extension;
import hudson.model.Run;
import hudson.model.TaskListener;
import hudson.model.listeners.RunListener;
import lombok.Getter;
import lombok.Setter;
import org.jenkinsci.plugins.AkkaListeners.AkkaPlugin;
import org.jenkinsci.plugins.AkkaListeners.Message.ForwardedMessage;


/**
 * Created by eerilio on 5/14/15.
 */
@Extension
public class AkkaRunListener<R extends Run> extends RunListener<R> {
    @Getter
    @Setter
    private ActorRef runListener = AkkaPlugin.getRunListenerActorRef();

    @Getter
    @Setter
    private ActorRef clusterActorRef = AkkaPlugin.getClusterListener();

    @Override
    public void onCompleted(R run, TaskListener listener) {
        super.onCompleted(run, listener);
        runListener.tell("Run Completed", ActorRef.noSender());
        clusterActorRef.tell(new ForwardedMessage("RUN COMPLETED"), runListener);
    }

    @Override
    public void onStarted(R run, TaskListener listener) {
        super.onStarted(run, listener);
        runListener.tell("Run Started", ActorRef.noSender());
        clusterActorRef.tell(new ForwardedMessage("RUN STARTED"), runListener);
    }
}
