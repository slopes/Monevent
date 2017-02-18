package monevent.common.process.time;

import com.google.common.collect.Lists;
import monevent.common.communication.EntityBusManager;
import monevent.common.communication.IEntityBus;
import monevent.common.model.IEntity;
import monevent.common.model.configuration.factory.FileConfigurationFactory;
import monevent.common.model.query.Query;
import monevent.common.model.query.QueryCriterionType;
import monevent.common.model.time.JobExecution;
import monevent.common.process.*;
import monevent.common.process.store.StoreProcessorConfiguration;
import monevent.common.store.IStore;
import monevent.common.store.StoreException;
import monevent.common.store.StoreFactory;
import monevent.common.store.StoreManager;
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

    @Test
    public void testConfiguration() {
        String name = "scheduledProcessor";
        Query query = new Query();
        query.addCriterion("processor", name, QueryCriterionType.Is);
        String cronExpression = "0/5 * * * * ?";
        String processor = "subProcessor";
        ProcessorConfiguration configurationWrite = new ScheduledProcessorConfiguration(name, query, cronExpression, Lists.newArrayList(processor));
        File file = new File("src/test/resources/config/processors/" + name + ".json");
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


        FileConfigurationFactory factory = new FileConfigurationFactory("src/test/resources/chain");
        factory.start();

        EntityBusManager entityBusManager = new EntityBusManager("entityBusManager");
        entityBusManager.start();

        StoreFactory storeFactory = new StoreFactory("storeFactory", factory);
        storeFactory.start();

        StoreManager storeManager = new StoreManager("storeManager", storeFactory);
        storeManager.start();

        ProcessorFactory processorFactory = new ProcessorFactory(entityBusManager, storeManager, factory);
        processorFactory.start();

        ProcessorManager processorManager = new ProcessorManager("processorManager", processorFactory);
        processorManager.start();


        String name = "scheduledProcessor";
        Query query = new Query();
        query.addCriterion("processor", name, QueryCriterionType.Is);
        String cronExpression = "0/1 * * * * ?";
        ScheduledProcessorConfiguration configuration = new ScheduledProcessorConfiguration();
        configuration.setName("scheduledProcessor");
        configuration.setCronExpression(cronExpression);
        configuration.setProcessors(Lists.newArrayList("storeProcessor"));
        IProcessor processor = configuration.build(entityBusManager, storeManager, processorManager);
        processor.start();
        try {
            Thread.sleep(2000);

            IStore store = storeManager.load("store");
            Assert.assertNotNull(store);
            List<IEntity> executions = store.read(new Query());
            Assert.assertNotNull(executions);
            Assert.assertTrue(executions.size() > 0);
            executions.forEach(e -> Assert.assertTrue(e instanceof JobExecution));

        } finally {
            processor.stop();
            processorManager.stop();
            factory.stop();
        }
    }

    @Test
    public void testScheduledProcessorWithError() throws InterruptedException, StoreException {
        FileConfigurationFactory factory = new FileConfigurationFactory("src/test/resources/chain");
        factory.start();

        EntityBusManager entityBusManager = new EntityBusManager("entityBusManager");
        entityBusManager.start();

        StoreFactory storeFactory = new StoreFactory("storeFactory", factory);
        storeFactory.start();

        StoreManager storeManager = new StoreManager("storeManager", storeFactory);
        storeManager.start();

        ProcessorFactory processorFactory = new ProcessorFactory(entityBusManager, storeManager, factory);
        processorFactory.start();

        ProcessorManager processorManager = new ProcessorManager("processorManager", processorFactory);
        processorManager.start();

        IProcessor storeProcessor = processorManager.load("storeProcessor");

        IEntityBus jobBus = entityBusManager.load("jobBus");
        jobBus.register(storeProcessor);

        String name = "scheduledProcessor";
        Query query = new Query();
        query.addCriterion("processor", name, QueryCriterionType.Is);
        String cronExpression = "0/1 * * * * ?";
        ScheduledProcessorConfiguration configuration = new ScheduledProcessorConfiguration();
        configuration.setName("scheduledProcessor");
        configuration.setCronExpression(cronExpression);
        configuration.setProcessors(Lists.newArrayList("crashProcessor"));
        IProcessor processor = configuration.build(entityBusManager, storeManager, processorManager);
        processor.start();
        try {
            Thread.sleep(2000);

            IStore store = storeManager.load("store");
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
            processorManager.stop();
            factory.stop();
        }
    }

}