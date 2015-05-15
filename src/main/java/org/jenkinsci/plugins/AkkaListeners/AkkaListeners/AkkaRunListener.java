package org.jenkinsci.plugins.AkkaListeners.AkkaListeners;

import akka.actor.ActorRef;
import hudson.Extension;
import hudson.model.Run;
import hudson.model.TaskListener;
import hudson.model.listeners.RunListener;
import lombok.Getter;
import lombok.Setter;
import org.jenkinsci.plugins.AkkaListeners.AkkaPlugin;


/**
 * Created by eerilio on 5/14/15.
 */
@Extension
public class AkkaRunListener<R extends Run> extends RunListener<R> {
    @Getter
    @Setter
    private ActorRef runListener = AkkaPlugin.getRunListenerActorRef();

    @Override
    public void onCompleted(R run, TaskListener listener) {
        super.onCompleted(run, listener);
        runListener.tell("Run Completed", ActorRef.noSender());
    }

    @Override
    public void onStarted(R run, TaskListener listener) {
        super.onStarted(run, listener);
        runListener.tell("Run Started", ActorRef.noSender());
    }
}
