package monevent.common.process;

import monevent.common.communication.EntityBusFactory;
import monevent.common.communication.EntityBusManager;
import monevent.common.communication.IEntityBusFactory;
import monevent.common.model.configuration.ConfigurationTest;
import monevent.common.model.configuration.factory.FileConfigurationFactory;
import monevent.common.store.IStoreFactory;
import monevent.common.store.StoreFactory;
import monevent.common.store.StoreManager;

/**
 * Created by steph on 12/03/2016.
 */
public class ProcessorTest extends ConfigurationTest {

    private final FileConfigurationFactory fileConfigurationFactory;
    private final IEntityBusFactory entityBusFactory;
    private final EntityBusManager entityBusManager;
    private final IStoreFactory storeFactory;
    private final StoreManager storeManager;
    private final IProcessorFactory processorFactory;
    private final ProcessorManager processorManager;


    public ProcessorTest() {
        this.fileConfigurationFactory = new FileConfigurationFactory("src/test/resources/config/");
        this.entityBusFactory = new EntityBusFactory("entityBusFactory",this.fileConfigurationFactory);
        this.entityBusManager = new EntityBusManager("entityBusManager", this.entityBusFactory);
        this.storeFactory = new StoreFactory("storeFactory", this.fileConfigurationFactory);
        this.storeManager = new StoreManager("storeManager", this.storeFactory);
        this.processorFactory = new ProcessorFactory(this.entityBusManager, this.storeManager, this.fileConfigurationFactory);
        this.processorManager = new ProcessorManager("processorManager", this.processorFactory);
    }

    protected void start() {
        this.fileConfigurationFactory.start();
        this.entityBusFactory.start();
        this.entityBusManager.start();
        this.storeFactory.start();
        this.storeManager.start();
        this.processorFactory.start();
        this.processorManager.start();
    }

    protected void stop() {
        this.processorManager.stop();
        this.processorFactory.stop();
        this.storeManager.stop();
        this.storeFactory.stop();
        this.entityBusManager.stop();
        this.entityBusFactory.stop();
        this.fileConfigurationFactory.stop();
    }

    public EntityBusManager getEntityBusManager() {
        return entityBusManager;
    }

    public ProcessorManager getProcessorManager() {
        return processorManager;
    }

    public StoreManager getStoreManager() {
        return storeManager;
    }
}