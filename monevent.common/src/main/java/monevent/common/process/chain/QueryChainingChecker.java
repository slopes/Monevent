package monevent.common.process.chain;

import monevent.common.model.IEntity;
import monevent.common.model.query.IQuery;
import monevent.common.process.chain.memory.MemoryChainProcessor;

/**
 * Created by slopes on 08/02/17.
 */
public class QueryChainingChecker implements IChainingChecker {
    private IQuery query;

    public QueryChainingChecker(IQuery query) {
        this.query = query;
    }

    @Override
    public boolean isComplete(IEntity entity) {

        if (query != null && entity != null) {
            return this.query.match(entity);
        }
        return false;
    }
}
