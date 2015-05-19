package org.jenkinsci.plugins.AkkaListeners;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import hudson.Extension;
import hudson.Plugin;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import org.jenkinsci.plugins.AkkaListeners.ActorRefs.*;


/**
 * Created by eerilio on 5/14/15.
 */
@Log4j2
public class AkkaPlugin extends Plugin {

    private final ActorSystem system = ActorSystem.create("ClusterSystem");

    @Getter
    private static ActorRef itemListenerActorRef;

    @Getter
    private static ActorRef runListenerActorRef;

    @Getter
    private static ActorRef savableListenerActorRef;

    @Getter
    private static ActorRef buildListenerActorRef;

    @Getter
    private static ActorRef clusterListener;

    @Override
    public void start(){
        log.info("Plugin Start");
        //startup(new String[]{"2552"});
        clusterListener = system.actorOf(Props.create(SimpleClusterListener.class), "clusterListener");

        log.info("Startup Called: Ports configured");
        itemListenerActorRef = system.actorOf(Props.create(ItemListenerActor.class));
        runListenerActorRef = system.actorOf(Props.create(RunListenerActor.class));
        savableListenerActorRef = system.actorOf(Props.create(SavableListenerActor.class));
        buildListenerActorRef = system.actorOf(Props.create(BuildListenerActor.class));
        log.info("Listeners Created");
    }

    private void startup(String[] ports){
        for (String port : ports) {
            // Override the configuration of the port

            // Create an Akka system

        }
    }

    @Override
    public void stop() throws Exception {
        super.stop();
        system.shutdown();
    }
}
