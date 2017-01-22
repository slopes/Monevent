package monevent.common.store;

/**
 * Created by steph on 13/03/2016.
 */
public interface IStoreConfigurationFactory {
    StoreConfiguration buildStore(String storeName);
}
