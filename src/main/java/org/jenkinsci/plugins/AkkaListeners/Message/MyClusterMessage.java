package org.jenkinsci.plugins.AkkaListeners.Message;

import java.io.Serializable;

/**
 * Created by eerilio on 5/15/15.
 */
public interface MyClusterMessage extends Serializable{

    Object getMessageContent();
}
