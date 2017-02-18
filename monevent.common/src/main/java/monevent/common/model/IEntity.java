package monevent.common.model;

import com.eaio.uuid.UUID;
import org.joda.time.DateTime;

import java.io.Serializable;
import java.util.List;
import java.util.Map;


public interface IEntity extends IDistinguishable,Serializable,Cloneable {
    String getType();

    DateTime getTimestamp();

    boolean contains(String field);

    void setValue(String key, Object value);

    Object getValue(String key);

    DateTime getValueAsDateTime(String key);

    Boolean getValueAsBoolean(String key);

    String getValueAsString(String key);

    Long getValueAsLong(String key);

    Integer getValueAsInteger(String key);

    Double getValueAsDouble(String key);

    UUID getValueAsUUID(String key);

    Map getValueAsMap(String key);

    <T> List<T> getValueAsList(String key);

    <T extends Enum<T>> T getValueAsEnum(String field, Class<T> enumType, T defaultValue);

    IEntity clone() throws CloneNotSupportedException;
}