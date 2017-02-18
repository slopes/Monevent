package monevent.common.process.store;

import monevent.common.model.IEntity;
import monevent.common.model.query.IQuery;
import monevent.common.process.ProcessorBase;
import monevent.common.store.IStore;

/**
 * Created by Stephane on 21/11/2015.
 */
public class StoreProcessor extends ProcessorBase {

    private final IStore store;

    public StoreProcessor(String name, IQuery query, IStore store) {
        super(name, query);
        this.store = store;
    }

    @Override
    protected IEntity doProcess(IEntity entity) throws Exception {
        //info("Processing entity %s %s",entity.getType(),entity.getId());
        if (this.store != null) {
            this.store.create(entity);
        }
        return entity;
    }


    @Override
    protected void doStart() {

    }

    @Override
    protected void doStop() {

    }

}
