package org.jenkinsci.plugins.AkkaListeners.AkkaListeners;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.testkit.TestActorRef;
import org.jenkinsci.plugins.AkkaListeners.ActorRefs.ItemListenerActor;
import org.jenkinsci.plugins.AkkaListeners.ActorRefs.SavableListenerActor;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by eerilio on 5/15/15.
 */

//Tests for ItemActor and ItemListener
public class AkkaItemListenerTest {

    ActorSystem system = ActorSystem.apply();

    private TestActorRef<ItemListenerActor> itemActorRef;

    @Before
    public void setUp() throws Exception {
        itemActorRef = TestActorRef.create(system, Props.create(ItemListenerActor.class), "itemTest");
    }

    /*@Test
    //Test Actor onReceive
    public void testOnRecieve(){
        itemActorRef.tell("Item Created", ActorRef.noSender());
        assertEquals(itemActorRef.underlyingActor().getMessage(), "Item Created");
        itemActorRef.tell("Item Deleted", ActorRef.noSender());
        assertEquals(itemActorRef.underlyingActor().getMessage(), "Item Deleted");
        itemActorRef.tell("Item Renamed", ActorRef.noSender());
        assertEquals(itemActorRef.underlyingActor().getMessage(), "Item Renamed");
    }

    @Test
    public void testCreated(){
        AkkaItemListener listener = new AkkaItemListener();
        listener.setItemActorRef(itemActorRef);
        assertNotNull(listener.getItemActorRef());
        listener.onCreated(null);
        assertEquals("Item Created", itemActorRef.underlyingActor().getMessage());
    }

    @Test
    public void testDeleted(){
        AkkaItemListener listener = new AkkaItemListener();
        listener.setItemActorRef(itemActorRef);
        assertNotNull(listener.getItemActorRef());
        listener.onDeleted(null);
        assertEquals("Item Deleted", itemActorRef.underlyingActor().getMessage());
    }

    @Test
    public void testRenamed(){
        AkkaItemListener listener = new AkkaItemListener();
        listener.setItemActorRef(itemActorRef);
        assertNotNull(listener.getItemActorRef());
        listener.onRenamed(null, null, null);
        assertEquals("Item Renamed", itemActorRef.underlyingActor().getMessage());
    }*/


}