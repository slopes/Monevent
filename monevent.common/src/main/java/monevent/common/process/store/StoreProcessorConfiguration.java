package monevent.common.process.store;

import monevent.common.communication.EntityBusManager;
import monevent.common.model.query.Query;
import monevent.common.process.IProcessor;
import monevent.common.process.ProcessorConfiguration;
import monevent.common.process.ProcessorManager;
import monevent.common.store.IStore;
import monevent.common.store.StoreManager;

/**
 * Created by steph on 12/03/2016.
 */
public class StoreProcessorConfiguration extends ProcessorConfiguration {

    private String storeName;

    public StoreProcessorConfiguration() {
        super();
    }

    public StoreProcessorConfiguration(String name, Query query,String storeName) {
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
    public IProcessor doBuild(EntityBusManager entityBusManager, StoreManager storeManager, ProcessorManager processorManager) {
        IStore store = storeManager.load(this.getStoreName());
        if (store != null) {
            return new StoreProcessor(getName(), getQuery(), store);
        }
        return null;
    }
}
