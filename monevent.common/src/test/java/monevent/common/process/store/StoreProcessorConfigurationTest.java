package monevent.common.process.store;

import monevent.common.model.configuration.ConfigurationException;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by slopes on 18/02/17.
 */
public class StoreProcessorConfigurationTest {
    @Test
    public void testCheckNoError() throws Exception {
        try {
            StoreProcessorConfiguration configuration = new StoreProcessorConfiguration();
            configuration.setName("StoreProcessor");
            configuration.setStoreName("store");
            configuration.check();
        } catch (Throwable error) {
            fail(error.getMessage());
        }
    }

    @Test(expected = ConfigurationException.class)
    public void testCheckNoStore() throws Exception {

        StoreProcessorConfiguration configuration = new StoreProcessorConfiguration();
        configuration.setName("StoreProcessor");
        configuration.check();

    }

}