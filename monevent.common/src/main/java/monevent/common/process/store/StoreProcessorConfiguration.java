package monevent.common.process.store;

import com.google.common.base.Strings;
import monevent.common.managers.Manager;
import monevent.common.model.configuration.ConfigurationException;
import monevent.common.model.query.Query;
import monevent.common.process.IProcessor;
import monevent.common.process.ProcessorConfiguration;
import monevent.common.store.IStore;

/**
 * Created by steph on 12/03/2016.
 */
public class StoreProcessorConfiguration extends ProcessorConfiguration {

    private String storeName;

    public StoreProcessorConfiguration() {
        super();
    }

    public StoreProcessorConfiguration(String name, Query query, String storeName) {
        super(name, query);
        this.storeName = storeName;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    @Override
    public void check() throws ConfigurationException {
        super.check();
        if (Strings.isNullOrEmpty(getStoreName()))
            throw new ConfigurationException("The store name cannot be null or empty.");
    }

    @Override
    public IProcessor doBuild(Manager manager) {
        IStore store = manager.get(this.getStoreName());
        if (store != null) {
            return new StoreProcessor(getName(), getQuery(), store);
        }
        return null;
    }


}
