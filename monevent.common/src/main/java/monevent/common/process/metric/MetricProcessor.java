package monevent.common.process.metric;

import monevent.common.communication.EntityBusManager;
import monevent.common.model.IEntity;
import monevent.common.model.metric.FixedMetric;
import monevent.common.model.metric.IMetric;
import monevent.common.model.query.IQuery;
import monevent.common.model.time.Timestamp;
import monevent.common.process.ProcessorBase;

import java.util.concurrent.atomic.AtomicReference;

/**
 * Created by steph on 06/03/2016.
 */
public class MetricProcessor extends ProcessorBase {

    private final AtomicReference<IMetric> metric;
    private final String valueField;
    private final long highestTrackableValue;
    private final int numberOfSignificantValueDigits;
    private final EntityBusManager entityBusManager;
    private final String metricBus;


    public MetricProcessor(String metricName,
                           IQuery query,
                           EntityBusManager entityBusManager,
                           String metricBus,
                           String valueField,
                           long highestTrackableValue,
                           int numberOfSignificantValueDigits) {
        super(metricName, query);
        this.valueField = valueField;
        this.metric = new AtomicReference<>();
        this.highestTrackableValue = highestTrackableValue;
        this.numberOfSignificantValueDigits = numberOfSignificantValueDigits;
        this.entityBusManager = entityBusManager;
        this.metricBus = metricBus;
    }


    @Override
    protected IEntity doProcess(IEntity entity) {
        if (entity instanceof Timestamp) {
            IMetric metric = this.metric.getAndSet(new FixedMetric(getName(), highestTrackableValue, numberOfSignificantValueDigits));
            IEntity metricEntity = metric.toEntity();
            if (publish(this.entityBusManager, this.metricBus, metricEntity)) {
                debug("Metric published.");
            } else {
                warn("Cannot publish metric.");
            }
            return metricEntity;
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
    }

    @Override
    protected void doStop() {
        IMetric metric = this.metric.get();
        metric.stop();
    }

}
