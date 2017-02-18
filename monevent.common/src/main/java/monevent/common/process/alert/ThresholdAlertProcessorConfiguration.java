package monevent.common.process.alert;

import com.google.common.base.Strings;
import monevent.common.communication.EntityBusManager;
import monevent.common.model.configuration.ConfigurationException;
import monevent.common.model.query.IQuery;
import monevent.common.process.IProcessor;
import monevent.common.process.ProcessorManager;
import monevent.common.store.StoreManager;

import java.util.concurrent.TimeUnit;

/**
 * Created by steph on 12/03/2016.
 */
public class ThresholdAlertProcessorConfiguration extends AlertProcessorConfiguration {
    //TODO: implement reverse mechanism
    private double fatalThreshold;
    private double criticalThreshold;
    private double mediumThreshold;
    private double lowThreshold;
    private double infoThreshold;
    private String valueField;

    public ThresholdAlertProcessorConfiguration() {
        super("Alert has been generated on threshold value !!");
    }


    public ThresholdAlertProcessorConfiguration(String name,
                                                IQuery query,
                                                String userMessage,
                                                int closeAfterDelay,
                                                TimeUnit closeAfterDelayTimeUnit,
                                                String alertBus,
                                                double fatalThreshold,
                                                double criticalThreshold,
                                                double mediumThreshold,
                                                double lowThreshold,
                                                double infoThreshold,
                                                String valueField) {
        super(name, query, userMessage, closeAfterDelay, closeAfterDelayTimeUnit, alertBus);
        this.fatalThreshold = fatalThreshold;
        this.criticalThreshold = criticalThreshold;
        this.mediumThreshold = mediumThreshold;
        this.lowThreshold = lowThreshold;
        this.infoThreshold = infoThreshold;
        this.valueField = valueField;
    }

    public double getFatalThreshold() {
        return fatalThreshold;
    }

    public void setFatalThreshold(double fatalThreshold) {
        this.fatalThreshold = fatalThreshold;
    }

    public double getCriticalThreshold() {
        return criticalThreshold;
    }

    public void setCriticalThreshold(double criticalThreshold) {
        this.criticalThreshold = criticalThreshold;
    }

    public double getMediumThreshold() {
        return mediumThreshold;
    }

    public void setMediumThreshold(double mediumThreshold) {
        this.mediumThreshold = mediumThreshold;
    }

    public double getLowThreshold() {
        return lowThreshold;
    }

    public void setLowThreshold(double lowThreshold) {
        this.lowThreshold = lowThreshold;
    }

    public double getInfoThreshold() {
        return infoThreshold;
    }

    public void setInfoThreshold(double infoThreshold) {
        this.infoThreshold = infoThreshold;
    }

    public String getValueField() {
        return valueField;
    }

    public void setValueField(String valueField) {
        this.valueField = valueField;
    }

    @Override
    public IProcessor doBuild(EntityBusManager entityBusManager, StoreManager storeManager, ProcessorManager processorManager) {
        return new ThresholdAlertProcessor(getName(), getQuery(), entityBusManager, getAlertBus(),
                getUserMessage(),
                getCloseAfterDelay(),
                getCloseAfterDelayTimeUnit(),
                getFatalThreshold(),
                getCriticalThreshold(),
                getMediumThreshold(),
                getLowThreshold(),
                getInfoThreshold(),
                getValueField());
    }

    @Override
    public void check() throws ConfigurationException {
        super.check();
        if (Strings.isNullOrEmpty(getValueField()))
            throw new ConfigurationException("The value filed cannot be null.");
        if (getFatalThreshold() < getCriticalThreshold())
            throw new ConfigurationException("The fatal threshold must exceed the critical threshold.");
        if (getCriticalThreshold() < getMediumThreshold())
            throw new ConfigurationException("The critical threshold must exceed the medium threshold.");
        if (getMediumThreshold() < getLowThreshold())
            throw new ConfigurationException("The medium threshold must exceed the low threshold.");
        if (getLowThreshold() < getInfoThreshold())
            throw new ConfigurationException("The low threshold must exceed the info threshold.");
    }
}
