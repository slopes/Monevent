package monevent.common.process.chain.memory;

import monevent.common.communication.EntityBusManager;
import monevent.common.process.IProcessor;
import monevent.common.process.ProcessorManager;
import monevent.common.process.chain.ChainProcessorConfiguration;
import monevent.common.store.StoreManager;

/**
 * Created by slopes on 08/02/17.
 */
public class MemoryChainProcessorConfiguration extends ChainProcessorConfiguration {

    private String store;

    public MemoryChainProcessorConfiguration() {
    }

    public MemoryChainProcessorConfiguration(String store) {
        this.store = store;
    }

    public String getStore() {
        return store;
    }

    public void setStore(String store) {
        this.store = store;
    }

    @Override
    protected IProcessor doBuild(EntityBusManager entityBusManager, StoreManager storeManager, ProcessorManager processorManager) {
        return new MemoryChainProcessor(getName(),getQuery(),getChainingList(),entityBusManager,getResultBus(),storeManager,getStore());
    }
}
