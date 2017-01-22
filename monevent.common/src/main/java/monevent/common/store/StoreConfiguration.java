package monevent.common.store;

import monevent.common.model.configuration.Configuration;

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
}
