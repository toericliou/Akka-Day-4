package org.jenkinsci.plugins.AkkaListeners.AkkaListeners;

import akka.actor.ActorRef;
import hudson.Extension;
import hudson.console.ConsoleNote;
import hudson.model.BuildListener;
import hudson.model.Cause;
import hudson.model.Result;
import lombok.Getter;
import lombok.Setter;
import org.jenkinsci.plugins.AkkaListeners.AkkaPlugin;
import org.jenkinsci.plugins.AkkaListeners.Message.ForwardedMessage;

import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.List;

/**
 * Created by eerilio on 5/14/15.
 */
@Extension
public class AkkaBuildListener implements BuildListener {

    @Getter
    @Setter
    private ActorRef buildListenerActorRef = AkkaPlugin.getBuildListenerActorRef();

    @Getter
    @Setter
    private ActorRef clusterActorRef = AkkaPlugin.getClusterListener();

    @Override
    public void started(List<Cause> list) {
        buildListenerActorRef.tell("Started", ActorRef.noSender());
        clusterActorRef.tell(new ForwardedMessage("Build Started"), buildListenerActorRef);
    }

    @Override
    public void finished(Result result) {
        buildListenerActorRef.tell("Finished", ActorRef.noSender());
        clusterActorRef.tell(new ForwardedMessage("Build Finished"), buildListenerActorRef);
    }

    @Override
    public PrintStream getLogger() {
        return null;
    }

    @Override
    public void annotate(ConsoleNote consoleNote) throws IOException {

    }

    @Override
    public void hyperlink(String s, String s1) throws IOException {

    }

    @Override
    public PrintWriter error(String s) {
        return null;
    }

    @Override
    public PrintWriter error(String s, Object... objects) {
        return null;
    }

    @Override
    public PrintWriter fatalError(String s) {
        return null;
    }

    @Override
    public PrintWriter fatalError(String s, Object... objects) {
        return null;
    }
}
