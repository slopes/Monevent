package monevent.common.process.alert;

import monevent.common.model.alert.AlertPriority;
import monevent.common.model.configuration.ConfigurationException;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by slopes on 18/02/17.
 */
public class MatchAlertProcessorConfigurationTest {
    @Test
    public void testCheckNoError() throws Exception {
        try {
            MatchAlertProcessorConfiguration configuration = new MatchAlertProcessorConfiguration();
            configuration.setName("MatchAlertProcessor");
            configuration.setPriority(AlertPriority.Info);
            configuration.setAlertBus("alertBus");
            configuration.setCloseAfterDelay(3);
            configuration.check();
        } catch (Throwable error) {
            fail(error.getMessage());
        }
    }

    @Test(expected = ConfigurationException.class)
    public void testCheckNoName() throws Exception {
        MatchAlertProcessorConfiguration configuration = new MatchAlertProcessorConfiguration();
        configuration.setPriority(AlertPriority.Info);
        configuration.setAlertBus("alertBus");
        configuration.setCloseAfterDelay(3);
        configuration.check();
    }

    @Test(expected = ConfigurationException.class)
    public void testCheckWrongDelay() throws Exception {
        MatchAlertProcessorConfiguration configuration = new MatchAlertProcessorConfiguration();
        configuration.setName("MatchAlertProcessor");
        configuration.setPriority(AlertPriority.Info);
        configuration.setAlertBus("alertBus");
        configuration.setCloseAfterDelay(-3);
        configuration.check();
    }

    @Test(expected = ConfigurationException.class)
    public void testCheckNoAlertBus() throws Exception {
        MatchAlertProcessorConfiguration configuration = new MatchAlertProcessorConfiguration();
        configuration.setName("MatchAlertProcessor");
        configuration.setPriority(AlertPriority.Info);
        configuration.setCloseAfterDelay(3);
        configuration.check();
    }

}