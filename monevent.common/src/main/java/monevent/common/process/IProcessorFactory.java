package monevent.common.process;

import monevent.common.managers.IManageable;

/**
 * Created by steph on 12/03/2016.
 */
public interface IProcessorFactory extends IManageable {
    IProcessor build(String processorName, ProcessorManager processorManager);
}
