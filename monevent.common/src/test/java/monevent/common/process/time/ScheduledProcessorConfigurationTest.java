package monevent.common.process.time;

import com.google.common.collect.Lists;
import monevent.common.model.configuration.ConfigurationException;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by slopes on 18/02/17.
 */
public class ScheduledProcessorConfigurationTest {
    @Test
    public void testCheckNoError() throws Exception {
        try {
            ScheduledProcessorConfiguration configuration = new ScheduledProcessorConfiguration();
            configuration.setName("ScheduledProcessor");
            configuration.setCronExpression("expression");
            configuration.setProcessors(Lists.newArrayList("processor"));
            configuration.check();
        } catch (Throwable error) {
            fail(error.getMessage());
        }
    }

    @Test(expected = ConfigurationException.class)
    public void testCheckNoCronExpression() throws Exception {
        ScheduledProcessorConfiguration configuration = new ScheduledProcessorConfiguration();
        configuration.setName("ScheduledProcessor");
        configuration.setProcessors(Lists.newArrayList("processor"));
        configuration.check();
    }

    @Test(expected = ConfigurationException.class)
    public void testCheckNoProcessors() throws Exception {
        ScheduledProcessorConfiguration configuration = new ScheduledProcessorConfiguration();
        configuration.setName("ScheduledProcessor");
        configuration.setCronExpression("expression");
        configuration.check();
    }

}