package monevent.common.model.query;

import com.eaio.uuid.UUID;
import monevent.common.model.IEntity;

import java.util.List;

/**
 * Created by steph on 23/03/2016.
 */
public abstract class QueryOperator implements  IQuery {

    private String name;
    private String id;

    protected QueryOperator(String name) {
        this.name = name;
        this.id = new UUID().toString();
    }

    public String getName() {
        return name;
    }

    @Override
    public String getId() {
        return id;
    }


    @Override
    public String getSortField() {
        //TODO : refactor with copy
        return null;
    }

    @Override
    public QueryOrder getSortOrder() {
        //TODO : refactor with copy
        return null;
    }

    @Override
    public List<QueryCriterion> getCriteria() {
        //TODO : refactor with copy
        return null;
    }
}
