package monevent.common.process.alert;

import monevent.common.model.configuration.ConfigurationException;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by slopes on 18/02/17.
 */
public class ThresholdAlertProcessorConfigurationTest {
    @Test
    public void testCheckNoError() throws Exception {
        try {
            ThresholdAlertProcessorConfiguration configuration = new ThresholdAlertProcessorConfiguration();
            configuration.setName("ThresholdAlertProcessor");
            configuration.setAlertBus("alertBus");
            configuration.setCloseAfterDelay(3);
            configuration.setFatalThreshold(10.0);
            configuration.setCriticalThreshold(9.0);
            configuration.setMediumThreshold(8.0);
            configuration.setLowThreshold(7.0);
            configuration.setInfoThreshold(6.0);
            configuration.setValueField("value");
            configuration.check();
        } catch (Throwable error) {
            fail(error.getMessage());
        }
    }

    @Test(expected = ConfigurationException.class)
    public void testCheckWrongFatalThreshold() throws Exception {
        ThresholdAlertProcessorConfiguration configuration = new ThresholdAlertProcessorConfiguration();
        configuration.setName("ThresholdAlertProcessor");
        configuration.setAlertBus("alertBus");
        configuration.setCloseAfterDelay(3);
        configuration.setFatalThreshold(0.0);
        configuration.setCriticalThreshold(9.0);
        configuration.setMediumThreshold(8.0);
        configuration.setLowThreshold(7.0);
        configuration.setInfoThreshold(6.0);
        configuration.setValueField("value");
        configuration.check();

    }

    @Test(expected = ConfigurationException.class)
    public void testCheckWrongCriticalThreshold() throws Exception {
        ThresholdAlertProcessorConfiguration configuration = new ThresholdAlertProcessorConfiguration();
        configuration.setName("ThresholdAlertProcessor");
        configuration.setAlertBus("alertBus");
        configuration.setCloseAfterDelay(3);
        configuration.setFatalThreshold(0.0);
        configuration.setCriticalThreshold(0.0);
        configuration.setMediumThreshold(8.0);
        configuration.setLowThreshold(7.0);
        configuration.setInfoThreshold(6.0);
        configuration.setValueField("value");
        configuration.check();

    }

    @Test(expected = ConfigurationException.class)
    public void testCheckWrongMediumThreshold() throws Exception {
        ThresholdAlertProcessorConfiguration configuration = new ThresholdAlertProcessorConfiguration();
        configuration.setName("ThresholdAlertProcessor");
        configuration.setAlertBus("alertBus");
        configuration.setCloseAfterDelay(3);
        configuration.setFatalThreshold(0.0);
        configuration.setCriticalThreshold(0.0);
        configuration.setMediumThreshold(0.0);
        configuration.setLowThreshold(7.0);
        configuration.setInfoThreshold(6.0);
        configuration.setValueField("value");
        configuration.check();

    }

    @Test(expected = ConfigurationException.class)
    public void testCheckWrongLowThreshold() throws Exception {
        ThresholdAlertProcessorConfiguration configuration = new ThresholdAlertProcessorConfiguration();
        configuration.setName("ThresholdAlertProcessor");
        configuration.setAlertBus("alertBus");
        configuration.setCloseAfterDelay(3);
        configuration.setFatalThreshold(0.0);
        configuration.setCriticalThreshold(0.0);
        configuration.setMediumThreshold(0.0);
        configuration.setLowThreshold(0.0);
        configuration.setInfoThreshold(6.0);
        configuration.setValueField("value");
        configuration.check();

    }

    @Test(expected = ConfigurationException.class)
    public void testCheckNoValueField() throws Exception {
        ThresholdAlertProcessorConfiguration configuration = new ThresholdAlertProcessorConfiguration();
        configuration.setName("ThresholdAlertProcessor");
        configuration.setAlertBus("alertBus");
        configuration.setCloseAfterDelay(3);
        configuration.setFatalThreshold(0.0);
        configuration.setCriticalThreshold(0.0);
        configuration.setMediumThreshold(0.0);
        configuration.setLowThreshold(0.0);
        configuration.setInfoThreshold(0.0);
        configuration.check();

    }

}