package monevent.common.store;

import monevent.common.managers.ManageableBase;
import monevent.common.model.IEntity;
import monevent.common.model.query.IQuery;

import java.util.List;

/**
 * Created by slopes on 18/02/17.
 */
public class CrashingStore extends ManageableBase implements IStore {
    protected CrashingStore(String name) {
        super(name);
    }

    @Override
    public void create(List<IEntity> entities) throws StoreException {
        throw new StoreException();
    }

    @Override
    public void create(IEntity entity) throws StoreException {
        throw new StoreException("Cannot create entity");
    }

    @Override
    public IEntity read(String id) throws StoreException {
        throw new StoreException("Cannot read entity");
    }

    @Override
    public List<IEntity> read(IQuery query) throws StoreException {
        throw new StoreException("Cannot create entity");
    }

    @Override
    public void update(List<IEntity> entities) throws StoreException {
        throw new StoreException("Cannot update entity", new Exception("because it's impossible"));
    }

    @Override
    public void update(IEntity entity) throws StoreException {
        throw new StoreException(new Exception("Cannot update entity"));
    }

    @Override
    public void delete(String id) throws StoreException {
        throw new StoreException(new Exception("Cannot delete entity"));
    }

    @Override
    public void delete(IQuery IQuery) throws StoreException {
        throw new StoreException(new Exception("Cannot delete entity"));
    }

    @Override
    protected void doStart() {

    }

    @Override
    protected void doStop() {

    }
}
