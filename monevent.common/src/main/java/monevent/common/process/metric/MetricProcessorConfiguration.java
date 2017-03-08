package monevent.common.process.metric;

import com.google.common.base.Strings;
import monevent.common.managers.Manager;
import monevent.common.model.configuration.ConfigurationException;
import monevent.common.model.query.Query;
import monevent.common.process.IProcessor;
import monevent.common.process.ProcessorConfiguration;

/**
 * Created by steph on 12/03/2016.
 */
public class MetricProcessorConfiguration extends ProcessorConfiguration {
    private String valueField;
    private long highestTrackableValue;
    private int numberOfSignificantValueDigits;
    private String metricBus;

    public MetricProcessorConfiguration() {
        super();
    }

    public MetricProcessorConfiguration(String name,
                                        Query query,
                                        String metricBus,
                                        String valueField,
                                        long highestTrackableValue,
                                        int numberOfSignificantValueDigits) {
        super(name, query);
        this.valueField = valueField;
        //TODO : set default values for metric histograms
        this.highestTrackableValue = highestTrackableValue;
        this.numberOfSignificantValueDigits = numberOfSignificantValueDigits;
        this.metricBus = metricBus;
    }

    public String getValueField() {
        return valueField;
    }

    public void setValueField(String valueField) {
        this.valueField = valueField;
    }

    public long getHighestTrackableValue() {
        return highestTrackableValue;
    }

    public void setHighestTrackableValue(long highestTrackableValue) {
        this.highestTrackableValue = highestTrackableValue;
    }

    public int getNumberOfSignificantValueDigits() {
        return numberOfSignificantValueDigits;
    }

    public void setNumberOfSignificantValueDigits(int numberOfSignificantValueDigits) {
        this.numberOfSignificantValueDigits = numberOfSignificantValueDigits;
    }

    public String getMetricBus() {
        return metricBus;
    }

    public void setMetricBus(String metricBus) {
        this.metricBus = metricBus;
    }

    @Override
    public void check() throws ConfigurationException {
        super.check();
        if (Strings.isNullOrEmpty(getValueField()))
            throw new ConfigurationException("The value field cannot be null or empty.");
        if (Strings.isNullOrEmpty(getMetricBus()))
            throw new ConfigurationException("The metric bus cannot be null or empty.");
    }

    @Override
    public IProcessor doBuild(Manager manager) {
        return new MetricProcessor(this.getName(),
                getQuery(),
                manager,
                getMetricBus(),
                getValueField(),
                getHighestTrackableValue(),
                getNumberOfSignificantValueDigits());
    }
}
