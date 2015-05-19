package org.jenkinsci.plugins.AkkaListeners.ActorRefs;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.testkit.TestActorRef;
import org.jenkinsci.plugins.AkkaListeners.Message.ForwardedMessage;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by eerilio on 5/19/15.
 */
public class SimpleClusterListenerTest {

    ActorSystem system = ActorSystem.apply();

    private TestActorRef<SimpleClusterListener> unitUnderTest;


    @Before
    public void setUp() throws Exception {
        unitUnderTest = TestActorRef.create(system, Props.create(SimpleClusterListener.class), "clusterTest");
    }

    @Test
    public void testPreStart(){
        assertFalse(unitUnderTest.underlyingActor().getCluster().getSelfRoles().contains(unitUnderTest));
        unitUnderTest.underlyingActor().preStart();
    }

    @Test
    public void testPostStop(){
        unitUnderTest.underlyingActor().postStop();
        assertTrue(unitUnderTest.underlyingActor().getCluster().isTerminated());
    }

    @Test
    public void testOnReceive() throws Exception {
        unitUnderTest.tell(new ForwardedMessage("TestMessage"), ActorRef.noSender());
        assertEquals(unitUnderTest.underlyingActor().getLastMsg().getClass(), ForwardedMessage.class);
        byte[] bytes = "Test Message".getBytes();
        unitUnderTest.tell(bytes, ActorRef.noSender());
        System.out.print("*********************************************"+unitUnderTest.underlyingActor().getLastMsg().getClass());
    }
}