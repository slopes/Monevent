package monevent.common.process.metric;

import monevent.common.model.metric.IMetric;

/**
 * Created by steph on 19/03/2016.
 */
public interface IMetricFactory {
    IMetric build(String metricName);
}
