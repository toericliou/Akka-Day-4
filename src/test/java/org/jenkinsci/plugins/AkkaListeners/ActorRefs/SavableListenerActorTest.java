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
public class SavableListenerActorTest {

    ActorSystem system = ActorSystem.apply();

    private TestActorRef<SavableListenerActor> savableActorRef;

    @Before
    public void setUp() throws Exception {
        savableActorRef = TestActorRef.create(system, Props.create(SavableListenerActor.class), "savableTest");
    }

    @Test
    public void onRecieve(){
        savableActorRef.tell("onChange", ActorRef.noSender());
        assertEquals(savableActorRef.underlyingActor().getLastMessage(), "onChange");
        system.shutdown();
    }
}