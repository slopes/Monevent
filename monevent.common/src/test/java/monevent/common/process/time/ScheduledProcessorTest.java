package monevent.common.process.time;

import com.google.common.collect.Lists;
import monevent.common.communication.EntityBusConfiguration;
import monevent.common.communication.IEntityBus;
import monevent.common.model.IEntity;
import monevent.common.model.configuration.Configuration;
import monevent.common.model.configuration.factory.file.FileConfigurationFactory;
import monevent.common.model.configuration.factory.memory.MemoryConfigurationFactory;
import monevent.common.model.query.Query;
import monevent.common.model.query.QueryCriterionType;
import monevent.common.model.time.JobExecution;
import monevent.common.process.*;
import monevent.common.store.IStore;
import monevent.common.store.StoreConfiguration;
import monevent.common.store.StoreException;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.List;

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

    @Override
    protected void addConfiguration() {
        this.factory.addFactory( new FileConfigurationFactory<>("configurationFileFactory", "src/test/resources/chain/"));
    }

    @Test
    public void testConfiguration() {
        String name = "scheduledProcessor";
        Query query = new Query();
        query.addCriterion("processor", name, QueryCriterionType.Is);
        String cronExpression = "0/5 * * * * ?";
        String processor = "subProcessor";
        ProcessorConfiguration configurationWrite = new ScheduledProcessorConfiguration(name, query, cronExpression, Lists.newArrayList(processor));
        File file = new File("src/test/resources/config/processor/" + name + ".json");
        try {
            write(file, configurationWrite);
            ScheduledProcessorConfiguration configurationRead = (ScheduledProcessorConfiguration) read(file);
            Assert.assertEquals(name, configurationRead.getName());
            Assert.assertEquals(name, configurationRead.getQuery().getCriteria().get(0).getValue());

        } finally {
            file.delete();
        }
    }

    @Test
    public void testScheduledProcessorWithNoError() throws InterruptedException, StoreException {

        String name = "scheduledProcessor";
        Query query = new Query();
        query.addCriterion("processor", name, QueryCriterionType.Is);
        String cronExpression = "0/1 * * * * ?";
        ScheduledProcessorConfiguration configuration = new ScheduledProcessorConfiguration();
        configuration.setName("scheduledProcessor");
        configuration.setCronExpression(cronExpression);
        configuration.setProcessors(Lists.newArrayList("storeProcessor"));
        IProcessor processor = configuration.build(this.manager);
        processor.start();
        try {
            Thread.sleep(2000);

            IStore store = this.manager.get("store");
            Assert.assertNotNull(store);
            List<IEntity> executions = store.read(new Query());
            Assert.assertNotNull(executions);
            Assert.assertTrue(executions.size() > 0);
            executions.forEach(e -> Assert.assertTrue(e instanceof JobExecution));

        } finally {
            processor.stop();
        }
    }

    @Test
    public void testScheduledProcessorWithError() throws InterruptedException, StoreException {
        IProcessor storeProcessor = this.manager.get("storeProcessor");

        IEntityBus jobBus = this.manager.get("jobBus");
        jobBus.register(storeProcessor);

        String name = "scheduledProcessor";
        Query query = new Query();
        query.addCriterion("processor", name, QueryCriterionType.Is);
        String cronExpression = "0/1 * * * * ?";
        ScheduledProcessorConfiguration configuration = new ScheduledProcessorConfiguration();
        configuration.setName("scheduledProcessor");
        configuration.setCronExpression(cronExpression);
        configuration.setProcessors(Lists.newArrayList("crashProcessor"));
        IProcessor processor = configuration.build(this.manager);
        processor.start();
        try {
            Thread.sleep(2000);

            IStore store = this.manager.get("store");
            Assert.assertNotNull(store);
            List<IEntity> executions = store.read(new Query());
            Assert.assertNotNull(executions);
            Assert.assertTrue(executions.size() > 0);
            executions.forEach(e -> {
                Assert.assertTrue(e instanceof JobExecution);
                Assert.assertEquals("KO",e.getValueAsString(JobExecution.status));
                Assert.assertEquals("crashProcessor",e.getValueAsString(JobExecution.processor));
            });

        } finally {
            processor.stop();
        }
    }

}