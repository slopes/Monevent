package monevent.common.process.metric;

import monevent.common.managers.ManagerBase;
import monevent.common.model.metric.IMetric;

/**
 * Created by steph on 19/03/2016.
 */
public class MetricManager extends ManagerBase<IMetric> {

    private final IMetricFactory factory;

    public MetricManager(String name, IMetricFactory factory) {
        super(name);
        this.factory = factory;
    }

    @Override
    protected IMetric build(String metricName) {
        if (this.factory != null) {
            return this.factory.build(metricName);
        }
        return null;
    }
}
