package monevent.common.store;

import monevent.common.managers.ManageableBase;
import monevent.common.model.Entity;
import monevent.common.model.IEntity;
import monevent.common.model.query.IQuery;

import java.util.List;

/**
 * Created by Stephane on 21/11/2015.
 */
public abstract class StoreBase extends ManageableBase implements IStore {

    protected StoreBase(String name) {
        super(name);
    }

    @Override
    public void create(List<IEntity> entities) throws StoreException {
        debug("Creating %d entities...", getEntityCount(entities));
        try {
            if (entities == null || entities.size() == 0) return;
            doCreate(entities);
        } catch (Exception error) {
            error("Cannot create %d entities.", error, getEntityCount(entities));
            throw new StoreException(trace("Cannot create %d entities.", getEntityCount(entities)), error);
        } finally {
            debug("... %d entities created", getEntityCount(entities));
        }
    }

    protected abstract void doCreate(List<IEntity> entities) throws StoreException;

    @Override
    public void create(IEntity entity) throws StoreException {
        String id = Entity.getEntityId(entity);
        debug("Creating entity %s ...",id );
        try {
            if (entity == null) return;
            doCreate(entity);
        } catch (Exception error) {
            error("Cannot process entity %s.", error, id);
            throw new StoreException(trace("Cannot process entity %s.", id), error);
        } finally {
            debug(" ... entity %s created.", id);
        }
    }

    protected abstract void doCreate(IEntity entity) throws StoreException;

    @Override
    public IEntity read(String id) throws StoreException {
        debug("Reading entity %s ...", id);
        try {
            if (id == null) return null;
            return doRead(id);
        } catch (Exception error) {
            error("Cannot read entity %s.", error, id);
            throw new StoreException(trace("Cannot read entity %s.", id), error);
        } finally {
            debug(" ... entity %s read.", id);
        }
    }

    protected abstract IEntity doRead(String id) throws StoreException;

    @Override
    public List<IEntity> read(IQuery IQuery) throws StoreException {
        debug("Reading entities ...");
        try {
            if (IQuery == null) return null;
            return doRead(IQuery);
        } catch (Exception error) {
            error("Cannot read entity.", error);
            throw new StoreException(trace("Cannot read entity."), error);
        } finally {
            debug(" ... entities read.");
        }
    }

    protected abstract List<IEntity> doRead(IQuery IQuery) throws StoreException;

    @Override
    public void update(List<IEntity> entities) throws StoreException {
        debug("Updating %d entities...", getEntityCount(entities));
        try {
            if (entities == null || entities.size() == 0) return;
            doUpdate(entities);
        } catch (Exception error) {
            error("Cannot update %d entities.", error, getEntityCount(entities));
            throw new StoreException(trace("Cannot update %d entities.", getEntityCount(entities)), error);
        } finally {
            debug("... %d entities updated", getEntityCount(entities));
        }
    }

    protected abstract void doUpdate(List<IEntity> entities) throws StoreException;

    @Override
    public void update(IEntity entity) throws StoreException {
        debug("Updating entity %s ...", Entity.getEntityId(entity));
        try {
            if (entity == null) return;
            doUpdate(entity);
        } catch (Exception error) {
            error("Cannot update entity %s.", error, Entity.getEntityId(entity));
            throw new StoreException(trace("Cannot update entity %s.", Entity.getEntityId(entity)), error);
        } finally {
            debug(" ... entity %s updated.", Entity.getEntityId(entity));
        }
    }

    protected abstract void doUpdate(IEntity entity) throws StoreException;

    @Override
    public void delete(String id) throws StoreException {
        debug("Deleting entity %s ...", id);
        try {
            if (id == null) return;
            doDelete(id);
        } catch (Exception error) {
            error("Cannot delete entity %s.", error, id);
            throw new StoreException(trace("Cannot delete entity %s.", id), error);
        } finally {
            debug(" ... entity %s deleted.", id);
        }
    }

    protected abstract void doDelete(String id) throws StoreException;

    @Override
    public void delete(IQuery IQuery) throws StoreException {
        debug("Deleting entities ...");
        try {
            if (IQuery == null) return;
            doDelete(IQuery);
        } catch (Exception error) {
            error("Cannot delete entity.", error);
            throw new StoreException(trace("Cannot delete entity."), error);
        } finally {
            debug(" ... entities deleted.");
        }
    }

    protected abstract void doDelete(IQuery IQuery) throws StoreException;

    protected int getEntityCount(List<IEntity> entities) {
        return (entities != null) ? entities.size() : 0;
    }

}
