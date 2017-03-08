package monevent.common.process.store;

import monevent.common.model.IEntity;
import monevent.common.model.event.Event;
import monevent.common.model.query.Query;
import monevent.common.model.query.QueryCriterionType;
import monevent.common.process.IProcessor;
import monevent.common.process.ProcessorConfiguration;
import monevent.common.process.ProcessorTest;
import monevent.common.store.memory.MemoryStore;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.util.List;

/**
 * Created by steph on 12/03/2016.
 */
public class StoreProcessorTest extends ProcessorTest {

    @Test
    public void testConfiguration() {
        String name = "storeProcessor";
        String storeName = "memoryStore";
        ProcessorConfiguration configurationWrite = new StoreProcessorConfiguration(name, null, storeName);
        File file = new File("src/test/resources/config/processor/" + name + ".json");
        try {
            write(file, configurationWrite);
            StoreProcessorConfiguration configurationRead = (StoreProcessorConfiguration) read(file);
            Assert.assertEquals(name, configurationRead.getName());
            Assert.assertEquals(storeName, configurationRead.getStoreName());
        } finally {
            file.delete();
        }
    }

    @Test
    public void testProcess() throws Exception {
        MemoryStore store = new MemoryStore("EntityStore");
        IProcessor processor = new StoreProcessor("storeProcessor",null, store);
        try {
            store.start();
            processor.start();
            for (int index = 0; index < 100; index++) {
                processor.process(new Event("Test").set("value", index));
            }
            Query alertQuery = new Query("alertQuery");
            alertQuery.addCriterion("value", 12, QueryCriterionType.LesserThan);

            List<IEntity> results = store.read(alertQuery);
            Assert.assertEquals(12, results.size());

        } finally {
            processor.stop();
            store.start();
        }
    }


}