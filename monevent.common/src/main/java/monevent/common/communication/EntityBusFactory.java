package monevent.common.communication;

import monevent.common.managers.ManageableBase;

/**
 * Created by steph on 20/03/2016.
 */
public class EntityBusFactory extends ManageableBase implements IEntityBusFactory {
    private final IEntityBusConfigurationFactory configurationFactory;

    public EntityBusFactory(String name, IEntityBusConfigurationFactory configurationFactory) {
        super(name);
        this.configurationFactory = configurationFactory;
    }

    @Override
    public IEntityBus build(String entityBusName) {
        EntityBusConfiguration configuration = this.configurationFactory.build(entityBusName);
        if (configuration!= null ) {
            return configuration.build();
        }
        return null;
    }

    @Override
    protected void doStart() {

    }

    @Override
    protected void doStop() {

    }
}