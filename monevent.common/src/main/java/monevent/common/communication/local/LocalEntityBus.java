package monevent.common.communication.local;

import com.google.common.eventbus.AsyncEventBus;
import com.google.common.eventbus.EventBus;
import monevent.common.communication.IEntityBus;
import monevent.common.managers.ManageableBase;
import monevent.common.model.IEntity;
import monevent.common.model.query.IQuery;
import monevent.common.process.IProcessor;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by steph on 06/03/2016.
 */
public class LocalEntityBus extends ManageableBase implements IEntityBus {

    private final EventBus eventBus;
    private final ExecutorService executor;
    private final AtomicBoolean isRunning;

    public LocalEntityBus(String name){
        super(name);
        int cores = Runtime.getRuntime().availableProcessors();
        this.executor = Executors.newFixedThreadPool(cores);
        this.eventBus = new AsyncEventBus(name,this.executor);
        this.isRunning = new AtomicBoolean(false);
    }

    @Override
    protected void doStart() {
        this.isRunning.set(true);
    }

    @Override
    protected void doStop() {
        this.isRunning.set(false);
        this.executor.shutdown();
    }

    @Override
    public void register(IProcessor processor) {
        eventBus.register(processor);
    }

    @Override
    public void unregister(IProcessor processor) {
        eventBus.unregister(processor);
    }

    @Override
    public void publish(IEntity entity) {
        if ( this.isRunning.get() && entity != null) {
            eventBus.post(entity);
        }
    }
}
