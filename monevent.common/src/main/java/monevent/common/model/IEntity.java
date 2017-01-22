package monevent.common.model;

import com.eaio.uuid.UUID;
import com.google.common.collect.ImmutableMap;
import org.joda.time.DateTime;

import java.io.Serializable;
import java.util.Map;

/**
 * Created by Stephane on 20/12/2015.
 */
public interface IEntity extends IDistinguishable,Comparable,Serializable,Cloneable {
    String getType();

    DateTime getTimestamp();

    boolean contains(String field);

    Comparable getValue(String key);

    DateTime getValueAsDateTime(String key);

    Boolean getValueAsBoolean(String key);

    String getValueAsString(String key);

    Long getValueAsLong(String key);

    Integer getValueAsInteger(String key);

    Double getValueAsDouble(String key);

    UUID getValueAsUUID(String key);

    Map getValueAsMap(String key);

    <T extends Enum<T>> T getValueAsEnum(String field, Class<T> enumType, T defaultValue);

    IEntity clone() throws CloneNotSupportedException;
}