package monevent.common.tools;

import com.eaio.uuid.UUID;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


/**
 * Created by Stephane on 18/11/2015.
 */
public class ComparableMap extends ConcurrentHashMap<String, Object> implements Comparable {
    protected static DateTimeFormatter dateTimeFormatter;

    static {
        dateTimeFormatter=ISODateTimeFormat.dateTimeParser();
    }

    public ComparableMap(int initialCapacity, float loadFactor) {
        super(initialCapacity, loadFactor);
    }

    public ComparableMap(int initialCapacity) {
        super(initialCapacity);
    }

    public ComparableMap() {
    }

    @Override
    public Object put(String key, Object value) {
        if (value != null) {
            return super.put(key, value);
        }
        return null;
    }

    public int compareTo(final Object other) {
        if (other == null) return 1;
        if (!other.getClass().isAssignableFrom(ComparableMap.class)) return -1;
        ComparableMap otherMap = (ComparableMap) other;
        final int compareSize = otherMap.size() - this.size();
        if (compareSize != 0) return compareSize;
        for (Entry<String, Object> entry : this.entrySet()) {
            String key = entry.getKey();
            Comparable value = (Comparable) entry.getValue();
            if (otherMap.containsKey(key)) {
                Comparable otherValue = (Comparable) otherMap.get(key);
                if (value != null && otherValue != null) {
                    int compareValue = value.compareTo(otherValue);
                    if (compareValue != 0) return compareValue;
                } else {
                    return (otherValue == null) ? -1 : 1;
                }
            } else {
                return 1;
            }
        }
        return 0;
    }

    public void setValue(String key, Comparable value) {
        if (value == null)  return;
        String[] keys = key.split("\\.");
        ComparableMap map = this;
        for (int fieldIndex = 0; fieldIndex < keys.length; fieldIndex++) {
            String subKey = keys[fieldIndex];
            if (fieldIndex == (keys.length - 1)) {
                String fieldKey = keys[fieldIndex];
                map.put(fieldKey, value);
            } else {
                if (!map.containsKey(subKey)) {
                    ComparableMap subMap = new ComparableMap();
                    map.put(subKey, subMap);
                    map = subMap;
                } else {
                    map = (ComparableMap) map.get(subKey);
                }
            }
        }
    }

    public Comparable getValue(String key) {
        String[] keys = key.split("\\.");
        ComparableMap map = this;
        for (int fieldIndex = 0; fieldIndex < keys.length; fieldIndex++) {
            String subKey = keys[fieldIndex];
            if (!map.containsKey(subKey)) return null;
            Comparable value = (Comparable) map.get(subKey);
            if (fieldIndex == (keys.length - 1)) return value;
            if (!(value instanceof ComparableMap)) return null;
            map = (ComparableMap) value;
        }
        return null; // never called
    }

    public DateTime getValueAsDateTime(String key) {
        Object value = getValue(key);
        if (null == value) return null;
        return dateTimeFormatter.parseDateTime(value.toString());
    }

    public Boolean getValueAsBoolean(String key) {
        Object value = getValue(key);
        if (null == value) return null;
        return Boolean.parseBoolean(value.toString());
    }

    public String getValueAsString(String key) {
        Object value = getValue(key);
        if (null == value) return null;
        return value.toString();
    }

    public Long getValueAsLong(String key) {
        Object value = getValue(key);
        if (null == value) return null;
        return Long.parseLong(value.toString());
    }

    public Integer getValueAsInteger(String key) {
        Object value = getValue(key);
        if (null == value) return null;
        return Integer.parseInt(value.toString());
    }

    public Double getValueAsDouble(String key) {
        Object value = getValue(key);
        if (null == value) return null;
        return Double.parseDouble(value.toString());
    }

    public UUID getValueAsUUID(String key) {
        Object value = getValue(key);
        if (null == value) return null;
        return new UUID(value.toString());

    }

    public Map getValueAsMap(String key) {
        Object value = getValue(key);
        if (null == value) return null;
        return (Map) value;
    }

    public <T extends Enum<T>> T getValueAsEnum(String field, Class<T> enumType, T defaultValue) {
        return OptionalObject.enumValue(get(field), enumType, defaultValue);
    }
}
