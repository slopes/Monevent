package monevent.common.model.query;

import monevent.common.model.IEntity;
import org.joda.time.DateTime;

/**
 * Created by slopes on 10/02/17.
 */
public class QueryCriterionIs extends QueryCriterion {

    public QueryCriterionIs() {
    }

    public QueryCriterionIs(String field, Object value) {
        super(field, value, QueryCriterionType.Is);
    }

    public Boolean match(IEntity entity) {
        Object value = getValue();
        Object entityValue = entity.getValue(getField());
        if (value == null || entityValue == null) return false;
        return value.equals(entityValue);
    }
}
