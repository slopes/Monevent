package monevent.common.model;

import com.eaio.uuid.UUID;
import com.google.common.base.Function;
import com.google.common.collect.Lists;
import monevent.common.tools.SafeMap;
import org.joda.time.DateTime;

import java.util.List;
import java.util.Map;


public class Entity extends SafeMap implements IEntity {

    public static String id = "id";
    public static String type = "type";
    public static String name = "name";
    public static String timestamp = "timestamp";

    public Entity() {
        setId(new UUID().toString());
        setTimestamp(DateTime.now());
    }

    public Entity(Map data) {
        putAll(data);
    }

    public Entity(String type) {
        this();
        setType(type);
    }

    public Entity(String name, String type) {
        this(type);
        setName(name);
    }

    public Entity(String name, String type, Entity entity, String... fieldsToCopy) {
        this(name, type);
        if (entity != null) {
            copyFields(entity, false, fieldsToCopy);
        }
    }

    public Entity set(String key, Comparable value) {
        setValue(key, value);
        return this;
    }

    @Override
    public boolean contains(String field) {
        return containsKey(field);
    }

    @Override
    public IEntity clone() throws CloneNotSupportedException {
        return (IEntity) super.clone();
    }

    @Override
    public String getType() {
        return getValueAsString(Entity.type);
    }


    public void setType(String type) {
        setValue(Entity.type, type);
    }

    @Override
    public String getId() {
        return getValueAsString(Entity.id);
    }

    public void setId(String id) {
        setValue(Entity.id, id);
    }

    @Override
    public DateTime getTimestamp() {
        return getValueAsDateTime(Entity.timestamp);
    }

    public void setTimestamp(DateTime timestamp) {
        setValue(Entity.timestamp, timestamp);
    }

    @Override
    public String getName() {
        return getValueAsString(Entity.name);
    }

    public void setName(String name) {
        setValue(Entity.name, name);
    }

    public Entity copyFields(Entity other, boolean overrideIfPresent, String... fieldsToCopy) {
        for (String field : fieldsToCopy) {
            if (other.containsKey(field)) {
                if (overrideIfPresent || !this.containsKey(field)) {
                    this.put(field, other.get(field));
                }
            }
        }
        return this;
    }

    public static <T extends IEntity> List<IEntity> toList(List<T> items) {
        return Lists.transform(items, new Function<T, IEntity>() {
            public IEntity apply(T item) {
                return item;
            }
        });
    }

    public static <T extends IEntity> List<T> fromList(List<IEntity> items) {
        return Lists.transform(items, new Function<IEntity, T>() {
            public T apply(IEntity item) {
                return (T) item;
            }
        });
    }

    public static String getEntityName(IEntity entity) {
        return entity == null ? "null entity" : entity.getName();
    }

    public static String getEntityId(IEntity entity) {
        return entity == null ? "null entity" : entity.getId();
    }


}
