package monevent.common.process;

import com.google.common.base.Strings;
import monevent.common.managers.Manager;
import monevent.common.model.configuration.Configuration;
import monevent.common.model.configuration.ConfigurationException;
import monevent.common.model.query.IQuery;

/**
 * Created by steph on 12/03/2016.
 */
public abstract class ProcessorConfiguration extends Configuration {

    private IQuery query;

    protected ProcessorConfiguration() {
        super();
        setCategory("processor");
    }

    protected ProcessorConfiguration(String name, IQuery query) {
        super(name);
        setCategory("processor");
        this.query = query;
    }

    public IQuery getQuery() {
        return query;
    }

    public void setQuery(IQuery query) {
        this.query = query;
    }

    @Override
    public IProcessor build(Manager manager) throws ConfigurationException {
        try {
            return doBuild(manager);
        } catch (Exception error) {
            throw new ConfigurationException(error);
        }
    }

    public void check() throws ConfigurationException {

        if (Strings.isNullOrEmpty(getName()))
            throw new ConfigurationException("The name of the processor cannot be null.");
    }

    protected abstract IProcessor doBuild(Manager manager);


}
