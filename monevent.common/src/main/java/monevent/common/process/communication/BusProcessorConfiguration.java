package monevent.common.process.communication;

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
 * Created by steph on 12/03/2016.
 */
public class BusProcessorConfiguration extends ProcessorConfiguration {
    private List<String> publications;
    private List<String> subscriptions;

    public BusProcessorConfiguration() {
        super();
    }

    public BusProcessorConfiguration(String name, IQuery query, List<String> publications, List<String> subscriptions) {
        super(name, query);
        this.publications = publications;
        this.subscriptions = subscriptions;
    }

    public List<String> getPublications() {
        return publications;
    }

    public void setPublications(List<String> publications) {
        this.publications = publications;
    }

    public List<String> getSubscriptions() {
        return subscriptions;
    }

    public void setSubscriptions(List<String> subscriptions) {
        this.subscriptions = subscriptions;
    }

    @Override
    protected IProcessor doBuild(EntityBusManager entityBusManager, StoreManager storeManager, ProcessorManager processorManager) {
        return new BusProcessor(getName(), getQuery(), entityBusManager, getSubscriptions(), processorManager, getPublications());
    }

    public void check() throws ConfigurationException {
        super.check();
        if (getPublications() == null || getPublications().size() ==0)
            throw new ConfigurationException("The list of publications cannot be null or empty.");
        if (getSubscriptions() == null || getSubscriptions().size() ==0)
            throw new ConfigurationException("The list of subscriptions cannot be null or empty.");
    }
}
