package org.jenkinsci.plugins.AkkaListeners.AkkaListeners;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.testkit.TestActorRef;
import org.jenkinsci.plugins.AkkaListeners.ActorRefs.ItemListenerActor;
import org.jenkinsci.plugins.AkkaListeners.ActorRefs.RunListenerActor;
import org.jenkinsci.plugins.AkkaListeners.ActorRefs.SimpleClusterListener;
import org.jenkinsci.plugins.AkkaListeners.Message.ForwardedMessage;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by eerilio on 5/15/15.
 */
public class AkkaRunListenerTest {

    ActorSystem system = ActorSystem.apply();

    private TestActorRef<RunListenerActor> runActorRef;

    private TestActorRef<SimpleClusterListener> clusterActorRef;

    @Before
    public void setUp() throws Exception {
        runActorRef = TestActorRef.create(system, Props.create(RunListenerActor.class), "runTest");
        clusterActorRef = TestActorRef.create(system, Props.create(SimpleClusterListener.class), "clusterTest");
    }

    @Test
    public void testOnStart(){
        AkkaRunListener listener = new AkkaRunListener();
        listener.setRunListener(runActorRef);
        listener.setClusterActorRef(clusterActorRef);
        assertNotNull(listener.getRunListener());
        assertNotNull(listener.getClusterActorRef());
        listener.onStarted(null, null);
        assertEquals(runActorRef.underlyingActor().getMessage(), "Run Started");
        assertEquals(clusterActorRef.underlyingActor().getLastMsg().getClass(), ForwardedMessage.class);
        system.shutdown();
    }

    @Test
    public void testOnCompleted(){
        AkkaRunListener listener = new AkkaRunListener();
        listener.setRunListener(runActorRef);
        listener.setClusterActorRef(clusterActorRef);
        assertNotNull(listener.getRunListener());
        assertNotNull(listener.getClusterActorRef());
        listener.onCompleted(null, null);
        assertEquals("Run Completed", runActorRef.underlyingActor().getMessage());
        assertEquals(clusterActorRef.underlyingActor().getLastMsg().getClass(), ForwardedMessage.class);
        system.shutdown();
    }

    @After
    public void tearDown(){
        system.shutdown();
    }
}