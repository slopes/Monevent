package monevent.common.model.configuration.factory;

import monevent.common.communication.IEntityBusConfigurationFactory;
import monevent.common.process.IProcessorConfigurationFactory;
import monevent.common.store.IStoreConfigurationFactory;

/**
 * Created by steph on 14/03/2016.
 */
public interface IConfigurationFactory extends IEntityBusConfigurationFactory,IProcessorConfigurationFactory,IStoreConfigurationFactory{
}
