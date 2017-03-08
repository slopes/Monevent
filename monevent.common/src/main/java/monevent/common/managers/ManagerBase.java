package monevent.common.managers;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.cache.RemovalListener;
import com.google.common.collect.ImmutableMap;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by steph on 12/03/2016.
 */
public abstract class ManagerBase<T extends IManageable> extends ManageableBase implements IManager {
    private AtomicBoolean isRunning;
    protected LoadingCache<String, T> manageables;

    protected ManagerBase(String name) {
        super(name);
        this.isRunning = new AtomicBoolean();
    }

    @Override
    protected void doStart() {
        CacheLoader<String, T> loader = new CacheLoader<String, T>() {
            public T load(String key) throws Exception {
                if (isRunning.get()) {
                    T manageable = build(key);
                    if (manageable != null) {
                        manageable.start();
                        return manageable;
                    } else {
                        throw new Exception(String.format("Manageable not found %s ", key));
                    }
                }
                else {
                    throw new Exception("The manager is not running.");
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
        this.isRunning.set(true);
    }

    protected abstract T build(String key);

    @Override
    protected void doStop() {
        this.isRunning.set(false);
        if (manageables != null) {
            manageables.invalidateAll();
        }
    }

    public <T extends IManageable> T get(String manageableFullName) {
        try {
            return (T) manageables.get(manageableFullName);
        } catch (ExecutionException e) {
            return null;
        }
    }
}
