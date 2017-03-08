package monevent.common.model.configuration.factory;

import monevent.common.managers.IManageable;
import monevent.common.model.configuration.Configuration;

/**
 * Created by steph on 14/03/2016.
 */
public interface IConfigurationFactory<T extends Configuration> extends IManageable {
    T build(String key);

    boolean canBuild(String name);

    void add(T configuration);
}
