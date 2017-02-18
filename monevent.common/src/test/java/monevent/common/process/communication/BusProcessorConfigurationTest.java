package monevent.common.process.communication;

import com.google.common.collect.Lists;
import monevent.common.model.configuration.Configuration;
import monevent.common.model.configuration.ConfigurationException;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by slopes on 18/02/17.
 */
public class BusProcessorConfigurationTest {

    @Test(expected = ConfigurationException.class)
    public void testCheckNoPublications() throws Exception {
        BusProcessorConfiguration configuration = new BusProcessorConfiguration();
        configuration.setName("BusProcessor");
        configuration.setSubscriptions(Lists.newArrayList("processor"));
        configuration.check();
    }

    @Test(expected = ConfigurationException.class)
    public void testCheckNoSubscriptions() throws Exception {
        BusProcessorConfiguration configuration = new BusProcessorConfiguration();
        configuration.setName("BusProcessor");
        configuration.setPublications(Lists.newArrayList("processor"));
        configuration.check();
    }

    @Test()
    public void testCheckNoError() throws Exception {
        try {
            BusProcessorConfiguration configuration = new BusProcessorConfiguration();
            configuration.setName("BusProcessor");
            configuration.setPublications(Lists.newArrayList("processor"));
            configuration.setSubscriptions(Lists.newArrayList("processor"));
            configuration.check();
        } catch (Throwable error)  {
            fail(error.getMessage());
        }

    }

}