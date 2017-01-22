package monevent.common.model.query;

import com.eaio.uuid.UUID;
import monevent.common.model.Entity;
import monevent.common.model.IEntity;
import org.joda.time.DateTime;

import java.io.Serializable;
import java.util.Map;

/**
 * Created by Stephane on 20/11/2015.
 */

public class QueryCriterion implements Serializable {
    private final String field;
    private final Object value;
    private final QueryCriterionType type;

    public QueryCriterion() {
        this.field = null;
        this.value = 0;
        this.type = QueryCriterionType.Is;
    }

    public QueryCriterion(Map data) {
        this.field = null;
        this.value = 0;
        this.type = QueryCriterionType.Is;
    }

    public QueryCriterion(String field, Comparable value, QueryCriterionType type) {
        this.field = field;
        this.value = value;
        this.type = type;
    }

    public String getField() {
        return field;
    }

    public Comparable getValue() {
        return (Comparable) value;
    }

    public QueryCriterionType getType() {
        return type;
    }

    public Boolean match(IEntity entity) {
        if (entity == null) return false;
        if (this.field == null) return true;
        if (this.value == null) return false;
        try {
            Comparable fieldValue = getFieldValue(entity);
            return doMatch(fieldValue);
        } catch (Exception error) {
            //TODO : log error
        }
        return false;
    }

    private Comparable getFieldValue(IEntity entity) {
        Entity localEntity = (Entity) entity;
        if (this.value instanceof Integer) return localEntity.getValueAsInteger(this.field);
        if (this.value instanceof Double) return localEntity.getValueAsDouble(this.field);
        if (this.value instanceof DateTime) return localEntity.getValueAsDateTime(this.field);
        if (this.value instanceof Boolean) return localEntity.getValueAsBoolean(this.field);
        if (this.value instanceof String) return localEntity.getValueAsString(this.field);
        if (this.value instanceof UUID) return localEntity.getValueAsUUID(this.field);
        if (this.value instanceof Long) return localEntity.getValueAsLong(this.field);
        return entity.getValue(this.field);
    }

    public Boolean doMatch(Comparable value) {
        switch (this.getType()) {
            case None:
                return true;
            case Is:
                return matchIs(value);
            case LesserThan:
                return matchLessThan(value);
            case LesserOrEqualThan:
                return matchLessOrEqualThan(value);
            case GreaterThan:
                return matchGreaterThan(value);
            case GreaterOrEqualThan:
                return matchGreaterOrEqualThan(value);
            case NotIs:
                return !matchIs(value);
        }
        return true;
    }

    private Boolean matchGreaterOrEqualThan(Comparable value) {
        if (this.value instanceof DateTime) {
            return this.value.equals(value) || (((DateTime) this.value).isBefore((DateTime) value));
        } else {
            return (this.getValue().compareTo(value) <= 0);
        }
    }

    private Boolean matchGreaterThan(Comparable value) {
        if (this.value instanceof DateTime) {
            return (((DateTime) this.value).isBefore((DateTime) value));
        } else {
            return (this.getValue().compareTo(value) < 0);
        }
    }

    private Boolean matchLessOrEqualThan(Comparable value) {
        if (this.value instanceof DateTime) {
            return this.value.equals(value) || (((DateTime) this.value).isAfter((DateTime) value));
        } else {
            return (this.getValue().compareTo(value) >= 0);
        }
    }

    private Boolean matchLessThan(Comparable value) {
        if (this.value instanceof DateTime) {
            return (((DateTime) this.value).isAfter((DateTime) value));
        } else {
            return (this.getValue().compareTo(value) > 0);
        }
    }

    private Boolean matchIs(Comparable value) {
        if (this.value instanceof DateTime) {
            return this.value.equals(value);
        } else {
            return (this.getValue().compareTo(value) == 0);
        }
    }

}
