package org.jenkinsci.plugins.AkkaListeners.AkkaListeners;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.testkit.TestActorRef;

import org.jenkinsci.plugins.AkkaListeners.ActorRefs.SavableListenerActor;
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
    AkkaSavableListener unitUnderTest;

    private TestActorRef<SavableListenerActor> savableActorRef;

    @Before
    public void setUp() throws Exception {
        savableActorRef = TestActorRef.create(system, Props.create(SavableListenerActor.class), "savableTest");
        //Mockito.mock(savableActorRef);
        //unitUnderTest =  new AkkaSavableListener();
        //unitUnderTest.setSavableActorRef(savableActorRef);
        //Mockito.doNothing().when(savableActorRef).tell("onChange", ActorRef.noSender());
    }

    /*@Test
    public void testChanged(){
        AkkaSavableListener listener = new AkkaSavableListener();
        listener.setSavableActorRef(savableActorRef);
        assertNotNull(listener.getSavableActorRef());
        listener.onChange(null, null);
        assertEquals("onChange", savableActorRef.underlyingActor().getLastMessage());
    }

    @Test
    public void testOnSaved(){
        savableActorRef.tell("onChange", ActorRef.noSender());
        assertEquals(savableActorRef.underlyingActor().getLastMessage(), "onChange");
    }

    /*@Test
    public void testChanged(){
        //AkkaSavableListener listener = new AkkaSavableListener();
        unitUnderTest.onChange(null, null);
        //Mockito.verify(savableActorRef, Mockito.times(1)).tell("onChange", ActorRef.noSender());
    }*/


}