package monevent.common.model.query;

import monevent.common.model.IEntity;

import java.util.Collection;

/**
 * Created by slopes on 10/02/17.
 */
public class QueryCriterionCount extends QueryCriterion{
    public QueryCriterionCount() {
    }

    public QueryCriterionCount(String field, Object value) {
        super(field, value, QueryCriterionType.Count);
    }

    public Boolean match(IEntity entity) {
        Object value = getValue();
        Object entityValue = entity.getValue(getField());
        if (value == null || entityValue == null) return false;
        if (entityValue instanceof Collection) {
            return value.equals(((Collection) entityValue).size());
        }
        return false;
    };
}
