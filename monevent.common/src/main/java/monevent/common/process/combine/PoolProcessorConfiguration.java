package monevent.common.process.combine;

import com.google.common.base.Strings;
import monevent.common.managers.Manager;
import monevent.common.model.configuration.ConfigurationException;
import monevent.common.model.query.IQuery;
import monevent.common.process.IProcessor;
import monevent.common.process.ProcessorConfiguration;

/**
 * Created by slopes on 15/01/17.
 */
public class PoolProcessorConfiguration extends ProcessorConfiguration {
    private String processorName;
    private int poolSize;

    public PoolProcessorConfiguration() {
        super();
    }

    public PoolProcessorConfiguration(String name, IQuery query, String processorName, int poolSize) {
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
    protected IProcessor doBuild(Manager manager) {
        return new PoolProcessor(getName(), getQuery(), getPoolSize(), manager, getProcessorName());
    }

    public void check() throws ConfigurationException {
        super.check();
        if (Strings.isNullOrEmpty(processorName))
            throw new ConfigurationException("The name of the processor cannot be null or empty.");
        if (getPoolSize() <= 0)
            throw new ConfigurationException("The pool size must be strictly positive.");
    }
}
