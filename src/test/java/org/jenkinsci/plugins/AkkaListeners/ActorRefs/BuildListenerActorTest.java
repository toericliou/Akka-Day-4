package org.jenkinsci.plugins.AkkaListeners.ActorRefs;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.testkit.TestActorRef;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by eerilio on 5/19/15.
 */
public class BuildListenerActorTest {

    ActorSystem system = ActorSystem.apply();

    private TestActorRef<BuildListenerActor> buildActorRef;

    @Before
    public void setUp() throws Exception {
        buildActorRef = TestActorRef.create(system, Props.create(BuildListenerActor.class), "buildTest");
    }

    @Test
    public void testOnRecieve(){
        buildActorRef.tell("Started", ActorRef.noSender());
        assertEquals(buildActorRef.underlyingActor().getMessage(), "Started");
        buildActorRef.tell("Finished", ActorRef.noSender());
        assertEquals(buildActorRef.underlyingActor().getMessage(), "Finished");
        system.shutdown();
    }
}