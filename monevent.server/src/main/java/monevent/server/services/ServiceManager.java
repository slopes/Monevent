package monevent.server.services;

import com.codahale.metrics.health.HealthCheck;
import io.dropwizard.setup.Environment;
import monevent.common.managers.ManagerBase;
import monevent.server.services.configuration.ConfigurationService;

/**
 * Created by slopes on 22/01/17.
 */
public class ServiceManager extends ManagerBase<IService> {

    private final ServiceFactory serviceFactory;
    private final HealthCheck healthCheck;
    private IService configurationService;

    private class ServicesHealthCheck extends HealthCheck {

        @Override
        protected Result check() throws Exception {
            if (configurationService != null) {
                HealthCheck.Result.unhealthy("The configuration service is not loaded");
            }
            return HealthCheck.Result.healthy();
        }
    }

    public ServiceManager(IServiceConfigurationFactory configurationFactory, Environment environment) {
        super("serviceManager");
        this.serviceFactory = new ServiceFactory(environment, configurationFactory);
        this.healthCheck = new ServicesHealthCheck();
        if (environment != null) {
            environment.jersey().register(this.healthCheck);
        } else {
            warn("Environment is not set!!");
        }
    }

    public ServiceManager(Environment environment) {
        this(new DefaultServiceConfigurationFactory(), environment);
    }

    @Override
    protected IService build(String service) {
        return this.serviceFactory.build(service);
    }

    protected HealthCheck getHealthCheck() {
        return healthCheck;
    }

    @Override
    protected void doStart() {
        super.doStart();
        configurationService = load(ConfigurationService.NAME);
    }

    @Override
    protected void doStop() {
        super.doStop();
        configurationService = null;
    }
}
