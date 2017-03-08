package monevent.common.process.alert;

import com.google.common.collect.Lists;
import monevent.common.model.IEntity;
import monevent.common.model.alert.*;
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
import java.util.concurrent.TimeUnit;

/**
 * Created by Stephane on 31/12/2015.
 */
public class ThresholdAlertProcessorTest extends ProcessorTest {

    @Test
    public void testConfiguration() {
        String name = "thresholdAlertProcessor";
        double fatalThreshold = 110.0;
        double criticalThreshold = 100.0;
        double mediumThreshold = 90.0;
        double lowThreshold = 90.0;
        double infoThreshold = 70.0;
        String valueField = "value";
        ProcessorConfiguration configurationWrite = new ThresholdAlertProcessorConfiguration(name, null, null, 0, TimeUnit.NANOSECONDS, null, fatalThreshold, criticalThreshold, mediumThreshold, lowThreshold, infoThreshold, valueField);
        File file = new File("src/test/resources/config/processor/" + name + ".json");
        try {
            write(file, configurationWrite);
            ThresholdAlertProcessorConfiguration configurationRead = (ThresholdAlertProcessorConfiguration) read(file);
            Assert.assertEquals(name, configurationRead.getName());
            Assert.assertEquals(fatalThreshold, configurationRead.getFatalThreshold(), 0.0);
            Assert.assertEquals(criticalThreshold, configurationRead.getCriticalThreshold(), 0.0);
            Assert.assertEquals(mediumThreshold, configurationRead.getMediumThreshold(), 0.0);
            Assert.assertEquals(lowThreshold, configurationRead.getLowThreshold(), 0.0);
            Assert.assertEquals(infoThreshold, configurationRead.getInfoThreshold(), 0.0);
            Assert.assertEquals(valueField, configurationRead.getValueField());

        } finally {
            file.delete();
        }
    }

    @Test
    public void testProcessWithNoStrategy() throws Exception {

        IProcessor processor = new ThresholdAlertProcessor("thresholdAlertProcessor", null, null, null, null, 0, TimeUnit.NANOSECONDS, 110.0, 100, 90.0, 80.0, 70.0, "value");
        MemoryStore store = new MemoryStore("EntityStore");

        try {
            store.start();
            processor.start();
            store.create(processor.process(new Event("Test").set("value", 115.0)));
            store.create(processor.process(new Event("Test").set("value", 105.0)));
            store.create(processor.process(new Event("Test").set("value", 95.0)));
            store.create(processor.process(new Event("Test").set("value", 85.0)));
            store.create(processor.process(new Event("Test").set("value", 75.0)));
            store.create(processor.process(new Event("Test").set("value", 65.0)));

            Query alertQuery = new Query("eventQuery");
            alertQuery.addCriterion("type", "alert", QueryCriterionType.Is);

            List<IEntity> results = store.read(alertQuery);
            Assert.assertEquals(5, results.size());

        } finally {
            processor.stop();
            store.stop();
        }
    }

    @Test
    public void testProcessWithFirstStrategy() throws Exception {

        IProcessor processor = new ThresholdAlertProcessor("thresholdAlertProcessor", null, null, null, null, 0, TimeUnit.NANOSECONDS, 110.0, 100, 90.0, 80.0, 70.0, "value");
        List<Alert> alerts = Lists.newArrayList();

        IAlertStrategy strategy = new FirstAlertStrategy();
        strategy.start();
        try {

            processor.start();
            alerts.add((Alert) processor.process(new Event("Test").set("value", 115.0)));
            Thread.sleep(50);
            alerts.add((Alert) processor.process(new Event("Test").set("value", 105.0)));
            Thread.sleep(50);
            alerts.add((Alert) processor.process(new Event("Test").set("value", 95.0)));
            Thread.sleep(50);
            alerts.add((Alert) processor.process(new Event("Test").set("value", 85.0)));
            Thread.sleep(50);
            alerts.add((Alert) processor.process(new Event("Test").set("value", 75.0)));
            Thread.sleep(50);
            alerts.add((Alert) processor.process(new Event("Test").set("value", 65.0)));
            Thread.sleep(50);

            Alert result = strategy.analyze(alerts);
            Assert.assertEquals(AlertPriority.Fatal, result.getPriority());

        } finally {
            processor.stop();
            strategy.stop();
        }
    }

