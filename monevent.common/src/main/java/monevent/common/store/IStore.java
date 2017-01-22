package monevent.common.store;

import monevent.common.managers.IManageable;
import monevent.common.model.IEntity;
import monevent.common.model.query.IQuery;

import java.util.List;

/**
 * Created by Stephane on 21/11/2015.
 */
public interface IStore extends IManageable {
    void create(List<IEntity> entities) throws StoreException;
    void create(IEntity entity) throws StoreException;
    IEntity read(String id) throws StoreException;
    List<IEntity> read(IQuery query) throws StoreException;
    void update(List<IEntity> entities) throws StoreException;
    void update(IEntity entity) throws StoreException;
    void delete(String id) throws StoreException;
    void delete(IQuery IQuery) throws StoreException;
}