package org.jenkinsci.plugins.AkkaListeners.AkkaListeners;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.testkit.TestActorRef;
import org.jenkinsci.plugins.AkkaListeners.ActorRefs.ItemListenerActor;
import org.jenkinsci.plugins.AkkaListeners.ActorRefs.RunListenerActor;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by eerilio on 5/15/15.
 */
public class AkkaRunListenerTest {

    ActorSystem system = ActorSystem.apply();

    private TestActorRef<RunListenerActor> runActorRef;

    @Before
    public void setUp() throws Exception {
        runActorRef = TestActorRef.create(system, Props.create(RunListenerActor.class), "runTest");
    }

    /*@Test
    public void testOnRecieve(){
        runActorRef.tell("Run Started", ActorRef.noSender());
        assertEquals(runActorRef.underlyingActor().getMessage(), "Run Started");
        runActorRef.tell("Run Completed", ActorRef.noSender());
        assertEquals(runActorRef.underlyingActor().getMessage(), "Run Completed");
    }

    @Test
    public void testOnStart(){
        AkkaRunListener listener = new AkkaRunListener();
        listener.setRunListener(runActorRef);
        assertNotNull(listener.getRunListener());
        listener.onStarted(null,null);
        assertEquals("Run Started", runActorRef.underlyingActor().getMessage());
    }

    @Test
    public void testOnCompleted(){
        AkkaRunListener listener = new AkkaRunListener();
        listener.setRunListener(runActorRef);
        assertNotNull(listener.getRunListener());
        listener.onCompleted(null, null);
        assertEquals("Run Completed", runActorRef.underlyingActor().getMessage());
    }*/
}