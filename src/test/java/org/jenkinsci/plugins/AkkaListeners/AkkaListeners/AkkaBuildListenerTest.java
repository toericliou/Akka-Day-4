package org.jenkinsci.plugins.AkkaListeners.AkkaListeners;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.testkit.TestActorRef;
import org.apache.maven.model.Build;
import org.jenkinsci.plugins.AkkaListeners.ActorRefs.BuildListenerActor;
import org.jenkinsci.plugins.AkkaListeners.ActorRefs.ItemListenerActor;
import org.jenkinsci.plugins.AkkaListeners.ActorRefs.SimpleClusterListener;
import org.jenkinsci.plugins.AkkaListeners.Message.ForwardedMessage;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by eerilio on 5/15/15.
 */
public class AkkaBuildListenerTest {

    ActorSystem system = ActorSystem.apply();

    private TestActorRef<BuildListenerActor> buildActorRef;
    private TestActorRef<SimpleClusterListener> clusterActorRef;

    @Before
    public void setUp() throws Exception {
        buildActorRef = TestActorRef.create(system, Props.create(BuildListenerActor.class), "buildTest");
        clusterActorRef = TestActorRef.create(system, Props.create(SimpleClusterListener.class), "clusterTest");
    }

    @Test
    public void testOnRecieve(){
        buildActorRef.tell("Started", ActorRef.noSender());
        assertEquals(buildActorRef.underlyingActor().getMessage(), "Started");
        buildActorRef.tell("Finished", ActorRef.noSender());
        assertEquals(buildActorRef.underlyingActor().getMessage(), "Finished");
    }

    @Test
    public void testStarted(){
        AkkaBuildListener listener = new AkkaBuildListener();
        listener.setBuildListenerActorRef(buildActorRef);
        listener.setClusterActorRef(clusterActorRef);
        assertNotNull(listener.getBuildListenerActorRef());
        assertNotNull(listener.getClusterActorRef());
        listener.started(null);
        assertEquals("Started", buildActorRef.underlyingActor().getMessage());
        assertEquals(clusterActorRef.underlyingActor().getLastMsg().getClass(), ForwardedMessage.class);
        system.shutdown();
    }

    @Test
    public void testFinished(){
        AkkaBuildListener listener = new AkkaBuildListener();
        listener.setBuildListenerActorRef(buildActorRef);
        listener.setClusterActorRef(clusterActorRef);
        assertNotNull(listener.getBuildListenerActorRef());
        assertNotNull(listener.getClusterActorRef());
        listener.finished(null);
        assertEquals("Finished", buildActorRef.underlyingActor().getMessage());
        assertEquals(clusterActorRef.underlyingActor().getLastMsg().getClass(), ForwardedMessage.class);
        system.shutdown();
    }

    @After
    public void tearDown(){
        system.shutdown();
    }
}