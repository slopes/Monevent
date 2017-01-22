package monevent.server.services;


import io.dropwizard.setup.Environment;
import monevent.common.managers.ManageableBase;

/**
 * Created by slopes on 22/01/17.
 */
public class ServiceFactory extends ManageableBase {
    private final Environment environment;
    private final IServiceConfigurationFactory configurationFactory;

    public ServiceFactory(Environment environment, IServiceConfigurationFactory configurationFactory) {
        super("serviceFactory");
        this.environment = environment;
        this.configurationFactory = configurationFactory;
    }

    public IService build(String name) {
        if (configurationFactory != null) {
            ServiceConfiguration serviceConfiguration = configurationFactory.build(name);
            if (serviceConfiguration != null) {
               return serviceConfiguration.build(this.environment, configurationFactory);
            } else {
                error("The service configuration does not exists.");
            }
        } else {
            error("The configuration factory is not set.");
        }

        return null;
    }

    @Override
    protected void doStart() {

    }

    @Override
    protected void doStop() {

    }
}
