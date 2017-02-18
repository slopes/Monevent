package monevent.common.model.metric;

import com.eaio.uuid.UUID;
import monevent.common.managers.ManageableBase;
import monevent.common.model.IEntity;
import monevent.common.tools.SafeMap;
import org.HdrHistogram.ConcurrentHistogram;
import org.HdrHistogram.Histogram;
import org.apache.commons.lang.NotImplementedException;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;


public class FixedMetric extends ManageableBase implements IMetric {

    private String id;
    private final Histogram data;
    private DateTime start;
    private DateTime stop;
    private AtomicLong last;
    private SafeMap criteria;

    public FixedMetric(String name, long highestTrackableValue,
                       int numberOfSignificantValueDigits) {
        super(name);
        this.data = new ConcurrentHistogram(highestTrackableValue, numberOfSignificantValueDigits);
        this.last = new AtomicLong();
        this.criteria = new SafeMap();
        this.start = DateTime.now();
    }

    @Override
    protected void doStart() {
        this.start = DateTime.now(DateTimeZone.UTC);
    }

    @Override
    protected void doStop() {
        this.stop = DateTime.now(DateTimeZone.UTC);
    }

    @Override
    public long getCount() {
        return this.data.getTotalCount();
    }

    @Override
    public DateTime getStart() {
        return this.start;
    }

    @Override
    public DateTime getStop() {
        return this.stop;
    }

    @Override
    public double getMinimum() {
        return this.data.getMinValue();
    }

    @Override
    public double getMaximum() {
        return this.data.getMaxValue();
    }

    @Override
    public double getLast() {
        return this.last.get();
    }

    @Override
    public double getPercentile50() {
        return this.data.getValueAtPercentile(50.0);
    }

    @Override
    public double getPercentile75() {
        return this.data.getValueAtPercentile(75.0);
    }

    @Override
    public double getPercentile95() {
        return this.data.getValueAtPercentile(95.0);
    }

    @Override
    public double getPercentile99() {
        return this.data.getValueAtPercentile(99.0);
    }

    @Override
    public double getMean() {
        return this.data.getMean();
    }

    @Override
    public double getStandardDeviation() {
        return this.data.getStdDeviation();
    }

    @Override
    public void add(double value) {
        this.last.set((long) value);
        this.data.recordValue((long) value);
    }

    @Override
    public IEntity toEntity() {
        Metric metric = new Metric(getName());
        metric.setStart(this.start);
        metric.setStop(DateTime.now(DateTimeZone.UTC));
        metric.setCount(data.getTotalCount());
        metric.setMean(data.getMean());
        metric.setMaximum(data.getMaxValue());
        metric.setMinimum(data.getMinValue());
        metric.setLast(this.last.get());
        metric.setStandardDeviation(data.getStdDeviation());
        metric.setPercentile50(data.getValueAtPercentile(50.0));
        metric.setPercentile75(data.getValueAtPercentile(75.0));
        metric.setPercentile95(data.getValueAtPercentile(95.0));
        metric.setPercentile99(data.getValueAtPercentile(99.0));
        if (id != null) {
            metric.setId(id);
        }
        metric.putAll(criteria);
        return metric;
    }

    @Override
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String getType() {
        return "metric";
    }

    @Override
    public DateTime getTimestamp() {
        return this.start;
    }

    public IEntity set(String key, Comparable value) {
        this.criteria.setValue(key, value);
        return this;
    }

    @Override
    public boolean contains(String field) {
        return this.criteria.containsKey(field);
    }

    @Override
    public void setValue(String key, Object value) {
        throw new NotImplementedException("The method setValue is not authorized on metrics");
    }

    @Override
    public Object getValue(String key) {
        return this.criteria.getValue(key);
    }

    @Override
    public DateTime getValueAsDateTime(String key) {
        return this.criteria.getValueAsDateTime(key);
    }

    @Override
    public Boolean getValueAsBoolean(String key) {
        return this.criteria.getValueAsBoolean(key);
    }

    @Override
    public String getValueAsString(String key) {
        return this.criteria.getValueAsString(key);
    }

    @Override
    public Long getValueAsLong(String key) {
        return this.criteria.getValueAsLong(key);
    }

    @Override
    public Integer getValueAsInteger(String key) {
        return this.criteria.getValueAsInteger(key);
    }

    @Override
    public Double getValueAsDouble(String key) {
        return this.criteria.getValueAsDouble(key);
    }

    @Override
    public UUID getValueAsUUID(String key) {
        return this.criteria.getValueAsUUID(key);
    }

    @Override
    public Map getValueAsMap(String key) {
        return this.criteria.getValueAsMap(key);
    }

    @Override
    public <T> List<T> getValueAsList(String key) {
        return this.criteria.getValueAsList(key);
    }

    @Override
    public <T extends Enum<T>> T getValueAsEnum(String field, Class<T> enumType, T defaultValue) {
        return this.criteria.getValueAsEnum(field,enumType,defaultValue);
    }

    @Override
    public IEntity clone() throws CloneNotSupportedException {
        return (IEntity) super.clone();
    }


}
