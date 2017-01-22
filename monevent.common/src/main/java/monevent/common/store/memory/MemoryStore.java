package monevent.common.store.memory;

import com.google.common.collect.ImmutableList;
import monevent.common.model.IEntity;
import monevent.common.model.query.IQuery;
import monevent.common.store.StoreBase;
import monevent.common.store.StoreException;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Stephane on 21/11/2015.
 */
public class MemoryStore extends StoreBase {
    private final Map<String, IEntity> entities;

    public MemoryStore() {
        this("MemoryStore");
    }

    public MemoryStore(String name) {
        super(name);
        this.entities = new ConcurrentHashMap<>();
    }

    @Override
    protected void doCreate(List<IEntity> entities) throws StoreException {
        for (IEntity entity : entities) {
            doCreate(entity);
        }
    }

    @Override
    protected void doCreate(IEntity entity) throws StoreException {
        if (entity.getId() == null) throw new StoreException("Entity cannot have null id.");
        this.entities.put(entity.getId(), entity);
    }

    @Override
    protected IEntity doRead(String id) throws StoreException {
        return this.entities.get(id);
    }

    @Override
    protected List<IEntity> doRead(IQuery query) throws StoreException {
        ImmutableList.Builder<IEntity> entities = new ImmutableList.Builder<IEntity>();
        for (IEntity entity : this.entities.values()) {
            if (entity != null && query.match(entity)) {
                entities.add(entity);
            }
        }
        return entities.build();
    }

    @Override
    protected void doUpdate(List<IEntity> entities) throws StoreException {
        doCreate(entities);
    }

    @Override
    protected void doUpdate(IEntity entity) throws StoreException{
        doCreate(entity);
    }

    @Override
    protected void doDelete(String id) throws StoreException{
        this.entities.remove(id);
    }

    @Override
    protected void doDelete(IQuery IQuery) throws StoreException {
        List<IEntity> entities = doRead(IQuery);
        if ( entities!= null && entities.size() > 0 ) {
            for (IEntity entity: entities) {
                doDelete(entity.getId());
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
