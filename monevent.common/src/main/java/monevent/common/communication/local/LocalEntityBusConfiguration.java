package monevent.common.communication.local;

import monevent.common.communication.EntityBusConfiguration;
import monevent.common.communication.IEntityBus;

/**
 * Created by slopes on 18/01/17.
 */
public class LocalEntityBusConfiguration extends EntityBusConfiguration {

    public LocalEntityBusConfiguration() {
        super();
    }

    public LocalEntityBusConfiguration(String name) {
        super(name);
    }

    @Override
    public IEntityBus build() {
        return new LocalEntityBus(getName());
    }
}
