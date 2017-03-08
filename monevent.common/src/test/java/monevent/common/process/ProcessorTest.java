package monevent.common.process;

import monevent.common.communication.EntityBusConfiguration;
import monevent.common.communication.IEntityBus;
import monevent.common.managers.ManageableFactory;
import monevent.common.managers.Manager;
import monevent.common.model.configuration.ConfigurationTest;
import monevent.common.model.configuration.factory.file.FileConfigurationFactory;
import monevent.common.store.StoreConfiguration;

/**
 * Created by steph on 12/03/2016.
 */
public class ProcessorTest extends ConfigurationTest {
    protected final ManageableFactory factory;
    protected final Manager manager;

    public ProcessorTest() {
        //Step 1 : Create the factories
        this.factory = new ManageableFactory();

        //Step 2 : Create the managers
        this.manager = new Manager(this.factory);
    }

    protected void start() {

        addConfiguration();

        this.factory.start();
        this.manager.start();
    }

    protected void addConfiguration() {
        this.factory.addFactory(new FileConfigurationFactory<>("configurationFileFactory", "src/test/resources/config/"));
    }


    protected void stop() {
        this.manager.stop();
        this.factory.stop();
    }


}