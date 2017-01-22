package monevent.common.communication;

import monevent.common.managers.IManageable;

/**
 * Created by steph on 20/03/2016.
 */
public interface IEntityBusFactory extends IManageable {
    IEntityBus build(String entityBusName);
}
