package monevent.server.services;


import io.dropwizard.setup.Environment;
import monevent.common.model.configuration.Configuration;


/**
 * Created by slopes on 22/01/17.
 */
public abstract class ServiceConfiguration extends Configuration
{
    protected ServiceConfiguration() {
    }

    protected ServiceConfiguration(String name) {
        super(name);
    }

    public abstract IService build(Environment environment,IServiceConfigurationFactory serviceConfigurationFactory);
}
