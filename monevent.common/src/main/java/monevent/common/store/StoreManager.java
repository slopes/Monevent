package monevent.common.store;

import monevent.common.managers.ManagerBase;

/**
 * Created by steph on 12/03/2016.
 */
public class StoreManager extends ManagerBase<IStore> {

    private IStoreFactory storeFactory;

    public StoreManager(String name, IStoreFactory storeFactory) {
        super(name);
        this.storeFactory = storeFactory;
    }

    @Override
    protected IStore build(String key) {
        return storeFactory.build(key);
    }

}
