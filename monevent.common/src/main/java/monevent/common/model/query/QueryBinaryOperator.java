package monevent.common.model.query;

import monevent.common.model.IEntity;

/**
 * Created by steph on 23/03/2016.
 */
public abstract class QueryBinaryOperator extends QueryOperator {
    private String name;
    private String id;
    private IQuery leftQuery;
    private IQuery rightQuery;


    protected QueryBinaryOperator(String name, IQuery leftQuery, IQuery rightQuery) {
        super(name);
        this.leftQuery = leftQuery;
        this.rightQuery = rightQuery;
    }


    @Override
    public boolean match(IEntity entity) {
        boolean left = false;
        if (this.leftQuery != null) {
            left = this.leftQuery.match(entity);
        }
        boolean right = false;
        if (this.rightQuery != null) {
            right = this.rightQuery.match(entity);
        }
        return apply(left, right);
    }

    protected abstract boolean apply(boolean left, boolean right);
}
