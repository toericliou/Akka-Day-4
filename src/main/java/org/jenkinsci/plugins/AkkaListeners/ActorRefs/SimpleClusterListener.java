package org.jenkinsci.plugins.AkkaListeners.ActorRefs;

import akka.actor.UntypedActor;
import akka.cluster.Cluster;
import akka.cluster.ClusterEvent;
import akka.cluster.ClusterEvent.*;
import akka.cluster.Member;
import akka.util.ByteStringBuilder;
import com.sun.mail.iap.ByteArray;
import lombok.Getter;
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
    @Getter
    private Cluster cluster = Cluster.get(getContext().system());
    private List<Member> members = new ArrayList<Member>();

    private static final String CLUSTER_PATH = "/user/clusterListener";

    @Getter
    private Object lastMsg;

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
            lastMsg = message;
        } else if (message instanceof UnreachableMember) {
            UnreachableMember mUnreachable = (UnreachableMember) message;
            log.info("Member detected as unreachable: {}", mUnreachable.member());
            lastMsg = message;

        } else if (message instanceof MemberRemoved) {
            MemberRemoved mRemoved = (MemberRemoved) message;
            log.info("Member is Removed: {}", mRemoved.member());
            members.remove(mRemoved.member());
            lastMsg = message;
        }  else if (message instanceof MemberEvent) {
            // ignore
        } else if (message instanceof ForwardedMessage){
            log.info("Message Sent");
            sendMessage((ForwardedMessage) message);
            lastMsg= message;
        } else if (message instanceof byte[]) {
            log.info("Message Being Processed");
            convertMessage((byte[]) message);
            lastMsg = message;
        } else {
            unhandled(message);
        }

    }

    private void sendMessage(ForwardedMessage message) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutput out = new ObjectOutputStream(bos);
        out.writeObject(message.getMessageContent());
        byte[] msgBytes = bos.toByteArray();
        for (Member member : members) {
            if (member.address().toString().equals("akka.tcp://ClusterSystem@127.0.0.1:2551")) {
                getContext().actorSelection(member.address() + CLUSTER_PATH).tell(msgBytes, getSender());
            }
        }
    }

    private void convertMessage(byte[] message) throws IOException, ClassNotFoundException {

        ByteArrayInputStream bis = new ByteArrayInputStream(message);
        ObjectInput in = new ObjectInputStream(bis);
        String s = (String) in.readObject();

        log.info("****************" + s + "******************");

    }

}
