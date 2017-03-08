package monevent.server.services.configuration;

import com.codahale.metrics.health.HealthCheck;
import monevent.common.model.configuration.Configuration;
import monevent.server.services.ServiceTest;
import monevent.server.services.exception.ServiceException;

import static org.junit.Assert.*;

/**
 * Created by slopes on 22/01/17.
 */
public class ConfigurationServiceTest extends ServiceTest {


    public ConfigurationServiceTest() {
        addConfiguration(new ConfigurationServiceConfiguration(configurationManager));
    }

    @org.junit.Test
    public void serviceConfigurationTest() throws Exception {
        ConfigurationService service = new ConfigurationService(null, getConfigurationManager());
        try {
            service.start();
            Configuration configuration = service.getServiceConfiguration(ConfigurationService.NAME);
            assertNotNull(configuration);
            assertEquals(ConfigurationService.NAME, configuration.getName());
        } finally {
            service.stop();
        }
    }

    @org.junit.Test(expected = ServiceException.class)
    public void nullServiceConfigurationTest() throws Exception {
        ConfigurationService service = new ConfigurationService(null, getConfigurationManager());
        try {
            service.start();
            Configuration configuration = service.getServiceConfiguration(null);
        } finally {
            service.stop();
        }
    }

    @org.junit.Test
    public void healthCheckHealthyCheck() throws Exception {
        ConfigurationService service = new ConfigurationService(null, getConfigurationManager());
        try {
            service.start();
            HealthCheck.Result healthCheck = service.check();
            assertNotNull(healthCheck);
            assertSame(HealthCheck.Result.healthy(), healthCheck);
        } finally {
            service.stop();
        }
    }

}