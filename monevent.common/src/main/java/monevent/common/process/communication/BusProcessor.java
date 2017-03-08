package monevent.common.process.communication;

import monevent.common.communication.IEntityBus;
import monevent.common.managers.Manager;
import monevent.common.model.IEntity;
import monevent.common.model.query.IQuery;
import monevent.common.process.IProcessor;
import monevent.common.process.ProcessorBase;

import java.util.List;

/**
 * Created by steph on 13/03/2016.
 */
public class BusProcessor extends ProcessorBase {
    private final Manager manager;
    private final List<String> publications;
    private final List<String> subscriptions;


    public BusProcessor(String name, IQuery query, Manager manager, List<String> subscriptions, List<String> publications) {
        super(name, query);
        this.manager = manager;
        this.publications = publications;
        this.subscriptions = subscriptions;
    }

    @Override
    protected IEntity doProcess(IEntity entity) throws Exception {
        if (this.publications != null && this.manager != null) {
            for (String publication : publications) {
                IProcessor processor = this.manager.get(publication);
                if (processor != null) {
                    processor.process(entity);
                }
            }
        }
        return entity;
    }


    @Override
    protected void doStart() {
        if (this.subscriptions != null && this.manager != null) {
            this.subscriptions.stream().forEach(s -> {
                IEntityBus entityBus = this.manager.get(s);
                if (entityBus != null) {
                    entityBus.register(this);
                }
            });
        }
    }

    @Override
    protected void doStop() {
        if (this.subscriptions != null && this.manager != null) {
            this.subscriptions.stream().forEach(s -> {
                IEntityBus entityBus = this.manager.get(s);
                if (entityBus != null) {
                    entityBus.unregister(this);
                }
            });
        }
    }
}
