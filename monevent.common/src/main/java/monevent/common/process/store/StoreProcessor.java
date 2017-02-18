package monevent.common.process.store;

import com.fasterxml.jackson.databind.ObjectMapper;
import monevent.common.managers.ManageableBase;
import monevent.common.model.Entity;
import monevent.common.model.IEntity;
import monevent.common.model.query.IQuery;
import monevent.common.model.store.StoreEvent;
import monevent.common.process.ProcessorBase;
import monevent.common.store.IStore;
import monevent.common.store.StoreException;

import java.util.List;
import java.util.Map;

/**
 * Created by Stephane on 21/11/2015.
 */
public class StoreProcessor extends ProcessorBase {

    private static ObjectMapper mapper;

    static {
        mapper = new ObjectMapper();
    }

    private final IStore store;

    public StoreProcessor(String name, IQuery query, IStore store) {
        super(name, query);
        this.store = store;
    }

    @Override
    protected IEntity doProcess(IEntity entity) throws Exception {
        //info("Processing entity %s %s",entity.getType(),entity.getId());
        if (entity instanceof StoreEvent) {
            StoreEvent storeEvent = (StoreEvent) entity;
            switch (storeEvent.getAction()) {
                case CREATE:
                    create(storeEvent.getData());
                    break;
                case UPDATE:
                    update(storeEvent.getData());
                    break;
                case DELETE:
                    delete(storeEvent.getData());
                    break;
            }
        } else {
            create(entity);
        }
        return entity;
    }

    private void delete(Object data) throws StoreException {
        if (data == null) return;

        if (data instanceof String) {
            if (this.store != null) {
                this.store.delete((String) data);
            }
        }

        if (data instanceof IQuery) {
            if (this.store != null) {
                this.store.delete((IQuery) data);
            }
        }

    }

    private void update(Object data) throws StoreException {
        if (data == null) return;

        if (data instanceof List) {
            if (this.store != null) {
                this.store.update((List) data);
            }
        }

        if (data instanceof IEntity) {
            if (this.store != null) {
                this.store.update((IEntity) data);
            }
        }
    }

    private void create(Object data) throws StoreException {
        if (data == null) return;

        if (data instanceof List) {
            if (this.store != null) {
                this.store.create((List) data);
            }
        }

        if (data instanceof IEntity) {
            if (this.store != null) {
                this.store.create((IEntity) data);
            }
        }

    }

    @Override
    protected void doStart() {

    }

    @Override
    protected void doStop() {

    }

}
