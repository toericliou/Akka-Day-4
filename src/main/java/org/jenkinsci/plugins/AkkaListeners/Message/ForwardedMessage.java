package org.jenkinsci.plugins.AkkaListeners.Message;

import lombok.Getter;

/**
 * Created by eerilio on 5/15/15.
 */
public class ForwardedMessage implements MyClusterMessage {

    private static final long serialVersionUID = -8462119854777759534L;

    @Getter
    private String destAddress;
    @Getter
    private Object messageContent;

    public ForwardedMessage(Object messageContent) {
        this.messageContent = messageContent;
    }

    public ForwardedMessage(String destAddress, Object messageContent) {
        this.destAddress = destAddress;
        this.messageContent = messageContent;
    }
}
