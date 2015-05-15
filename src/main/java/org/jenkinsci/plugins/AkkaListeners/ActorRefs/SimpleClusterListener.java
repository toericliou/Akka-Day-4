package org.jenkinsci.plugins.AkkaListeners.ActorRefs;

import akka.actor.UntypedActor;
import akka.cluster.Cluster;
import akka.cluster.ClusterEvent;
import akka.cluster.ClusterEvent.*;
import akka.cluster.Member;
import akka.util.ByteStringBuilder;
import com.sun.mail.iap.ByteArray;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.io.IOUtils;
import org.jenkinsci.plugins.AkkaListeners.Message.ForwardedMessage;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by eerilio on 5/15/15.
 */

@Log4j2
public class SimpleClusterListener extends UntypedActor{

    Cluster cluster = Cluster.get(getContext().system());
    private List<Member> members = new ArrayList<Member>();

    private static final String CLUSTER_PATH = "/user/clusterListener";

    @Override
    public void preStart(){
        cluster.subscribe(getSelf(), ClusterEvent.initialStateAsEvents(),
                MemberEvent.class, UnreachableMember.class);
    }

    @Override
    public void postStop() {
        cluster.unsubscribe(getSelf());
    }

    @Override
    public void onReceive(Object message) throws Exception {
        if (message instanceof MemberUp) {
            MemberUp mUp = (MemberUp) message;
            members.add(mUp.member());
            log.info("Member is Up: {}", mUp.member());
        } else if (message instanceof UnreachableMember) {
            UnreachableMember mUnreachable = (UnreachableMember) message;
            log.info("Member detected as unreachable: {}", mUnreachable.member());

        } else if (message instanceof MemberRemoved) {
            MemberRemoved mRemoved = (MemberRemoved) message;
            log.info("Member is Removed: {}", mRemoved.member());
            members.remove(mRemoved.member());
        } else if (message instanceof byte[]) {
                log.info("Message Being Processed");
                convertMessage((byte[]) message);
        } else if (message instanceof MemberEvent) {
            // ignore
        } else if (message instanceof ForwardedMessage){
            log.info("Message Sent");
            sendMessage((ForwardedMessage) message);
        }  else {
            unhandled(message);
        }

    }

    private void sendMessage(ForwardedMessage message) throws IOException {
        ByteArrayOutputStream b = new ByteArrayOutputStream();
        ObjectOutput out = new ObjectOutputStream(b);
        out.writeObject(message.getMessageContent());
        byte[] msgBytes = b.toByteArray();

        for (Member member : members) {
            if (allowForward(member, message.getDestAddress())) {
                getContext().actorSelection(member.address() + CLUSTER_PATH)
                        .tell(msgBytes, getSender());
            }
        }
    }

    private void convertMessage(byte[] message) throws IOException, ClassNotFoundException {
        ByteArrayInputStream b = new ByteArrayInputStream(message);
        byte[] bytes = IOUtils.toByteArray(b);
        String passedMsg = new String(bytes);

        log.info("****************" + passedMsg + "******************");
    }
    private boolean allowForward(Member member, String destAddress) {
        if (destAddress == null) {
            return true;
        } else if (destAddress.equals(member.address().toString())) {
            return true;
        }
        return false;
    }

}
