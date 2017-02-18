package monevent.common.model.query;

import monevent.common.model.IEntity;
import org.joda.time.DateTime;

/**
 * Created by slopes on 10/02/17.
 */
public class QueryCriterionGreaterOrEqualThan extends QueryCriterion {

    public QueryCriterionGreaterOrEqualThan() {
    }

    public QueryCriterionGreaterOrEqualThan(String field, Object value) {
        super(field, value, QueryCriterionType.GreaterOrEqualThan);
    }

    public Boolean match(IEntity entity) {
        Object value = getValue();
        Object entityValue = entity.getValue(getField());
        if (value == null || entityValue == null) return false;
        if (value instanceof DateTime && entityValue instanceof DateTime) {
            return entityValue.equals(value) || (((DateTime) value).isBefore((DateTime) entityValue));
        } else {
            if (value instanceof Comparable && entityValue instanceof Comparable) {
                return ((Comparable) value).compareTo(((Comparable) entityValue)) <= 0;
            }
            return false;
        }
    }
}
