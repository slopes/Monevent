package monevent.common.process.time;

import monevent.common.model.query.Query;
import monevent.common.model.query.QueryCriterionType;
import monevent.common.process.ProcessorConfiguration;
import monevent.common.process.ProcessorTest;
import monevent.common.process.store.StoreProcessorConfiguration;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

/**
 * Created by steph on 12/03/2016.
 */
public class ScheduledProcessorTest extends ProcessorTest {
    @Before
    public void setUpClass() {
        start();
    }

    @After
    public void tearDownClass() {
        stop();
    }

    @Test
    public void testConfiguration() {
        String name = "scheduledProcessor";
        Query query = new Query();
        query.addCriterion("processor", name, QueryCriterionType.Is);
        String cronExpression ="0/5 * * * * ";
        String processor = "subProcessor";
        ProcessorConfiguration configurationWrite = new StoreProcessorConfiguration(name, query,"memoryStore");
        File file = new File("src/test/resources/config/processors/" + name + ".json");
        try {
            write(file, configurationWrite);
            StoreProcessorConfiguration configurationRead = (StoreProcessorConfiguration) read(file);
            Assert.assertEquals(name, configurationRead.getName());
            Assert.assertEquals(name, ((Query) configurationRead.getQuery()).getCriteria().get(0).getValue());

        } finally {
            file.delete();
        }
    }

}