package monevent.server.services;

import com.codahale.metrics.health.HealthCheck;
import io.dropwizard.setup.Environment;
import monevent.common.managers.ManageableBase;

/**
 * Created by slopes on 22/01/17.
 */
public abstract class ServiceBase extends ManageableBase implements IService {

    private final Environment environment;

    protected ServiceBase(String name, Environment environment) {
        super(name);
        this.environment = environment;
    }

    @Override
    protected void doStart() {
        if (environment != null) {
            environment.jersey().register(this);
            environment.healthChecks().register(getName(), new ServiceHealthCheck(this));
        } else {
            warn("Environement is not set. This is allowed only for testing.");
        }
    }

    @Override
    protected void doStop() {
    }




}
