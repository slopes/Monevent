package monevent.common.communication;

import monevent.common.managers.IManageable;
import monevent.common.model.IEntity;
import monevent.common.process.IProcessor;

/**
 * Created by steph on 20/03/2016.
 */
public interface IEntityBus extends IManageable {
    void register(IProcessor processor);

    void unregister(IProcessor processor);

    void publish(IEntity entity);
}
