package monevent.common.process.combine;

import com.google.common.collect.Lists;
import monevent.common.communication.EntityBusConfiguration;
import monevent.common.model.Entity;
import monevent.common.model.IEntity;
import monevent.common.model.configuration.factory.memory.MemoryConfigurationFactory;
import monevent.common.process.*;
import monevent.common.store.StoreConfiguration;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.List;

import static org.junit.Assert.fail;

/**
 * Created by slopes on 15/01/17.
 */
public class SequentialProcessorTest extends ProcessorTest {

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

        this.factory.addFactory(new MemoryConfigurationFactory<EntityBusConfiguration>("entityBusConfigurationFactory"));
        this.factory.addFactory(new MemoryConfigurationFactory<StoreConfiguration>("storeConfigurationFactory"));
        MemoryConfigurationFactory<ProcessorConfiguration> processorConfigurationFactory = new MemoryConfigurationFactory<ProcessorConfiguration>("processorConfigurationFactory");
        processorConfigurationFactory.add(new GenericProcessorConfiguration("genericProcessorR", entity -> AddLetter(entity, "R")) );
        processorConfigurationFactory.add(new GenericProcessorConfiguration("genericProcessorO", entity -> AddLetter(entity, "O")));
        processorConfigurationFactory.add(new GenericProcessorConfiguration("genericProcessorC", entity -> AddLetter(entity, "C")));
        processorConfigurationFactory.add(new GenericProcessorConfiguration("genericProcessorK", entity -> AddLetter(entity, "K")));
        processorConfigurationFactory.add(new GenericProcessorConfiguration("genericProcessorS", entity -> AddLetter(entity, "S")) );
        this.factory.addFactory(processorConfigurationFactory);
    }

    @Test
    public void testConfiguration() {
        String name = "sequentialProcessor";
        int poolSize = 2;
        List<String> processors = Lists.newArrayList("processor1", "processor2", "processor3");
        ProcessorConfiguration configurationWrite = new SequentialProcessorConfiguration(name, null, processors, poolSize);
        File file = new File("src/test/resources/config/processor/" + name + ".json");
        try {
            write(file, configurationWrite);
            SequentialProcessorConfiguration configurationRead = (SequentialProcessorConfiguration) read(file);
            Assert.assertEquals(name, configurationRead.getName());
            Assert.assertArrayEquals(processors.toArray(), configurationRead.getProcessors().toArray());
            Assert.assertEquals(poolSize, configurationRead.getPoolSize());
        } finally {
            file.delete();
        }
    }

    @Test
    public void doProcess() throws Exception {


        IProcessor processor = new SequentialProcessor("poolProcessor", null, this.manager, Lists.newArrayList("genericProcessorR", "genericProcessorO", "genericProcessorC", "genericProcessorK", "genericProcessorS"));
        try {
            processor.start();

            IEntity entity = new Entity("event").set("chain", "Monevent-");
            entity = processor.process(entity);

            Assert.assertEquals("Monevent-ROCKS", entity.getValueAsString("chain"));
        } finally {
            processor.stop();
        }
    }

    private boolean AddLetter(IEntity entity, String letter) {
        Entity chainEntity = (Entity) entity;
        String chain = chainEntity.getValueAsString("chain");
        chainEntity.setValue("chain", chain.concat(letter));
        return true;
    }

}