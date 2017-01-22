package monevent.common.model.query;

/**
 * Created by steph on 23/03/2016.
 */
public class AndQueryOperator extends QueryBinaryOperator {
    public AndQueryOperator(IQuery leftQuery, IQuery rightQuery) {
        super("And", leftQuery, rightQuery);
    }

    @Override
    protected boolean apply(boolean left, boolean right) {
        return (left && right);
    }
}
