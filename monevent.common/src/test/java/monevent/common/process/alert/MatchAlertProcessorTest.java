package monevent.common.process.alert;

import monevent.common.model.IEntity;
import monevent.common.model.alert.AlertPriority;
import monevent.common.model.event.Event;
import monevent.common.model.query.Query;
import monevent.common.model.query.QueryCriterionType;
import monevent.common.process.IProcessor;
import monevent.common.process.ProcessorConfiguration;
import monevent.common.process.ProcessorTest;
import monevent.common.store.IStore;
import monevent.common.store.memory.MemoryStore;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by Stephane on 21/11/2015.
 */
public class MatchAlertProcessorTest extends ProcessorTest {

    @Test
    public void testConfiguration() {
        String name = "matchAlertProcessor";
        Query query = new Query();
        query.addCriterion("processor", name, QueryCriterionType.Is);
        String userMessage = "An event as occurred";
        AlertPriority priority = AlertPriority.Medium;
        ProcessorConfiguration configurationWrite = new MatchAlertProcessorConfiguration(name, null, userMessage, 0, TimeUnit.NANOSECONDS, null,priority);
        File file = new File("src/test/resources/config/processor/" + name + ".json");
        try {
            write(file, configurationWrite);
            MatchAlertProcessorConfiguration configurationRead = (MatchAlertProcessorConfiguration) read(file);
            Assert.assertEquals(name, configurationRead.getName());
            Assert.assertEquals(userMessage, configurationRead.getUserMessage());
            Assert.assertEquals(priority, configurationRead.getPriority());
        } finally {
            file.delete();
        }
    }

    @Test
    public void testProcess() throws Exception {

        Query query = new Query();
        query.addCriterion("value", 5, QueryCriterionType.GreaterThan);
        String userMessage = "An event as occurred";
        AlertPriority priority = AlertPriority.Medium;
        IProcessor matchAlertProcessor = new MatchAlertProcessor("matchAlertProcessor", query,null,null, userMessage, 0, TimeUnit.NANOSECONDS, priority);
        IStore store = new MemoryStore("memoryStore");
        try {
            matchAlertProcessor.start();
            store.start();

            for (int index = 0; index < 10; index++) {
                store.create(matchAlertProcessor.process(new Event("Test").set("value", index)));
            }

            Query alertQuery = new Query("alertQuery");
            alertQuery.addCriterion("type", "alert", QueryCriterionType.Is);

            List<IEntity> results = store.read(alertQuery);
            Assert.assertEquals(4, results.size());

        } finally {
            store.stop();
            matchAlertProcessor.stop();
        }
    }
}