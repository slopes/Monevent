package monevent.common.process.chain.memory;

import com.google.common.base.Strings;
import monevent.common.managers.Manager;
import monevent.common.model.configuration.ConfigurationException;
import monevent.common.process.IProcessor;
import monevent.common.process.chain.ChainProcessorConfiguration;

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
    public void check() throws ConfigurationException {
        super.check();
        if (Strings.isNullOrEmpty(getStore()))
            throw new ConfigurationException("The chain store cannot be null or empty.");

    }

    @Override
    protected IProcessor doBuild(Manager manager) {
        return new MemoryChainProcessor(getName(),getQuery(),getChainingList(),manager,getResultBus(),getStore());
    }


}
