package org.jenkinsci.plugins.AkkaListeners.ActorRefs;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Address;
import akka.actor.Props;
import akka.cluster.ClusterEvent;
import akka.cluster.Member;
import akka.cluster.MemberStatus;
import akka.cluster.UniqueAddress;
import akka.testkit.TestActorRef;
import org.jenkinsci.plugins.AkkaListeners.Message.ForwardedMessage;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.*;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

/**
 * Created by eerilio on 5/19/15.
 */
@RunWith(MockitoJUnitRunner.class) public class SimpleClusterListenerTest {

    ActorSystem system = ActorSystem.apply();

    private TestActorRef<SimpleClusterListener> unitUnderTest;

    @Mock Member mockClusterMember;
    @Mock ClusterEvent.MemberRemoved mockClusterMemberRemovedMsg;
    @Mock ClusterEvent.MemberUp mockClusterMemberUpMsg;
    @Mock ClusterEvent.UnreachableMember mockUnreachableEvent;

    @Before
    public void setUp() throws Exception {
        unitUnderTest = TestActorRef.create(system, Props.create(SimpleClusterListener.class), "clusterTest");
        Address myAddress = new Address("mock", "mock");
        when(mockClusterMember.address()).thenReturn(myAddress);
        when(mockClusterMemberUpMsg.member()).thenReturn(mockClusterMember);
        when(mockClusterMemberRemovedMsg.member()).thenReturn(mockClusterMember);
    }

    @Test
    public void testRemovedOrAdded() throws Exception {
        akka.pattern.Patterns.ask(unitUnderTest, mockClusterMemberUpMsg, 3000);
        akka.pattern.Patterns.ask(unitUnderTest, mockClusterMemberRemovedMsg, 3000);
        system.shutdown();
    }

    @Test
    public void testUnreachable() throws Exception {
        akka.pattern.Patterns.ask(unitUnderTest, mockUnreachableEvent, 3000);
        system.shutdown();
    }

    @Test
    public void messageRecieve() throws Exception{
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutput out = new ObjectOutputStream(bos);
        out.writeObject(new ForwardedMessage("test"));
        byte[] msgBytes = bos.toByteArray();
        akka.pattern.Patterns.ask(unitUnderTest, msgBytes, 3000);
        system.shutdown();
    }

    @Test
    public void messageSend() throws Exception{
        akka.pattern.Patterns.ask(unitUnderTest, new ForwardedMessage("test"), 3000);
        system.shutdown();
    }

}