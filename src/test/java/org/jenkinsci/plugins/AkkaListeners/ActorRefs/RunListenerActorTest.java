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
public class RunListenerActorTest {

    ActorSystem system = ActorSystem.apply();

    private TestActorRef<RunListenerActor> runActorRef;

    @Before
    public void setUp() throws Exception {
        runActorRef = TestActorRef.create(system, Props.create(RunListenerActor.class), "runTest");
    }

    @Test
    public void testOnRecieve(){
        runActorRef.tell("Run Started", ActorRef.noSender());
        assertEquals(runActorRef.underlyingActor().getMessage(), "Run Started");
        runActorRef.tell("Run Completed", ActorRef.noSender());
        assertEquals(runActorRef.underlyingActor().getMessage(), "Run Completed");
    }
}