package monevent.common.communication;

import monevent.common.communication.local.LocalEntityBusConfigurationFactory;
import monevent.common.managers.ManagerBase;

/**
 * Created by steph on 12/03/2016.
 */
public class EntityBusManager extends ManagerBase<IEntityBus> {

    private IEntityBusFactory factory;

    public EntityBusManager(String name, IEntityBusFactory factory) {
        super(name);
        this.factory = factory;
    }

    public EntityBusManager(String name) {
        super(name);
        this.factory = new EntityBusFactory(name,new LocalEntityBusConfigurationFactory());
    }

    @Override
    protected IEntityBus build(String entityBusName) {
        return factory.build(entityBusName);
    }
}
