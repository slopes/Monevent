package monevent.common.model.query;

import monevent.common.model.IEntity;

/**
 * Created by slopes on 10/02/17.
 */
public class QueryCriterionNotIs extends QueryCriterion {

    public QueryCriterionNotIs() {
    }

    public QueryCriterionNotIs(String field, Object value) {
        super(field, value, QueryCriterionType.NotIs);
    }

    public Boolean match(IEntity entity) {
        Object value = getValue();
        Object entityValue = entity.getValue(getField());
        if (value == null || entityValue == null) return false;
        return !value.equals(entityValue);
    }
}
