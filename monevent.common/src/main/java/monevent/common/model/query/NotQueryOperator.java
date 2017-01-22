package monevent.common.model.query;

/**
 * Created by steph on 23/03/2016.
 */
public class NotQueryOperator extends QueryUnaryOperator {
    public NotQueryOperator(IQuery query) {
        super("Not", query);
    }

    @Override
    protected boolean apply(boolean match) {
        return !match;
    }
}
