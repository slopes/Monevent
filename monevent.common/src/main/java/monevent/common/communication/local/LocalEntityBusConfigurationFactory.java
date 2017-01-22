package monevent.common.communication.local;

import monevent.common.communication.EntityBusConfiguration;
import monevent.common.communication.IEntityBusConfigurationFactory;

/**
 * Created by slopes on 18/01/17.
 */
public class LocalEntityBusConfigurationFactory implements IEntityBusConfigurationFactory {
    @Override
    public EntityBusConfiguration build(String entityBusName) {
        return new LocalEntityBusConfiguration(entityBusName);
    }
}
