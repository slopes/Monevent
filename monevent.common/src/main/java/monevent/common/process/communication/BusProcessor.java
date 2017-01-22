package monevent.common.process.communication;

import monevent.common.communication.EntityBusManager;
import monevent.common.communication.IEntityBus;
import monevent.common.model.IEntity;
import monevent.common.model.query.IQuery;
import monevent.common.process.IProcessor;
import monevent.common.process.ProcessorBase;
import monevent.common.process.ProcessorManager;

import java.util.List;

/**
 * Created by steph on 13/03/2016.
 */
public class BusProcessor extends ProcessorBase {
    private final ProcessorManager processorManager;
    private final EntityBusManager entityBusManager;
    private final List<String> publications;
    private final List<String> subscriptions;


    public BusProcessor(String name, IQuery query, EntityBusManager entityBusManager, List<String> subscriptions, ProcessorManager processorManager, List<String> publications) {
        super(name, query);
        this.processorManager = processorManager;
        this.publications = publications;
        this.subscriptions = subscriptions;
        this.entityBusManager = entityBusManager;
    }

    @Override
    protected IEntity doProcess(IEntity entity) throws Exception {
        if (this.publications != null && this.processorManager != null) {
            this.publications.stream().forEach(p -> {
                IProcessor processor = this.processorManager.load(p);
                if (processor != null) {
                    processor.process(entity);
                }
            });
        }
        return entity;
    }


    @Override
    protected void doStart() {
        if (this.subscriptions != null && this.entityBusManager != null) {
            this.subscriptions.stream().forEach(s -> {
                IEntityBus entityBus = this.entityBusManager.load(s);
                if (entityBus != null) {
                    entityBus.register(this);
                }
            });
        }
    }

    @Override
    protected void doStop() {
        if (this.subscriptions != null && this.entityBusManager != null) {
            this.subscriptions.stream().forEach(s -> {
                IEntityBus entityBus = this.entityBusManager.load(s);
                if (entityBus != null) {
                    entityBus.unregister(this);
                }
            });
        }
    }
}
