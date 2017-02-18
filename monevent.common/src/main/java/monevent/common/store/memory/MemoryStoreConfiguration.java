package monevent.common.store.memory;

import com.google.common.base.Strings;
import monevent.common.model.configuration.ConfigurationException;
import monevent.common.store.IStore;
import monevent.common.store.StoreConfiguration;

/**
 * Created by steph on 13/03/2016.
 */
public class MemoryStoreConfiguration extends StoreConfiguration{

    public MemoryStoreConfiguration() {
        super();
    }

    public MemoryStoreConfiguration(String name) {
        super(name);
    }

    @Override
    public IStore build() {
        return new MemoryStore(this.getName());
    }



}
