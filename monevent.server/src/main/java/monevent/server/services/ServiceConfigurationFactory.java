package monevent.server.services;

import io.dropwizard.setup.Environment;
import monevent.common.model.configuration.factory.ConfigurationFactoryBase;
import monevent.common.model.configuration.factory.memory.MemoryConfigurationFactory;

/**
 * Created by slopes on 05/03/17.
 */
public class ServiceConfigurationFactory extends MemoryConfigurationFactory<ServiceConfiguration> {
    private final  Environment environment;


    public ServiceConfigurationFactory(Environment environment) {
        super("serviceConfigurationFactory");
        this.environment = environment;
    }

    @Override
    public ServiceConfiguration build(String key) {
        ServiceConfiguration configuration = super.build(key);
        configuration.setEnvironment(this.environment);
        return configuration;
    }

}
