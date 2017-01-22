package monevent.common.process.combine;

import monevent.common.model.Entity;
import monevent.common.model.IEntity;
import monevent.common.process.*;
import monevent.common.process.metric.MetricProcessorConfiguration;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;

import static org.junit.Assert.*;

/**
 * Created by slopes on 15/01/17.
 */
public class PoolProcessorTest extends ProcessorTest {


    @Test
    public void testConfiguration() {
        String name = "poolProcessor";
        String processorName = "delayProcessor";
        int poolSize = 2;
        ProcessorConfiguration configurationWrite = new PoolProcessorConfiguration(name, null, processorName, poolSize);
        File file = new File("src/test/resources/config/processors/" + name + ".json");
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
        Predicate<IEntity> predicate = entity -> {
            try {
                int delta = entity.getValueAsInteger("delta");
                Thread.sleep(100);
                eventProcessed.addAndGet(delta);
            } catch (InterruptedException error) {
                fail(error.getMessage());
            }
            return true;
        };

        Map<String, Predicate<IEntity>> predicates = new ConcurrentHashMap<>();
        predicates.put("genericProcessor", predicate);
        IProcessorFactory factory = new GenericProcessorFactory(predicates);
        ProcessorManager processorManager = new ProcessorManager("processorManager", factory);
        IProcessor processor = new PoolProcessor("poolProcessor", null, 10, processorManager, "genericProcessor");
        try {
            factory.start();
            processorManager.start();
            processor.start();
            for (int index = 0; index < 10; index++) {
                IEntity entity = new Entity("event", "test").set("delta", index);
                processor.process(entity);
            }
            Thread.sleep(150);
            Assert.assertEquals(45, eventProcessed.get());
        } finally {
            processor.stop();
            processorManager.stop();
            factory.stop();
        }
    }

};