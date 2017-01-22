package monevent.common.process;

import monevent.common.managers.IManageable;

import java.util.List;

/**
 * Created by steph on 12/03/2016.
 */
public interface IProcessorConfigurationFactory extends IManageable{
    ProcessorConfiguration buildProcessor(String processorName);
}
