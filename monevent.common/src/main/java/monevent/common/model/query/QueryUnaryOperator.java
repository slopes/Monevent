package monevent.common.model.query;

import monevent.common.model.IEntity;

/**
 * Created by steph on 23/03/2016.
 */
public abstract  class QueryUnaryOperator extends QueryOperator {
    private IQuery query;

    public QueryUnaryOperator(String name, IQuery query) {
        super(name);
        this.query = query;
    }

    @Override
    public boolean match(IEntity entity) {
        if ( query != null ) {
            return apply(query.match(entity));
        }
        return false;
    }

    protected abstract boolean apply(boolean match);
}
