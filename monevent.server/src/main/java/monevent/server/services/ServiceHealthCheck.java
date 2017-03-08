package monevent.server.services;

import com.codahale.metrics.health.HealthCheck;

/**
 * Created by slopes on 20/02/17.
 */

public class ServiceHealthCheck extends HealthCheck {
    private IService service;

    public ServiceHealthCheck(IService service) {
        this.service = service;
    }

    @Override
    protected Result check() throws Exception {
        if (this.service != null) {
            return service.check();
        }
        return HealthCheck.Result.unhealthy("The service is not initialized");
    }
}