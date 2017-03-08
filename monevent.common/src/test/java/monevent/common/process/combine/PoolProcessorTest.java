package monevent.common.process.combine;

import monevent.common.communication.EntityBusConfiguration;
import monevent.common.model.Entity;
import monevent.common.model.IEntity;
import monevent.common.model.configuration.factory.memory.MemoryConfigurationFactory;
import monevent.common.process.*;
import monevent.common.store.IStore;
import monevent.common.store.StoreConfiguration;
import monevent.common.store.StoreException;
import monevent.common.store.memory.MemoryStoreConfiguration;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;

import static org.junit.Assert.fail;

/**
 * Created by slopes on 15/01/17.
 */
public class PoolProcessorTest extends ProcessorTest {

    @Before
    public void setUpClass() {
        start();
    }

    @After
    public void tearDownClass() {
        stop();
    }

    @Override
    protected void addConfiguration() {

        MemoryConfigurationFactory<EntityBusConfiguration> entityBusConfigurationFactory = new MemoryConfigurationFactory<EntityBusConfiguration>("entityBusConfigurationFactory");
        this.factory.addFactory(entityBusConfigurationFactory);

        MemoryConfigurationFactory<StoreConfiguration> storeConfigurationFactory = new MemoryConfigurationFactory<StoreConfiguration>("storeConfigurationFactory");
        storeConfigurationFactory.add(new MemoryStoreConfiguration("resultStore"));
        this.factory.addFactory(storeConfigurationFactory);


        Predicate<IEntity> predicate = entity -> {
            try {
                IStore resultStore = this.manager.get("resultStore");
                IEntity result = resultStore.read("result");
                int delta = entity.getValueAsInteger("delta");
                AtomicInteger eventProcessed = (AtomicInteger) result.getValue("eventProcessed");
                Thread.sleep(100);
                eventProcessed.addAndGet(delta);
            } catch (InterruptedException error) {
                fail(error.getMessage());
            } catch (StoreException error) {
                fail(error.getMessage());
            }
            return true;
        };

        MemoryConfigurationFactory processorConfigurationFactory = new MemoryConfigurationFactory<ProcessorConfiguration>("processorConfigurationFactory");
        processorConfigurationFactory.add(new GenericProcessorConfiguration("genericProcessor", predicate));
        this.factory.addFactory(processorConfigurationFactory);
    }

    @Test
    public void testConfiguration() {
        String name = "poolProcessor";
        String processorName = "delayProcessor";
        int poolSize = 2;
        ProcessorConfiguration configurationWrite = new PoolProcessorConfiguration(name, null, processorName, poolSize);
        File file = new File("src/test/resources/config/processor/" + name + ".json");
        try {
            write(file, configurationWrite);
            PoolProcessorConfiguration configurationRead = (PoolProcessorConfiguration) read(file);
            Assert.assertEquals(name, configurationRead.getName());
            Assert.assertEquals(processorName, configurationRead.getProcessorName());
            Assert.assertEquals(poolSize, configurationRead.getPoolSize());
        } finally {
            file.delete();
        }
    }

    @Test
    public void testProcess() throws Exception {

        final AtomicInteger eventProcessed = new AtomicInteger();
        IStore resultStore = this.manager.get("resultStore");
        Entity result = new Entity("result");
        result.setId("result");
        result.setValue("eventProcessed", eventProcessed);
        resultStore.create(result);

        IProcessor processor = new PoolProcessor("poolProcessor", null, 10, this.manager, "genericProcessor");
        try {
            processor.start();
            for (int index = 0; index < 10; index++) {
                IEntity entity = new Entity("event").set("delta", index);
                processor.process(entity);
            }
            Thread.sleep(150);
            Assert.assertEquals(45, eventProcessed.get());
        } finally {
            processor.stop();
        }
    }

};