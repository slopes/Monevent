package monevent.common.store;


public class CrashingStoreConfiguration extends StoreConfiguration {
    @Override
    public IStore build() {
        return new CrashingStore("Let's put an end to your misery !!!");
    }
}
