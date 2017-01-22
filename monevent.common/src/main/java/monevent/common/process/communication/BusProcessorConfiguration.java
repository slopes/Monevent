package monevent.common.process.communication;

import monevent.common.communication.EntityBusManager;
import monevent.common.model.configuration.Configuration;
import monevent.common.model.query.IQuery;
import monevent.common.process.IProcessor;
import monevent.common.process.ProcessorConfiguration;
import monevent.common.process.ProcessorManager;
import monevent.common.process.time.ScheduledProcessorBase;
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
        return new BusProcessor(getName(),getQuery(),entityBusManager,getSubscriptions(),processorManager,getPublications());
    }


}
