package monevent.common.process.combine;

import com.google.common.collect.Lists;
import monevent.common.model.Entity;
import monevent.common.model.IEntity;
import monevent.common.process.*;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Predicate;

import static org.junit.Assert.*;

/**
 * Created by slopes on 15/01/17.
 */
public class SequentialProcessorTest extends ProcessorTest {

    @Test
    public void testConfiguration() {
        String name = "sequentialProcessor";
        int poolSize = 2;
        List<String> processors = Lists.newArrayList("processor1", "processor2", "processor3");
        ProcessorConfiguration configurationWrite = new SequentialProcessorConfiguration(name, null, processors, poolSize);
        File file = new File("src/test/resources/config/processors/" + name + ".json");
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

        Map<String, Predicate<IEntity>> predicates = new ConcurrentHashMap<>();
        predicates.put("genericProcessorR", entity -> AddLetter(entity, "R"));
        predicates.put("genericProcessorO", entity -> AddLetter(entity, "O"));
        predicates.put("genericProcessorC", entity -> AddLetter(entity, "C"));
        predicates.put("genericProcessorK", entity -> AddLetter(entity, "K"));
        predicates.put("genericProcessorS", entity -> AddLetter(entity, "S"));
        IProcessorFactory factory = new GenericProcessorFactory(predicates);
        ProcessorManager processorManager = new ProcessorManager("processorManager", factory);


        IProcessor processor = new SequentialProcessor("poolProcessor", null,processorManager, Lists.newArrayList("genericProcessorR", "genericProcessorO", "genericProcessorC", "genericProcessorK", "genericProcessorS"));
        try {
            factory.start();
            processorManager.start();
            processor.start();

            IEntity entity = new Entity("event", "test").set("chain","Monevent-");
            entity = processor.process(entity);


            Assert.assertEquals("Monevent-ROCKS", entity.getValueAsString("chain"));
        } finally {
            processor.stop();
            processorManager.stop();
            factory.stop();
        }
    }

    private boolean AddLetter(IEntity entity, String letter) {
        Entity chainEntity = (Entity) entity;
        String chain = chainEntity.getValueAsString("chain");
        chainEntity.setValue("chain", chain.concat(letter));
        return true;
    }

}