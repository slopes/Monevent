package monevent.common.process.alert;

import com.google.common.base.Strings;
import monevent.common.model.configuration.ConfigurationException;
import monevent.common.model.query.IQuery;
import monevent.common.process.ProcessorConfiguration;

import java.util.concurrent.TimeUnit;

/**
 * Created by steph on 23/03/2016.
 */
public abstract class AlertProcessorConfiguration extends ProcessorConfiguration {
    private String userMessage;
    private int closeAfterDelay;
    private TimeUnit closeAfterDelayTimeUnit;
    private String alertBus;

    public AlertProcessorConfiguration(String name, IQuery query, String userMessage, int closeAfterDelay, TimeUnit closeAfterDelayTimeUnit,String alertBus) {
        super(name, query);
        this.userMessage = userMessage;
        this.closeAfterDelay = closeAfterDelay;
        this.closeAfterDelayTimeUnit = closeAfterDelayTimeUnit;
        this.alertBus = alertBus;
    }

    public AlertProcessorConfiguration(String userMessage) {
        this.userMessage = userMessage;
    }

    public String getUserMessage() {
        return userMessage;
    }

    public void setUserMessage(String userMessage) {
        this.userMessage = userMessage;
    }

    public int getCloseAfterDelay() {
        return closeAfterDelay;
    }

    public void setCloseAfterDelay(int closeAfterDelay) {
        this.closeAfterDelay = closeAfterDelay;
    }

    public TimeUnit getCloseAfterDelayTimeUnit() {
        return closeAfterDelayTimeUnit;
    }

    public void setCloseAfterDelayTimeUnit(TimeUnit closeAfterDelayTimeUnit) {
        this.closeAfterDelayTimeUnit = closeAfterDelayTimeUnit;
    }

    public String getAlertBus() {
        return alertBus;
    }

    public void setAlertBus(String alertBus) {
        this.alertBus = alertBus;
    }

    public void check() throws ConfigurationException {
        super.check();
        if (Strings.isNullOrEmpty(getAlertBus()))
            throw new ConfigurationException("Alert bus cannot be null.");
        if (Strings.isNullOrEmpty(getUserMessage()))
            throw new ConfigurationException("User message cannot be null.");
        if ( getCloseAfterDelay() < 0 )
            throw new ConfigurationException("Closing delay must be strictly positive.");

    }
}
