package monevent.common.managers;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.cache.RemovalListener;
import com.google.common.collect.ImmutableMap;

/**
 * Created by steph on 12/03/2016.
 */
public abstract class ManagerBase<T extends IManageable> extends ManageableBase implements IManager<T> {
    private LoadingCache<String, T> manageables;

    protected ManagerBase(String name) {
        super(name);
    }

    @Override
    protected void doStart() {
        CacheLoader<String, T> loader = new CacheLoader<String, T>() {
            public T load(String key) throws Exception {
                T manageable = build(key);
                if (manageable != null) {
                    manageable.start();
                    return manageable;
                } else {
                    throw new Exception(String.format("Manageable not found %s ", key));
                }
            }
        };

        final RemovalListener<String, T> listener = notification -> {
            final T manageable = notification.getValue();
            if (manageable != null) {
                manageable.stop();
            }
        };

        this.manageables = CacheBuilder.newBuilder()
                .removalListener(listener)
                .build(loader);
    }

    protected abstract T build(String key);

    @Override
    protected void doStop() {
        if (manageables != null) {
            manageables.invalidateAll();
        }
    }

    @Override
    public T load(String key) {
        if (manageables != null) {
            return manageables.getUnchecked(key);
        }
        return null;
    }


    @Override
    public void unload(String key) {
        if (manageables != null) {
            manageables.invalidate(key);
        }
    }

    @Override
    public ImmutableMap<String,T> getAll() {
        return ImmutableMap.copyOf(manageables.asMap());
    }


}
