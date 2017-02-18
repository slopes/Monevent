package monevent.common.store;

import com.google.common.base.Strings;
import monevent.common.model.configuration.Configuration;
import monevent.common.model.configuration.ConfigurationException;

/**
 * Created by steph on 13/03/2016.
 */
public abstract class StoreConfiguration extends Configuration {
    protected StoreConfiguration() {
        super();
    }

    protected StoreConfiguration(String name) {
        super(name);
    }

    public abstract IStore build();

    @Override
    public void check() throws ConfigurationException {
        if (Strings.isNullOrEmpty(getName()))
            throw new ConfigurationException("The store name cannot be null or empty.");
    }
}
