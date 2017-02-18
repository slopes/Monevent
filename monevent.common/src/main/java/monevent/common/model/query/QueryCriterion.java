package monevent.common.model.query;

import com.eaio.uuid.UUID;
import monevent.common.model.Entity;
import monevent.common.model.IEntity;
import org.joda.time.DateTime;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;

/**
 * Created by Stephane on 20/11/2015.
 */

public abstract class QueryCriterion implements Serializable {
    private final String field;
    private final Object value;
    private final QueryCriterionType type;

    protected QueryCriterion() {
        this.field = null;
        this.value = 0;
        this.type = QueryCriterionType.Is;
    }

    protected QueryCriterion(String field, Object value, QueryCriterionType type) {
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

    public abstract Boolean match(IEntity entity) ;

}
