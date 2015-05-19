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
public class ItemListenerActorTest {

    ActorSystem system = ActorSystem.apply();

    private TestActorRef<ItemListenerActor> itemActorRef;

    @Before
    public void setUp(){
        itemActorRef = TestActorRef.create(system, Props.create(ItemListenerActor.class), "itemTest");
    }

    @Test
    public void testOnReceive() throws Exception {
        itemActorRef.tell("Item Created", ActorRef.noSender());
        assertEquals(itemActorRef.underlyingActor().getMessage(), "Item Created");
        itemActorRef.tell("Item Deleted", ActorRef.noSender());
        assertEquals(itemActorRef.underlyingActor().getMessage(), "Item Deleted");
        itemActorRef.tell("Item Renamed", ActorRef.noSender());
        assertEquals(itemActorRef.underlyingActor().getMessage(), "Item Renamed");
    }
}