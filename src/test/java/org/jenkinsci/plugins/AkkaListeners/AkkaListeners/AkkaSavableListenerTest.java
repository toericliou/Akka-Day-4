package org.jenkinsci.plugins.AkkaListeners.AkkaListeners;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.testkit.TestActorRef;

import org.jenkinsci.plugins.AkkaListeners.ActorRefs.SavableListenerActor;
import org.jenkinsci.plugins.AkkaListeners.ActorRefs.SimpleClusterListener;
import org.jenkinsci.plugins.AkkaListeners.Message.ForwardedMessage;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import lombok.extern.log4j.Log4j2;


import static org.junit.Assert.*;

/**
 * Created by eerilio on 5/14/15.
 */
@Log4j2
//Test SavableListener and SavableActor
public class AkkaSavableListenerTest {

    ActorSystem system = ActorSystem.apply();

    private TestActorRef<SavableListenerActor> savableActorRef;
    private TestActorRef<SimpleClusterListener> clusterActorRef;

    @Before
    public void setUp() throws Exception {
        savableActorRef = TestActorRef.create(system, Props.create(SavableListenerActor.class), "savableTest");
        clusterActorRef = TestActorRef.create(system, Props.create(SimpleClusterListener.class), "clusterTest");
        //Mockito.mock(savableActorRef);
        //unitUnderTest =  new AkkaSavableListener();
        //unitUnderTest.setSavableActorRef(savableActorRef);
        //Mockito.doNothing().when(savableActorRef).tell("onChange", ActorRef.noSender());
    }

    @Test
    public void testChanged(){
        AkkaSavableListener listener = new AkkaSavableListener();
        listener.setSavableActorRef(savableActorRef);
        listener.setClusterActorRef(clusterActorRef);
        assertNotNull(listener.getSavableActorRef());
        assertNotNull(listener.getClusterActorRef());
        listener.onChange(null, null);
        assertEquals(savableActorRef.underlyingActor().getLastMessage(), "onChange");
        assertEquals(clusterActorRef.underlyingActor().getLastMsg().getClass(), ForwardedMessage.class);
        system.shutdown();
    }

    @After
    public void tearDown(){
        system.shutdown();
    }

}