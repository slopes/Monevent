package monevent.common.model.query;

import monevent.common.model.IEntity;
import org.joda.time.DateTime;

/**
 * Created by slopes on 10/02/17.
 */
public class QueryCriterionGreaterThan extends QueryCriterion{

    public QueryCriterionGreaterThan() {
    }

    public QueryCriterionGreaterThan(String field, Object value) {
        super(field, value, QueryCriterionType.GreaterThan);
    }

    public Boolean match(IEntity entity) {
        Object value = getValue();
        Object entityValue = entity.getValue(getField());
        if (value == null || entityValue == null) return false;
        if (value instanceof DateTime && entityValue instanceof DateTime) {
            return (((DateTime) value).isBefore((DateTime) entityValue));
        } else {
            if (value instanceof Comparable && entityValue instanceof Comparable) {
                return ((Comparable) value).compareTo(((Comparable) entityValue)) < 0;
            }
            return false;
        }
    };
}
