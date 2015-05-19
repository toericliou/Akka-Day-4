package org.jenkinsci.plugins.AkkaListeners.AkkaListeners;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.testkit.TestActorRef;
import akka.testkit.TestProbe;
import com.sun.mail.imap.protocol.Item;
import org.jenkinsci.plugins.AkkaListeners.ActorRefs.ItemListenerActor;
import org.jenkinsci.plugins.AkkaListeners.ActorRefs.SavableListenerActor;
import org.jenkinsci.plugins.AkkaListeners.ActorRefs.SimpleClusterListener;
import org.jenkinsci.plugins.AkkaListeners.Message.ForwardedMessage;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by eerilio on 5/15/15.
 */

//Tests for ItemActor and ItemListener
public class AkkaItemListenerTest {

    ActorSystem system = ActorSystem.apply();

    private TestActorRef<ItemListenerActor> itemActorRef;

    private TestActorRef<SimpleClusterListener> clusterActorRef;

    @Before
    public void setUp() throws Exception {
        itemActorRef = TestActorRef.create(system, Props.create(ItemListenerActor.class), "runTest");
        clusterActorRef = TestActorRef.create(system, Props.create(SimpleClusterListener.class), "clusterTest");
    }

    @Test
    public void testCreated() throws Exception{
        AkkaItemListener listener = new AkkaItemListener();
        listener.setItemActorRef(itemActorRef);
        listener.setClusterActorRef(clusterActorRef);
        assertNotNull(listener.getItemActorRef());
        assertNotNull(listener.getClusterActorRef());
        listener.onCreated(null);
        assertEquals(itemActorRef.underlyingActor().getMessage(), "Item Created");
        assertEquals(clusterActorRef.underlyingActor().getLastMsg().getClass(), ForwardedMessage.class);
        system.shutdown();
    }

    @Test
    public void testDeleted(){
        AkkaItemListener listener = new AkkaItemListener();
        listener.setItemActorRef(itemActorRef);
        listener.setClusterActorRef(clusterActorRef);
        assertNotNull(listener.getItemActorRef());
        assertNotNull(listener.getClusterActorRef());
        listener.onDeleted(null);
        assertEquals(itemActorRef.underlyingActor().getMessage(), "Item Deleted");
        assertEquals(clusterActorRef.underlyingActor().getLastMsg().getClass(), ForwardedMessage.class);
        system.shutdown();
    }

    @Test
    public void testRenamed(){
        AkkaItemListener listener = new AkkaItemListener();
        listener.setItemActorRef(itemActorRef);
        listener.setClusterActorRef(clusterActorRef);
        assertNotNull(listener.getItemActorRef());
        assertNotNull(listener.getClusterActorRef());
        listener.onRenamed(null, null, null);
        assertEquals(itemActorRef.underlyingActor().getMessage(), "Item Renamed");
        assertEquals(clusterActorRef.underlyingActor().getLastMsg().getClass(), ForwardedMessage.class);
        system.shutdown();
    }

    @After
    public void tearDown(){
        system.shutdown();
    }


}