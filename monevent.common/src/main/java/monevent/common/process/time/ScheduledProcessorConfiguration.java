package monevent.common.process.time;

import monevent.common.communication.EntityBusManager;
import monevent.common.model.query.IQuery;
import monevent.common.process.IProcessor;
import monevent.common.process.ProcessorConfiguration;
import monevent.common.process.ProcessorManager;
import monevent.common.process.combine.SequentialProcessor;
import monevent.common.store.IStore;
import monevent.common.store.StoreManager;

import java.util.List;

/**
 * Created by slopes on 18/01/17.
 */
public class ScheduledProcessorConfiguration extends ProcessorConfiguration {

    private String cronExpression;
    private List<String> processors;

    public ScheduledProcessorConfiguration() {
        super();
    }


    public ScheduledProcessorConfiguration(String name, IQuery query, String cronExpression, List<String> processors) {
        super(name, query);
        this.cronExpression = cronExpression;
        this.processors = processors;
    }

    public String getCronExpression() {
        return cronExpression;
    }

    public void setCronExpression(String cronExpression) {
        this.cronExpression = cronExpression;
    }

    public List<String> getProcessors() {
        return processors;
    }

    public void setProcessors(List<String> processors) {
        this.processors = processors;
    }

    @Override
    protected IProcessor doBuild(EntityBusManager entityBusManager, StoreManager storeManager, ProcessorManager processorManager) {
        return new ScheduledProcessor(getName(),getQuery(),getCronExpression(),getProcessors(),processorManager);
    }

}
