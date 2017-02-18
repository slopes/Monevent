package monevent.common.process.metric;

import monevent.common.communication.EntityBusManager;
import monevent.common.model.query.Query;
import monevent.common.process.IProcessor;
import monevent.common.process.ProcessorConfiguration;
import monevent.common.process.ProcessorManager;
import monevent.common.store.StoreManager;

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
    public IProcessor doBuild(EntityBusManager entityBusManager, StoreManager storeManager, ProcessorManager processorManager) {
        return new MetricProcessor(this.getName(),
                getQuery(),
                entityBusManager,
                getMetricBus(),
                getValueField(),
                getHighestTrackableValue(),
                getNumberOfSignificantValueDigits());
    }
}
