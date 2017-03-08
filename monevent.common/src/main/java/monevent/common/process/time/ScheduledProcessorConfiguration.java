package monevent.common.process.time;

import com.google.common.base.Strings;
import monevent.common.managers.Manager;
import monevent.common.model.configuration.ConfigurationException;
import monevent.common.model.query.IQuery;
import monevent.common.process.IProcessor;
import monevent.common.process.ProcessorConfiguration;

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
    public void check() throws ConfigurationException {
        //TODO : Add check of the cron itself
        super.check();
        if (Strings.isNullOrEmpty(getCronExpression()))
            throw new ConfigurationException("The cron expression cannot be null or empty.");
        if (getProcessors() == null || getProcessors().size() ==0)
            throw new ConfigurationException("The list of processors cannot be null or empty.");

    }

    @Override
    protected IProcessor doBuild(Manager manager) {
        return new ScheduledProcessor(getName(),getQuery(),getCronExpression(),getProcessors(), manager);
    }

}
