package monevent.common.managers;

import com.google.common.collect.ImmutableMap;

/**
 * Created by steph on 12/03/2016.
 */
public interface IManager<T extends IManageable> {
    T load(String key);
    void unload(String key);
    ImmutableMap<String,T> getAll();
}
