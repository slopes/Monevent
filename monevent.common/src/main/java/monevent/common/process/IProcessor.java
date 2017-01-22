package monevent.common.process;

import monevent.common.managers.IManageable;
import monevent.common.model.IEntity;

/**
 * Created by Stephane on 20/11/2015.
 */


public interface IProcessor extends IManageable {
    IEntity process(IEntity entity) throws ProcessorException;
}