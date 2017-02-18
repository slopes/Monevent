package monevent.server.services;


import com.google.common.base.Strings;
import io.dropwizard.setup.Environment;
import monevent.common.model.configuration.Configuration;
import monevent.common.model.configuration.ConfigurationException;


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


    @Override
    public void check() throws ConfigurationException {
        if (Strings.isNullOrEmpty(getName()))
            throw new ConfigurationException("The service name cannot be null or empty.");
    }

    public abstract IService build(Environment environment,IServiceConfigurationFactory serviceConfigurationFactory);
}
