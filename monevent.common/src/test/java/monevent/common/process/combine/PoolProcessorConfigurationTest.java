package monevent.common.process.combine;

import monevent.common.model.configuration.ConfigurationException;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by slopes on 18/02/17.
 */
public class PoolProcessorConfigurationTest {
    @Test
    public void checkNoError() throws Exception {
        try {
            PoolProcessorConfiguration configuration = new PoolProcessorConfiguration();
            configuration.setName("PoolProcessorConfiguration");
            configuration.setPoolSize(3);
            configuration.setProcessorName("processor");
            configuration.check();
        } catch (Throwable error) {
            fail(error.getMessage());
        }
    }

    @Test(expected = ConfigurationException.class)
    public void checkNoName() throws Exception {
        PoolProcessorConfiguration configuration = new PoolProcessorConfiguration();
        configuration.setPoolSize(3);
        configuration.setProcessorName("processor");
        configuration.check();
    }

    @Test(expected = ConfigurationException.class)
    public void checkWrongPool() throws Exception {
        PoolProcessorConfiguration configuration = new PoolProcessorConfiguration();
        configuration.setName("PoolProcessorConfiguration");
        configuration.setPoolSize(-3);
        configuration.setProcessorName("processor");
        configuration.check();
    }

    @Test(expected = ConfigurationException.class)
    public void checkNoProcessorName() throws Exception {
        PoolProcessorConfiguration configuration = new PoolProcessorConfiguration();
        configuration.setName("PoolProcessorConfiguration");
        configuration.setPoolSize(3);
        configuration.check();
    }

}