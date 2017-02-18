package monevent.common.model.query;

import monevent.common.model.IEntity;

/**
 * Created by slopes on 10/02/17.
 */
public class QueryCriterionNotExists extends QueryCriterion {

    public QueryCriterionNotExists() {
    }

    public QueryCriterionNotExists(String field) {
        super(field, null, QueryCriterionType.NotExists);
    }

    public Boolean match(IEntity entity) {
        return !entity.contains(getField());
    }
}
