package monevent.common.process.metric;

import monevent.common.communication.EntityBusManager;
import monevent.common.model.IEntity;
import monevent.common.model.metric.FixedMetric;
import monevent.common.model.metric.IMetric;
import monevent.common.model.query.IQuery;
import monevent.common.model.time.Timestamp;
import monevent.common.process.time.ScheduledProcessorBase;

import java.util.concurrent.atomic.AtomicReference;

/**
 * Created by steph on 06/03/2016.
 */
public class MetricProcessor extends ScheduledProcessorBase {

    private final AtomicReference<IMetric> metric;
    private final String valueField;
    private final boolean resetOnPublish;
    private final long highestTrackableValue;
    private final int numberOfSignificantValueDigits;

    public MetricProcessor(String metricName,
                           IQuery query,
                           String cronExpression,
                           String publication,
                           EntityBusManager entityBusManager,
                           String valueField,
                           long highestTrackableValue,
                           int numberOfSignificantValueDigits,
                           boolean resetOnPublish) {
        super(metricName, query, cronExpression, publication, entityBusManager);
        this.valueField = valueField;
        this.metric = new AtomicReference<IMetric>();
        this.resetOnPublish = resetOnPublish;
        this.highestTrackableValue = highestTrackableValue;
        this.numberOfSignificantValueDigits = numberOfSignificantValueDigits;
    }

    @Override
    protected IEntity doProcess(IEntity entity) {
        if (entity instanceof Timestamp) {

                IMetric metric = this.metric.getAndSet(new FixedMetric(getName(), highestTrackableValue, numberOfSignificantValueDigits));
                return metric.toEntity();

        } else {
            Long value = entity.getValueAsLong(this.valueField);
            if (value != null) {
                this.metric.get().add(value);
            }
            return entity;
        }
    }

    @Override
    protected void doStart() {
        IMetric metric = new FixedMetric(getName(), highestTrackableValue, numberOfSignificantValueDigits);
        metric.start();
        this.metric.set(metric);
        super.doStart();
    }

    @Override
    protected void doStop() {
        super.doStop();
        IMetric metric = this.metric.get();
        metric.stop();
    }

}
