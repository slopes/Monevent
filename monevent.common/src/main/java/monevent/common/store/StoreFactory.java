package monevent.common.store;

import monevent.common.managers.ManageableBase;

/**
 * Created by steph on 13/03/2016.
 */
public class StoreFactory extends ManageableBase implements IStoreFactory {
    private final IStoreConfigurationFactory configurationFactory;

    public StoreFactory(String name, IStoreConfigurationFactory configurationFactory) {
        super(name);
        this.configurationFactory = configurationFactory;
    }

    @Override
    public IStore build(String storeName) {
        StoreConfiguration configuration = this.configurationFactory.buildStore(storeName);
        if (configuration!= null ) {
            return configuration.build();
        }
        return null;
    }

    @Override
    protected void doStart() {

    }

    @Override
    protected void doStop() {

    }
}
