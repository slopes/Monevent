package monevent.common.process.alert;

import monevent.common.managers.Manager;
import monevent.common.model.alert.AlertPriority;
import monevent.common.model.query.IQuery;
import monevent.common.process.IProcessor;

import java.util.concurrent.TimeUnit;


/**
 * Created by steph on 12/03/2016.
 */
public class MatchAlertProcessorConfiguration extends AlertProcessorConfiguration {

    private AlertPriority priority;

    public MatchAlertProcessorConfiguration(String name, IQuery query, String userMessage, int closeAfterDelay, TimeUnit closeAfterDelayTimeUnit, String alertBus, AlertPriority priority) {
        super(name, query, userMessage, closeAfterDelay, closeAfterDelayTimeUnit,alertBus);
        this.priority = priority;
    }

    public MatchAlertProcessorConfiguration() {
        super("Alert has been generated on query occurrence !!");
    }

    public AlertPriority getPriority() {
        return priority;
    }

    public void setPriority(AlertPriority priority) {
        this.priority = priority;
    }

    @Override
    public IProcessor doBuild(Manager manager) {
        return new MatchAlertProcessor(getName(), getQuery(),manager, getAlertBus(),
                getUserMessage(),
                getCloseAfterDelay(),
                getCloseAfterDelayTimeUnit(),
                getPriority());
    }


}
