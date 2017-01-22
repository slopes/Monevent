package monevent.server.services;

import com.codahale.metrics.health.HealthCheck;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by slopes on 22/01/17.
 */
public class ServiceManagerTest {
    @Test
    public void buildTest() throws Exception {
        ServiceManager serviceManager = new ServiceManager(null);
        try {
            serviceManager.start();
            HealthCheck healthCheck = serviceManager.getHealthCheck();
            assertNotNull(healthCheck);
            assertSame(HealthCheck.Result.healthy(), healthCheck.execute());
        }
        finally {
            serviceManager.stop();
        }

    }

}