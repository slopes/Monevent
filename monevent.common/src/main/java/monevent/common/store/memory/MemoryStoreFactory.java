package monevent.common.store.memory;

import monevent.common.managers.ManageableBase;
import monevent.common.store.IStore;
import monevent.common.store.IStoreFactory;

/**
 * Created by slopes on 04/02/17.
 */
public class MemoryStoreFactory extends ManageableBase implements IStoreFactory {

    public MemoryStoreFactory() {
        super("memoryStoreFactory");
    }

    @Override
    public IStore build(String storeName) {
        return new MemoryStore(storeName);
    }

    @Override
    protected void doStart() {

    }

    @Override
    protected void doStop() {

    }
}
