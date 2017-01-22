package monevent.common.model.query;

/**
 * Created by steph on 23/03/2016.
 */

public class OrQueryOperator extends QueryBinaryOperator {
    public OrQueryOperator(IQuery leftQuery, IQuery rightQuery) {
        super("Or", leftQuery, rightQuery);
    }

    @Override
    protected boolean apply(boolean left, boolean right) {
        return (left || right);
    }
}
