package monevent.common.model.configuration.factory;

import monevent.common.managers.ManageableBase;
import monevent.common.model.configuration.Configuration;

/**
 * Created by steph on 12/03/2016.
 */
public abstract class ConfigurationFactoryBase<T extends Configuration>  extends ManageableBase implements IConfigurationFactory<T>{

    protected ConfigurationFactoryBase(String name) {
        super(name);
    }

}
