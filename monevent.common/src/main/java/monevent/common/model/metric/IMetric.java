package monevent.common.model.metric;

import monevent.common.managers.IManageable;
import monevent.common.model.IEntity;
import org.joda.time.DateTime;

/**
 * Created by steph on 19/03/2016.
 */
public interface IMetric extends IManageable , IEntity {
    long getCount();
    DateTime getStart();
    DateTime getStop();
    double getMinimum();
    double getMaximum();
    double getLast();
    double getPercentile50();
    double getPercentile75();
    double getPercentile95();
    double getPercentile99();
    double getMean();
    double getStandardDeviation();
    void add(long value);
    IEntity toEntity();
}
