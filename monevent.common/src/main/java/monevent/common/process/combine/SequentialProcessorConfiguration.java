package monevent.common.process.combine;

import com.google.common.base.Strings;
import monevent.common.communication.EntityBusManager;
import monevent.common.model.configuration.ConfigurationException;
import monevent.common.model.query.IQuery;
import monevent.common.process.IProcessor;
import monevent.common.process.ProcessorConfiguration;
import monevent.common.process.ProcessorManager;
import monevent.common.store.StoreManager;

import java.util.List;

/**
 * Created by slopes on 15/01/17.
 */
public class SequentialProcessorConfiguration extends ProcessorConfiguration {
    private List<String> processors;
    private int poolSize;

    public SequentialProcessorConfiguration() {
        super();
    }

    public SequentialProcessorConfiguration( String name, IQuery query,List<String> processors, int poolSize) {
        super(name, query);
        this.processors = processors;
        this.poolSize = poolSize;
    }

    public List<String> getProcessors() {
        return processors;
    }

    public void setProcessors(List<String> processors) {
        this.processors = processors;
    }

    public int getPoolSize() {
        return poolSize;
    }

    public void setPoolSize(int poolSize) {
        this.poolSize = poolSize;
    }

    @Override
    public void check() throws ConfigurationException {
        super.check();
        if (getPoolSize() <= 0)
            throw new ConfigurationException("The pool size must be strickly positive.");
        if (getProcessors() == null || getProcessors().size() ==0)
            throw new ConfigurationException("The list of processors cannot be null or empty.");

    }

    @Override
    protected IProcessor doBuild(EntityBusManager entityBusManager, StoreManager storeManager, ProcessorManager processorManager) {
        return new SequentialProcessor(getName(),getQuery(), processorManager, getProcessors());
    }


}
