package monevent.common.process.metric;

import monevent.common.model.configuration.ConfigurationException;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by slopes on 18/02/17.
 */
public class MetricProcessorConfigurationTest {
    @Test
    public void testCheckNoError() throws Exception {
        try {
            MetricProcessorConfiguration configuration = new MetricProcessorConfiguration();
            configuration.setName("MetricProcessor");
            configuration.setMetricBus("metricBuss");
            configuration.setValueField("value");
            configuration.check();
        } catch (Throwable error) {
            fail(error.getMessage());
        }
    }

    @Test(expected = ConfigurationException.class)
    public void testCheckNoMetricBus() throws Exception {
        MetricProcessorConfiguration configuration = new MetricProcessorConfiguration();
        configuration.setName("MetricProcessor");
        configuration.setValueField("value");
        configuration.check();

    }

    @Test(expected = ConfigurationException.class)
    public void testCheckNoValueField() throws Exception {
        MetricProcessorConfiguration configuration = new MetricProcessorConfiguration();
        configuration.setName("MetricProcessor");
        configuration.setMetricBus("metricBuss");
        configuration.check();
    }

}