package monevent.common.process.metric;

import monevent.common.communication.EntityBusManager;
import monevent.common.model.query.Query;
import monevent.common.process.IProcessor;
import monevent.common.process.ProcessorManager;
import monevent.common.process.time.ScheduledProcessorConfiguration;
import monevent.common.store.StoreManager;

/**
 * Created by steph on 12/03/2016.
 */
public class MetricProcessorConfiguration extends ScheduledProcessorConfiguration {
    private String valueField;
    private long highestTrackableValue;
    private int numberOfSignificantValueDigits;
    private boolean resetOnPublish;

    public MetricProcessorConfiguration() {
        super();
    }

    public MetricProcessorConfiguration(String name,
                                        Query query,
                                        String cronExpression,
                                        String publication,
                                        String valueField,
                                        long highestTrackableValue,
                                        int numberOfSignificantValueDigits,
                                        boolean resetOnPublish) {
        super(name, query,cronExpression,publication);
        this.valueField = valueField;
        this.highestTrackableValue = highestTrackableValue;
        this.numberOfSignificantValueDigits = numberOfSignificantValueDigits;
        this.resetOnPublish = resetOnPublish;
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

    public boolean isResetOnPublish() {
        return resetOnPublish;
    }

    public void setResetOnPublish(boolean resetOnPublish) {
        this.resetOnPublish = resetOnPublish;
    }

    @Override
    public IProcessor doBuild(EntityBusManager entityBusManager, StoreManager storeManager, ProcessorManager processorManager) {
        return new MetricProcessor(this.getName(),
                getQuery(),
                getCronExpression(),
                getPublication(),
                entityBusManager,
                getValueField(),
                getHighestTrackableValue(),
                getNumberOfSignificantValueDigits(),
                isResetOnPublish());
    }
}
