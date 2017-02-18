package monevent.common.tools;

import com.eaio.uuid.UUID;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


/**
 * Created by Stephane on 18/11/2015.
 */
public class SafeMap extends ConcurrentHashMap<String, Object>  {
    protected static DateTimeFormatter dateTimeFormatter;

    static {
        dateTimeFormatter = ISODateTimeFormat.dateTimeParser();
    }

    public SafeMap(int initialCapacity, float loadFactor) {
        super(initialCapacity, loadFactor);
    }

    public SafeMap(int initialCapacity) {
        super(initialCapacity);
    }

    public SafeMap() {
    }

    @Override
    public Object put(String key, Object value) {
        if (value != null) {
            return super.put(key, value);
        }
        return null;
    }

    public void setValue(String key, Object value) {
        if (value == null) return;
        String[] keys = key.split("\\.");
        SafeMap map = this;
        for (int fieldIndex = 0; fieldIndex < keys.length; fieldIndex++) {
            String subKey = keys[fieldIndex];
            if (fieldIndex == (keys.length - 1)) {
                String fieldKey = keys[fieldIndex];
                map.put(fieldKey, value);
            } else {
                if (!map.containsKey(subKey)) {
                    SafeMap subMap = new SafeMap();
                    map.put(subKey, subMap);
                    map = subMap;
                } else {
                    map = (SafeMap) map.get(subKey);
                }
            }
        }
    }

    public Object getValue(String key) {
        if ( !key.contains(".")) return get(key);
        String[] keys = key.split("\\.");
        Map map = this;
        for (int fieldIndex = 0; fieldIndex < keys.length; fieldIndex++) {
            String subKey = keys[fieldIndex];
            if (!map.containsKey(subKey)) return null;
            Object value = map.get(subKey);
            if (fieldIndex == (keys.length - 1)) return value;
            if (!(value instanceof Map)) return null;
            map = (Map) value;
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
        return Math.round(Double.parseDouble(value.toString()));
    }

    public Integer getValueAsInteger(String key) {
        Object value = getValue(key);
        if (null == value) return null;
        return (int) Math.round(Double.parseDouble(value.toString()));
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

    public <T> List<T> getValueAsList(String key) {
        Object value = getValue(key);
        if (null == value) return null;
        return (List) value;
    }

    public <T extends Enum<T>> T getValueAsEnum(String field, Class<T> enumType, T defaultValue) {
        return OptionalObject.enumValue(get(field), enumType, defaultValue);
    }
}
