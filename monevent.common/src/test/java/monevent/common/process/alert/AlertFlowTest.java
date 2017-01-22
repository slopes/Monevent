package monevent.common.process.alert;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import monevent.common.communication.EntityBusManager;
import monevent.common.communication.IEntityBus;
import monevent.common.communication.IEntityBusFactory;
import monevent.common.communication.local.LocalEntityBus;
import monevent.common.managers.ManageableBase;
import monevent.common.model.Entity;
import monevent.common.model.IEntity;
import monevent.common.model.alert.AlertPriority;
import monevent.common.model.event.Event;
import monevent.common.model.query.Query;
import monevent.common.model.query.QueryCriterionType;
import monevent.common.process.*;
import monevent.common.process.alert.MatchAlertProcessorConfiguration;
import monevent.common.process.combine.SequentialProcessor;
import monevent.common.process.combine.SequentialProcessorConfiguration;
import monevent.common.process.communication.BusProcessor;
import monevent.common.process.communication.BusProcessorConfiguration;
import monevent.common.process.metric.MetricProcessorConfiguration;
import monevent.common.process.store.StoreProcessorConfiguration;
import monevent.common.process.time.ScheduledProcessorConfiguration;
import monevent.common.store.IStore;
import monevent.common.store.IStoreFactory;
import monevent.common.store.StoreManager;
import monevent.common.store.memory.MemoryStore;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

/**
 * Created by steph on 06/03/2016.
 */
public class AlertFlowTest extends ProcessorTest {

    private class AlertFlowProcessorConfigurationFactory extends ManageableBase implements IProcessorConfigurationFactory {

        public AlertFlowProcessorConfigurationFactory() {
            super("alertFlowProcessorConfigurationFactory");
        }

        @Override
        public ProcessorConfiguration buildProcessor(String processorName) {

            if (processorName.equals("metricProcessor")) {
                MetricProcessorConfiguration processorConfiguration = new MetricProcessorConfiguration();
                processorConfiguration.setName("metricProcessor");
                processorConfiguration.setValueField("value");
                processorConfiguration.setHighestTrackableValue(100000);
                processorConfiguration.setNumberOfSignificantValueDigits(2);
                processorConfiguration.setMetricBus("metricBus");
                return processorConfiguration;
            }

            if (processorName.equals("alertProcessor")) {
                MatchAlertProcessorConfiguration alertProcessorConfiguration = new MatchAlertProcessorConfiguration();
                alertProcessorConfiguration.setName("alertProcessor");
                Query metricQuery = new Query();
                metricQuery.addCriterion(Entity.type, "metric", QueryCriterionType.Is);
                alertProcessorConfiguration.setQuery(metricQuery);
                alertProcessorConfiguration.setPriority(AlertPriority.Info);
                alertProcessorConfiguration.setUserMessage("Hey, I saw a metric");
                alertProcessorConfiguration.setAlertBus("alertBus");
                return alertProcessorConfiguration;
            }

            if (processorName.equals("storeProcessor")) {
                return new StoreProcessorConfiguration("storeProcessor",
                        null,
                        "alertStore");

            }

            if (processorName.equals("alertBusProcessor")) {
                return new BusProcessorConfiguration("busProcessor",
                        null,
                        Lists.newArrayList("storeProcessor"),
                        Lists.newArrayList("alertBus"));

            }

            if (processorName.equals("metricBusProcessor")) {
                return new BusProcessorConfiguration("metricBusProcessor",
                        null,
                        Lists.newArrayList("alertProcessor"),
                        Lists.newArrayList("metricBus"));

            }

            if (processorName.equals("scheduledProcessor")) {

                ScheduledProcessorConfiguration processorConfiguration = new ScheduledProcessorConfiguration();
                processorConfiguration.setName("scheduledProcessor");
                processorConfiguration.setCronExpression("*/1 * * * * ?");
                processorConfiguration.setProcessors(Lists.newArrayList("metricProcessor"));
                return processorConfiguration;

            }


            return null;
        }

        @Override
        protected void doStart() {

        }

        @Override
        protected void doStop() {

        }
    }

    private class AlertFlowStoreFactory extends ManageableBase implements IStoreFactory {

        protected AlertFlowStoreFactory() {
            super("alertFlowStoreFactory");
        }

        @Override
        public IStore build(String storeName) {
            return new MemoryStore(storeName);
        }

        @Override
        protected void doStart() {

        }

        @Override
        protected void doStop() {

        }
    }

    private class AlertFlowProcessorFactory extends ProcessorFactory {

        public AlertFlowProcessorFactory(EntityBusManager entityBusManager, StoreManager storeManager) {
            super(entityBusManager, storeManager, new AlertFlowProcessorConfigurationFactory());
        }
    }

    @Test
    public void testCompleteFlow() {

        EntityBusManager entityBusManager = new EntityBusManager("entityBusManager");
        entityBusManager.start();

        StoreManager storeManager = new StoreManager("storeManager", new AlertFlowStoreFactory());
        storeManager.start();

        ProcessorManager processorManager = new ProcessorManager("processorManager", new AlertFlowProcessorFactory(entityBusManager, storeManager));
        processorManager.start();

        IProcessor metricProcessor = processorManager.load("metricProcessor");

        IProcessor alertBusProcessor = processorManager.load("alertBusProcessor");

        IProcessor metricBusProcessor = processorManager.load("metricBusProcessor");

        IProcessor scheduledProcessor = processorManager.load("scheduledProcessor");


        try {

            for (int index = 0; index < 100; index++) {
                Event event = new Event("eventName", "eventType");
                event.set("value", 100);

                metricProcessor.process(event);
            }

            Thread.sleep(1000);

            IStore store = storeManager.load("alertStore");
            Query alertQuery = new Query("alertQuery");
            alertQuery.addCriterion("type", "alert", QueryCriterionType.Is);
            List<IEntity> results = store.read(alertQuery);
            Assert.assertTrue(results.size()>0);

        } catch (Exception error) {
            Assert.fail(error.getMessage());
        } finally {
            processorManager.stop();
            storeManager.stop();
            ;
            entityBusManager.stop();
        }


    }
}
