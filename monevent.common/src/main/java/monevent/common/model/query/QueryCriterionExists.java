package monevent.common.model.query;

import monevent.common.model.IEntity;
import org.joda.time.DateTime;

/**
 * Created by slopes on 10/02/17.
 */
public class QueryCriterionExists extends QueryCriterion{
    public QueryCriterionExists() {
    }

    public QueryCriterionExists(String field) {
        super(field, null, QueryCriterionType.Exists);
    }

    public Boolean match(IEntity entity) {
        return entity.contains(getField());
    };
}
