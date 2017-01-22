package monevent.common.store;

import monevent.common.managers.IManageable;

/**
 * Created by steph on 12/03/2016.
 */
public interface IStoreFactory extends IManageable{
    IStore build(String storeName);
}
