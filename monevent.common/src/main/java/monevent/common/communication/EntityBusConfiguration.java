package monevent.common.communication;

import com.google.common.base.Strings;
import monevent.common.model.configuration.Configuration;
import monevent.common.model.configuration.ConfigurationException;

/**
 * Created by steph on 20/03/2016.
 */
public abstract class EntityBusConfiguration extends Configuration {

    protected EntityBusConfiguration() {
        super();
    }

    protected EntityBusConfiguration(String name) {
        super(name);
    }

    public abstract IEntityBus build();

    @Override
    public void check() throws ConfigurationException {
        if (Strings.isNullOrEmpty(getName()))
            throw new ConfigurationException("The bus name cannot be null or empty.");
    }
}