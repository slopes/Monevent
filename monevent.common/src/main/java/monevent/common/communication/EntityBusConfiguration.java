package monevent.common.communication;

import com.google.common.base.Strings;
import monevent.common.managers.Manager;
import monevent.common.model.configuration.Configuration;
import monevent.common.model.configuration.ConfigurationException;

/**
 * Created by steph on 20/03/2016.
 */
public class EntityBusConfiguration extends Configuration {

    public EntityBusConfiguration() {
        super();
        setCategory("bus");
    }

    public EntityBusConfiguration(String name) {
        super(name);
        setCategory("bus");
    }

    @Override
    public void check() throws ConfigurationException {
        if (Strings.isNullOrEmpty(getName()))
            throw new ConfigurationException("The bus name cannot be null or empty.");

    }

    @Override
    public EntityBus build(Manager manager) {
        return new EntityBus(getName());
    }
}