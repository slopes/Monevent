package monevent.common.process.combine;

import com.google.common.collect.Lists;
import monevent.common.model.configuration.ConfigurationException;
import monevent.common.process.command.CommandProcessorConfiguration;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by slopes on 18/02/17.
 */
public class SequentialProcessorConfigurationTest {

    @Test
    public void testCheckNoError() throws Exception {
        try {
            SequentialProcessorConfiguration configuration = new SequentialProcessorConfiguration();
            configuration.setName("SequentialProcessor");
            configuration.setPoolSize(2);
            configuration.setProcessors(Lists.newArrayList("processor"));
            configuration.check();
        } catch (Throwable error) {
            fail(error.getMessage());
        }
    }

    @Test(expected = ConfigurationException.class)
    public void testCheckNoProcessor() throws Exception {
        SequentialProcessorConfiguration configuration = new SequentialProcessorConfiguration();
        configuration.setName("SequentialProcessor");
        configuration.setPoolSize(2);
        configuration.check();
    }

    @Test(expected = ConfigurationException.class)
    public void testCheckWrongPoolSize() throws Exception {
        SequentialProcessorConfiguration configuration = new SequentialProcessorConfiguration();
        configuration.setName("SequentialProcessor");
        configuration.setPoolSize(-2);
        configuration.setProcessors(Lists.newArrayList("processor"));
        configuration.check();
    }

}