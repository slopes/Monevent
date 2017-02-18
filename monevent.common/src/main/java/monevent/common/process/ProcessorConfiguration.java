package monevent.common.process;

import com.google.common.base.Strings;
import monevent.common.communication.EntityBusManager;
import monevent.common.model.configuration.Configuration;
import monevent.common.model.configuration.ConfigurationException;
import monevent.common.model.query.IQuery;
import monevent.common.store.StoreManager;

/**
 * Created by steph on 12/03/2016.
 */
public abstract class ProcessorConfiguration extends Configuration {

    private IQuery query;

    protected ProcessorConfiguration() {
        super();
    }

    protected ProcessorConfiguration(String name, IQuery query) {
        super(name);
        this.query = query;
    }

    public IQuery getQuery() {
        return query;
    }

    public void setQuery(IQuery query) {
        this.query = query;
    }

    public IProcessor build(EntityBusManager entityBusManager, StoreManager storeManager, ProcessorManager processorManager) throws ConfigurationException {
        try {
            return doBuild(entityBusManager,storeManager,processorManager);
        } catch (Exception error) {
            throw new ConfigurationException(error);
        }
    }

    public void check() throws ConfigurationException {

        if (Strings.isNullOrEmpty(getName()))
            throw new ConfigurationException("The name of the processor cannot be null.");
    }

    protected abstract IProcessor doBuild(EntityBusManager entityBusManager, StoreManager storeManager, ProcessorManager processorManager);


}