    @Test
    public void testProcessWithLastStrategy() throws Exception {

        IProcessor processor = new ThresholdAlertProcessor("thresholdAlertProcessor", null, null, null, null, 0, TimeUnit.NANOSECONDS, 110.0, 100, 90.0, 80.0, 70.0, "value");
        List<Alert> alerts = Lists.newArrayList();

        IAlertStrategy strategy = new LastAlertStrategy();
        strategy.start();
        try {

            processor.start();
            alerts.add((Alert) processor.process(new Event("Test").set("value", 115.0)));
            Thread.sleep(50);
            alerts.add((Alert) processor.process(new Event("Test").set("value", 105.0)));
            Thread.sleep(50);
            alerts.add((Alert) processor.process(new Event("Test").set("value", 95.0)));
            Thread.sleep(50);
            alerts.add((Alert) processor.process(new Event("Test").set("value", 85.0)));
            Thread.sleep(50);
            alerts.add((Alert) processor.process(new Event("Test").set("value", 75.0)));
            Thread.sleep(50);
            alerts.add((Alert) processor.process(new Event("Test").set("value", 65.0)));
            Thread.sleep(50);

            Alert result = strategy.analyze(alerts);
            Assert.assertEquals(AlertPriority.Info, result.getPriority());

        } finally {
            processor.stop();
            strategy.stop();
        }
    }


    @Test
    public void testProcessWithLeastStrategy() throws Exception {

        IProcessor processor = new ThresholdAlertProcessor("thresholdAlertProcessor", null, null, null, null, 0, TimeUnit.NANOSECONDS, 110.0, 100, 90.0, 80.0, 70.0, "value");
        List<Alert> alerts = Lists.newArrayList();

        IAlertStrategy strategy = new LeastAlertStrategy();
        strategy.start();
        try {

            processor.start();
            alerts.add((Alert) processor.process(new Event("Test").set("value", 115.0)));
            Thread.sleep(50);
            alerts.add((Alert) processor.process(new Event("Test").set("value", 105.0)));
            Thread.sleep(50);
            alerts.add((Alert) processor.process(new Event("Test").set("value", 95.0)));
            Thread.sleep(50);
            alerts.add((Alert) processor.process(new Event("Test").set("value", 85.0)));
            Thread.sleep(50);
            alerts.add((Alert) processor.process(new Event("Test").set("value", 75.0)));
            Thread.sleep(50);
            alerts.add((Alert) processor.process(new Event("Test").set("value", 65.0)));
            Thread.sleep(50);

            Alert result = strategy.analyze(alerts);
            Assert.assertEquals(AlertPriority.Info, result.getPriority());

        } finally {
            processor.stop();
            strategy.stop();
        }
    }

    @Test
    public void testProcessWithWorstStrategy() throws Exception {

        IProcessor processor = new ThresholdAlertProcessor("thresholdAlertProcessor", null, null, null, null, 0, TimeUnit.NANOSECONDS, 110.0, 100, 90.0, 80.0, 70.0, "value");
        List<Alert> alerts = Lists.newArrayList();

        IAlertStrategy strategy = new WorstAlertStrategy();
        strategy.start();
        try {

            processor.start();
            alerts.add((Alert) processor.process(new Event("Test").set("value", 115.0)));
            Thread.sleep(50);
            alerts.add((Alert) processor.process(new Event("Test").set("value", 105.0)));
            Thread.sleep(50);
            alerts.add((Alert) processor.process(new Event("Test").set("value", 95.0)));
            Thread.sleep(50);
            alerts.add((Alert) processor.process(new Event("Test").set("value", 85.0)));
            Thread.sleep(50);
            alerts.add((Alert) processor.process(new Event("Test").set("value", 75.0)));
            Thread.sleep(50);
            alerts.add((Alert) processor.process(new Event("Test").set("value", 65.0)));
            Thread.sleep(50);

            Alert result = strategy.analyze(alerts);
            Assert.assertEquals(AlertPriority.Fatal, result.getPriority());

        } finally {
            processor.stop();
            strategy.stop();
        }
    }
}