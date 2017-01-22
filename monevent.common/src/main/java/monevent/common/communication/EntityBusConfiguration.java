package monevent.common.communication;

import monevent.common.model.configuration.Configuration;
import monevent.common.model.query.IQuery;

/**
 * Created by steph on 20/03/2016.
 */
public abstract class EntityBusConfiguration extends Configuration {

    protected EntityBusConfiguration() {
        super();
    }

    protected EntityBusConfiguration(String name) {
        super(name);
    }

    public abstract IEntityBus build();
}