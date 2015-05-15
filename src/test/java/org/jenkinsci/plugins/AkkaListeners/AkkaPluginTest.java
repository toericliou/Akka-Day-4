package org.jenkinsci.plugins.AkkaListeners;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by eerilio on 5/14/15.
 */
public class AkkaPluginTest {

    private AkkaPlugin unitUnderTest;

    @Before
    public void setUp() throws Exception {
        unitUnderTest = new AkkaPlugin();
    }

    /*@Test
    public void start(){
        unitUnderTest.start();
        assertNotNull(unitUnderTest.getBuildListenerActorRef());
        assertNotNull(unitUnderTest.getItemListenerActorRef());
        assertNotNull(unitUnderTest.getSavableListenerActorRef());
        assertNotNull(unitUnderTest.getRunListenerActorRef());
    }*/
}