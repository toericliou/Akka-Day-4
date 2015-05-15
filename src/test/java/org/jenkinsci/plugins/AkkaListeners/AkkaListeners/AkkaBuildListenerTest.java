package org.jenkinsci.plugins.AkkaListeners.AkkaListeners;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.testkit.TestActorRef;
import org.apache.maven.model.Build;
import org.jenkinsci.plugins.AkkaListeners.ActorRefs.BuildListenerActor;
import org.jenkinsci.plugins.AkkaListeners.ActorRefs.ItemListenerActor;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by eerilio on 5/15/15.
 */
public class AkkaBuildListenerTest {

    ActorSystem system = ActorSystem.apply();

    private TestActorRef<BuildListenerActor> buildActorRef;

    @Before
    public void setUp() throws Exception {
        buildActorRef = TestActorRef.create(system, Props.create(BuildListenerActor.class), "buildTest");
    }

    /*@Test
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
        assertNotNull(listener.getBuildListenerActorRef());
        listener.started(null);
        assertEquals("Started", buildActorRef.underlyingActor().getMessage());
    }

    @Test
    public void testFinished(){
        AkkaBuildListener listener = new AkkaBuildListener();
        listener.setBuildListenerActorRef(buildActorRef);
        assertNotNull(listener.getBuildListenerActorRef());
        listener.finished(null);
        assertEquals("Finished", buildActorRef.underlyingActor().getMessage());
    }*/
}