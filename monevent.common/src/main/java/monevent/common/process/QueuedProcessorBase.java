package monevent.common.process;

import com.google.common.collect.Lists;
import monevent.common.model.IEntity;
import monevent.common.model.query.IQuery;

import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by slopes on 10/02/17.
 */
public abstract class QueuedProcessorBase extends ProcessorBase {

    private BlockingQueue<IEntity> entities;
    private ExecutorService executor;
    private AtomicBoolean isRunning;

    protected QueuedProcessorBase(String name, IQuery query) {
        super(name, query);
        this.entities = new LinkedBlockingDeque<IEntity>();
        this.isRunning = new AtomicBoolean();
    }

    @Override
    protected IEntity doProcess(IEntity entity) throws Exception {

        if (this.isRunning.get()) {
            entities.offer(entity);
        }
        return entity;
    }

    @Override
    protected void doStart() {
        this.executor = Executors.newFixedThreadPool(1);
        this.isRunning.set(true);
        this.executor.execute(() -> {
            while (this.isRunning.get()) {

                try {
                    IEntity entity = this.entities.poll(100, TimeUnit.MILLISECONDS); //TODO : manage timeout
                    if (entity != null) {
                        List<IEntity> items = Lists.newArrayList(entity);
                        this.entities.drainTo(items, 500);
                        while (!items.isEmpty()) {
                            doExecute(items);
                            items.clear();
                            this.entities.drainTo(items, 500);
                        }
                    }
                } catch (InterruptedException interruption) {
                    info("Interuption required.");
                }

            }
        });

    }

    protected abstract void doExecute(List<IEntity> items);

    @Override
    protected void doStop() {
        this.isRunning.set(false);
        try {
            this.executor.shutdown();
            this.executor.awaitTermination(100, TimeUnit.MILLISECONDS);
        } catch (InterruptedException interruption) {
            info("Interuption required.");
        }
    }
}
