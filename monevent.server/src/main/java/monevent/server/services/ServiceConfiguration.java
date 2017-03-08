package monevent.server.services;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.base.Strings;
import io.dropwizard.setup.Environment;
import monevent.common.model.configuration.Configuration;
import monevent.common.model.configuration.ConfigurationException;


/**
 * Created by slopes on 22/01/17.
 */
public abstract class ServiceConfiguration extends Configuration
{
    @JsonIgnore
    private Environment environment;

    protected ServiceConfiguration() {
        setCategory("service");
    }

    protected ServiceConfiguration(String name) {
        super(name);
        setCategory("service");
    }


    public Environment getEnvironment() {
        return environment;
    }

    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    @Override
    public void check() throws ConfigurationException {
        if (Strings.isNullOrEmpty(getName()))
            throw new ConfigurationException("The service name cannot be null or empty.");
    }


}
