package monevent.common.process.combine;

import monevent.common.communication.EntityBusManager;
import monevent.common.model.query.IQuery;
import monevent.common.process.IProcessor;
import monevent.common.process.ProcessorConfiguration;
import monevent.common.process.ProcessorManager;
import monevent.common.store.StoreManager;

import java.util.List;

/**
 * Created by slopes on 15/01/17.
 */
public class PoolProcessorConfiguration extends ProcessorConfiguration {
    private String processorName;
    private int poolSize;

    public PoolProcessorConfiguration() {
        super();
    }

    public PoolProcessorConfiguration(String name, IQuery query,String processorName, int poolSize) {
        super(name, query);
        this.processorName = processorName;
        this.poolSize = poolSize;
    }

    public String getProcessorName() {
        return processorName;
    }

    public void setProcessorName(String processorName) {
        this.processorName = processorName;
    }

    public int getPoolSize() {
        return poolSize;
    }

    public void setPoolSize(int poolSize) {
        this.poolSize = poolSize;
    }

    @Override
    protected IProcessor doBuild(EntityBusManager entityBusManager, StoreManager storeManager, ProcessorManager processorManager) {
        return new PoolProcessor(getName(),getQuery(),getPoolSize(), processorManager, getProcessorName());
    }
}
