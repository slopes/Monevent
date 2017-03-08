package monevent.common.process.combine;

import com.google.common.util.concurrent.*;
import monevent.common.managers.Manager;
import monevent.common.model.IEntity;
import monevent.common.model.query.IQuery;
import monevent.common.process.IProcessor;
import monevent.common.process.ProcessorBase;
import monevent.common.process.ProcessorException;

import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

/**
 * Created by slopes on 15/01/17.
 */
public class PoolProcessor extends ProcessorBase {
    private ListeningExecutorService executorService;
    private final int poolSize;
    private final Manager manager;
    private final String processorName;
    private final FutureCallback<IEntity> processCallback;

    public PoolProcessor(String name, IQuery query, int poolSize, Manager manager, String processorName) {
        super(name, query);
        this.poolSize = poolSize;
        this.manager = manager;
        this.processorName = processorName;
        this.processCallback = new ProcessCallback();
    }

    @Override
    protected IEntity doProcess(IEntity entity) throws Exception {
        if (this.executorService != null && !this.executorService.isTerminated()) {
            IProcessor processor = this.manager.get(this.processorName);
            if (processor != null) {
                ListenableFuture<IEntity> result = this.executorService.submit(new Process(entity, processor));
                Futures.addCallback(result, this.processCallback);
            } else {
                throw new ProcessorException(String.format("The processor %s does not exist", processorName));
            }
        }
        return entity;
    }

    @Override
    protected void doStart() {
        this.executorService = MoreExecutors.listeningDecorator(Executors.newFixedThreadPool(this.poolSize,new ProcessThreadFactory()));
    }

    @Override
    protected void doStop() {
        this.executorService.shutdown();
        this.executorService.isTerminated();
    }

    private class ProcessThreadFactory implements ThreadFactory {
        public Thread newThread(Runnable runnable) {
            return new Thread(runnable, String.format("%s.Thread",getName()));
        }
    }

    private class ProcessCallback implements FutureCallback<IEntity> {
        public void onSuccess(IEntity entity) {
            debug("Successfully process %s : %s",entity.getId(),entity.getId());
        }

        public void onFailure(Throwable error) {
            error(String.format("The processor %s has failed", processorName), error);
        }
    }

    private class Process implements Callable<IEntity> {

        private IEntity entity;
        private IProcessor processor;

        public Process(IEntity entity, IProcessor processor) {
            this.entity = entity;
            this.processor = processor;
        }

        @Override
        public IEntity call() throws Exception {
            return this.processor.process(this.entity);
        }
    }
}
