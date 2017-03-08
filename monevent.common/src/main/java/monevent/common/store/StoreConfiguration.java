package monevent.common.store;

import com.google.common.base.Strings;
import monevent.common.managers.Manager;
import monevent.common.model.configuration.Configuration;
import monevent.common.model.configuration.ConfigurationException;

/**
 * Created by steph on 13/03/2016.
 */
public abstract class StoreConfiguration extends Configuration {
    protected StoreConfiguration() {
        super();
        setCategory("store");
    }

    protected StoreConfiguration(String name) {
        super(name);
        setCategory("store");
    }

    protected abstract IStore build();

    @Override
    public void check() throws ConfigurationException {
        if (Strings.isNullOrEmpty(getName()))
            throw new ConfigurationException("The store name cannot be null or empty.");
    }

    @Override
    public IStore build(Manager manager) {
        return build();
    }
}
