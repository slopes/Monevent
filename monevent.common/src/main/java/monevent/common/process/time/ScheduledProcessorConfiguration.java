package monevent.common.process.time;

import monevent.common.model.query.IQuery;
import monevent.common.process.ProcessorConfiguration;
import monevent.common.store.IStore;

/**
 * Created by slopes on 18/01/17.
 */
public abstract class ScheduledProcessorConfiguration extends ProcessorConfiguration {

    private String cronExpression;
    private String publication;

    protected ScheduledProcessorConfiguration() {
        super();
    }

    protected ScheduledProcessorConfiguration(String name, IQuery query, String cronExpression, String publication) {
        super(name, query);
        this.cronExpression = cronExpression;
        this.publication = publication;
    }

    public String getCronExpression() {
        return cronExpression;
    }

    public void setCronExpression(String cronExpression) {
        this.cronExpression = cronExpression;
    }

    public String getPublication() {
        return publication;
    }

    public void setPublication(String publication) {
        this.publication = publication;
    }
}
