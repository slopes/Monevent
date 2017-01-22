package monevent.server.services.configuration;

import com.codahale.metrics.health.HealthCheck;
import com.codahale.metrics.health.HealthCheckRegistry;
import monevent.server.ServerConfiguration;
import monevent.server.services.DefaultServiceConfigurationFactory;
import monevent.server.services.IService;
import monevent.server.services.ServiceConfiguration;
import monevent.server.services.exception.ServiceException;

import static org.junit.Assert.*;

/**
 * Created by slopes on 22/01/17.
 */
public class ConfigurationServiceTest {
    @org.junit.Test
    public void serviceConfigurationTest() throws Exception {
        ConfigurationService service = new ConfigurationService();
        try {
            service.start();
            ServiceConfiguration configuration = service.getServiceConfiguration(ConfigurationService.NAME);
            assertNotNull(configuration);
            assertEquals(ConfigurationService.NAME, configuration.getName());
        } finally {
            service.stop();
        }
    }

    @org.junit.Test(expected = ServiceException.class)
    public void nullServiceConfigurationTest() throws Exception {
        ConfigurationService service = new ConfigurationService();
        try {
            service.start();
            ServiceConfiguration configuration = service.getServiceConfiguration(null);
        } finally {
            service.stop();
        }
    }

    @org.junit.Test
    public void healthCheckHealthyCheck() throws Exception {
        ConfigurationService service = new ConfigurationService();
        try {
            service.start();
            HealthCheck healthCheck = service.getHealthCheck();
            assertNotNull(healthCheck);
            assertSame(HealthCheck.Result.healthy(), healthCheck.execute());
        } finally {
            service.stop();
        }
    }

}