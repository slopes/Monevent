package monevent.common.process;

import monevent.common.communication.EntityBusManager;
import monevent.common.managers.ManageableBase;
import monevent.common.store.StoreManager;

/**
 * Created by steph on 29/02/2016.
 */

public class ProcessorFactory extends ManageableBase implements IProcessorFactory {

    private final EntityBusManager entityBusManager;
    private final StoreManager storeManager;
    private final IProcessorConfigurationFactory configurationFactory;

    public ProcessorFactory(EntityBusManager entityBusManager, StoreManager storeManager, IProcessorConfigurationFactory configurationFactory) {
        super("processFactory");
        this.entityBusManager = entityBusManager;
        this.storeManager = storeManager;
        this.configurationFactory = configurationFactory;
    }

    @Override
    protected void doStart() {

    }

    @Override
    protected void doStop() {

    }

    @Override
    public IProcessor build(String processorName,ProcessorManager processorManager) {
        ProcessorConfiguration configuration = this.configurationFactory.buildProcessor(processorName);
        if (configuration == null) return null;
        return configuration.build(this.entityBusManager,this.storeManager, processorManager);
    }
}
