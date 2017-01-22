package monevent.common.model.metric;


import monevent.common.managers.ManageableException;
import monevent.common.model.Entity;
import monevent.common.model.IEntity;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

import java.util.Map;

public class Metric extends Entity implements IMetric {

    public static String count = "count";
    public static String maximum = "maximum";
    public static String minimum = "minimum";
    public static String squareSum = "squareSum";
    public static String sum = "sum";
    public static String stop = "stop";
    public static String start = "start";
    public static String last = "last";
    public static String mean = "mean";
    public static String standardDeviation = "standardDeviation";
    public static String percentile50 = "percentile50";
    public static String percentile75 = "percentile75";
    public static String percentile95 = "percentile95";
    public static String percentile99 = "percentile99";

    public Metric() {
        super("metric");
    }

    public Metric(String name) {
        super("metric");
        reset();
    }

    public Metric(Map data) {
        super(data);
    }

    public Metric(String name, Entity entity, String... fieldsToCopy) {
        super(name, "metric", entity);
    }

    @Override
    public long getCount() {
        return getValueAsLong(Metric.count);
    }

    public void setCount(long count) {
        setValue(Metric.count, count);
    }

    @Override
    public DateTime getStart() {
        return getValueAsDateTime(Metric.start);
    }

    public void setStart(DateTime start) {
        setValue(Metric.start, start.toDateTimeISO());
    }

    @Override
    public DateTime getStop() {
        return getValueAsDateTime(Metric.stop);
    }

    public void setStop(DateTime stop) {
        setValue(Metric.stop, stop.toDateTimeISO());
    }
    
    public double getSum() {
        return getValueAsDouble(Metric.sum);
    }

    public void setSum(double sum) {
        setValue(Metric.sum, sum);
    }

    @Override
    public double getMinimum() {
        return getValueAsDouble(Metric.minimum);
    }

    public void setMinimum(double minimum) {
        setValue(Metric.minimum, minimum);
    }

    @Override
    public double getMaximum() {
        return getValueAsDouble(Metric.maximum);
    }

    public void setMaximum(double maximum) {
        setValue(Metric.maximum, maximum);
    }

    public double getSquareSum() {
        return getValueAsDouble(Metric.squareSum);
    }

    public void setSquareSum(double squareSum) {
        setValue(Metric.squareSum, squareSum);
    }

    @Override
    public double getLast() {
        return getValueAsDouble(Metric.last);
    }

    public void setLast(double last) {
        setValue(Metric.last, last);
    }

    @Override
    public double getPercentile50() {
        return getValueAsDouble(Metric.percentile50);
    }

    public void setPercentile50(double percentile50) {
        setValue(Metric.percentile50, percentile50);
    }

    @Override
    public double getPercentile75() {
        return getValueAsDouble(Metric.percentile75);
    }

    public void setPercentile75(double percentile75) {
        setValue(Metric.percentile75, percentile75);
    }

    @Override
    public double getPercentile95() {
        return getValueAsDouble(Metric.percentile95);
    }

    public void setPercentile95(double percentile95) {
        setValue(Metric.percentile95, percentile95);
    }

    @Override
    public double getPercentile99() {
        return getValueAsDouble(Metric.percentile99);
    }

    public void setPercentile99(double percentile99) {
        setValue(Metric.percentile99, percentile99);
    }

    @Override
    public double getMean() {
        return getValueAsDouble(Metric.mean);
    }

    public void setMean(double mean) {
        setValue(Metric.mean, mean);
    }

    @Override
    public double getStandardDeviation() {
        long count = getCount();
        double sum = getSum();
        if (count == 0) return 0.0;
        double sigma = (getSquareSum() / count) - ((sum * sum) / (count * count));
        return Math.sqrt(sigma);
    }

    @Override
    public void add(long value) {
        setMean((getMean() * getCount() + value) / (getCount() + 1));
        setCount(getCount() + 1);
        setMinimum(Math.min(getMinimum(), value));
        setMaximum(Math.max(getMaximum(), value));
        setSum(getSum() + value);
        setSquareSum(getSquareSum() + (value * value));
        setLast(value);
    }

    @Override
    public IEntity toEntity() {
        return this;
    }

    public void setStandardDeviation(double standardDeviation) {
        setValue(Metric.standardDeviation, standardDeviation);
    }

    @Override
    public void start() throws ManageableException {
        setStart(DateTime.now(DateTimeZone.UTC));
    }

    @Override
    public void stop() throws ManageableException {
        setStop(DateTime.now(DateTimeZone.UTC));
    }

    private void reset() {
        setCount(0);
        setMinimum(Double.MIN_VALUE);
        setMaximum(Double.MAX_VALUE);
        setSum(0.0);
        setSquareSum(0.0);
        setStart(DateTime.now(DateTimeZone.UTC));
        setStop(DateTime.now(DateTimeZone.UTC));
        setLast(0);
        setMean(0.0);
    }
}