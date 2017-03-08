package monevent.common.process.alert;

import com.google.common.collect.Lists;
import monevent.common.communication.EntityBusConfiguration;
import monevent.common.model.Entity;
import monevent.common.model.IEntity;
import monevent.common.model.alert.AlertPriority;
import monevent.common.model.configuration.Configuration;
import monevent.common.model.configuration.factory.memory.MemoryConfigurationFactory;
import monevent.common.model.event.Event;
import monevent.common.model.query.Query;
import monevent.common.model.query.QueryCriterionType;
import monevent.common.process.*;
import monevent.common.process.communication.BusProcessorConfiguration;
import monevent.common.process.metric.MetricProcessorConfiguration;
import monevent.common.process.store.StoreProcessorConfiguration;
import monevent.common.process.time.ScheduledProcessorConfiguration;
import monevent.common.store.IStore;
import monevent.common.store.StoreConfiguration;
import monevent.common.store.memory.MemoryStoreConfiguration;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

/**
 * Created by steph on 06/03/2016.
 */
public class AlertFlowTest extends ProcessorTest {


    @Override
    protected void addConfiguration() {
        MemoryConfigurationFactory<Configuration> configurationFactory = new MemoryConfigurationFactory("entityBusConfigurationFactory");
        configurationFactory.add(new EntityBusConfiguration("alertBus"));
        configurationFactory.add(new EntityBusConfiguration("metricBus"));
        configurationFactory.add(new EntityBusConfiguration("jobBus"));


        configurationFactory.add(new MemoryStoreConfiguration("alertStore"));

         MetricProcessorConfiguration processorConfiguration = new MetricProcessorConfiguration();
        processorConfiguration.setName("metricProcessor");
        processorConfiguration.setValueField("value");
        processorConfiguration.setHighestTrackableValue(100000);
        processorConfiguration.setNumberOfSignificantValueDigits(2);
        processorConfiguration.setMetricBus("metricBus");
        configurationFactory.add(processorConfiguration);

        MatchAlertProcessorConfiguration alertProcessorConfiguration = new MatchAlertProcessorConfiguration();
        alertProcessorConfiguration.setName("alertProcessor");
        Query metricQuery = new Query();
        metricQuery.addCriterion(Entity.type, "metric", QueryCriterionType.Is);
        alertProcessorConfiguration.setQuery(metricQuery);
        alertProcessorConfiguration.setPriority(AlertPriority.Info);
        alertProcessorConfiguration.setUserMessage("Hey, I saw a metric");
        alertProcessorConfiguration.setAlertBus("alertBus");
        configurationFactory.add(alertProcessorConfiguration);

        configurationFactory.add(new StoreProcessorConfiguration("storeProcessor", null, "alertStore"));

        configurationFactory.add(new BusProcessorConfiguration("alertBusProcessor",
                null,
                Lists.newArrayList("storeProcessor"),
                Lists.newArrayList("alertBus")));

        configurationFactory.add(new BusProcessorConfiguration("metricBusProcessor",
                null,
                Lists.newArrayList("alertProcessor"),
                Lists.newArrayList("metricBus")));

        ScheduledProcessorConfiguration scheduledProcessorConfiguration = new ScheduledProcessorConfiguration();
        scheduledProcessorConfiguration.setName("scheduledProcessor");
        scheduledProcessorConfiguration.setCronExpression("0/1 * * * * ?");
        scheduledProcessorConfiguration.setProcessors(Lists.newArrayList("metricProcessor"));
        configurationFactory.add(scheduledProcessorConfiguration);

        this.factory.addFactory(configurationFactory);
    }

    @Before
    public void setUpClass() {
        start();
    }

    @After
    public void tearDownClass() {
        stop();
    }

    @Test
    public void testCompleteFlow() {


        IProcessor metricProcessor = this.manager.get("metricProcessor");

        IProcessor alertBusProcessor = this.manager.get("alertBusProcessor");

        IProcessor metricBusProcessor = this.manager.get("metricBusProcessor");

        IProcessor storeProcessor = this.manager.get("storeProcessor");

        IProcessor scheduledProcessor = this.manager.get("scheduledProcessor");


        try {

            for (int index = 0; index < 100; index++) {
                Event event = new Event("eventType");
                event.set("value", 100);

                metricProcessor.process(event);
            }

            Thread.sleep(1000);

            IStore store = this.manager.get("alertStore");
            Query alertQuery = new Query("alertQuery");
            alertQuery.addCriterion("type", "alert", QueryCriterionType.Is);
            List<IEntity> results = store.read(alertQuery);
            Assert.assertTrue(results.size() > 0);

        } catch (Exception error) {
            Assert.fail(error.getMessage());
        }
    }
}
