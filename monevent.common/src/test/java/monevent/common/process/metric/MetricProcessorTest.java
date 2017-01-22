package monevent.common.process.metric;

import monevent.common.model.event.Event;
import monevent.common.model.metric.Metric;
import monevent.common.model.time.Timestamp;
import monevent.common.process.IProcessor;
import monevent.common.process.ProcessorConfiguration;
import monevent.common.process.ProcessorTest;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;

/**
 * Created by steph on 12/03/2016.
 */
public class MetricProcessorTest extends ProcessorTest {

    @Test
    public void testConfiguration() {
        String name = "metricProcessor";
        String valueField = "value";
        long highestTrackableValue = 10000L;
        int numberOfSignificantValueDigits = 2;
        String cronExpression = "0 0 0 0 0 *";
        String publication = "dummyProcessor";
        boolean resetOnPublish = true;
        ProcessorConfiguration configurationWrite = new MetricProcessorConfiguration(name, null,cronExpression,publication, valueField, highestTrackableValue, numberOfSignificantValueDigits,resetOnPublish);
        File file = new File("src/test/resources/config/processors/" + name + ".json");
        try {
            write(file, configurationWrite);
            MetricProcessorConfiguration configurationRead = (MetricProcessorConfiguration) read(file);
            Assert.assertEquals(name, configurationRead.getName());
            Assert.assertEquals(valueField, configurationRead.getValueField());
            Assert.assertEquals(highestTrackableValue, configurationRead.getHighestTrackableValue());
            Assert.assertEquals(numberOfSignificantValueDigits, configurationRead.getNumberOfSignificantValueDigits());
            Assert.assertEquals(publication, configurationRead.getPublication());
            Assert.assertEquals(resetOnPublish, configurationRead.isResetOnPublish());
            Assert.assertEquals(cronExpression, configurationRead.getCronExpression());
        } finally {
            file.delete();
        }
    }

    @Test
    public void testProcess() throws Exception {
        IProcessor processor = new MetricProcessor("metricProcessor", null,null,null,null, "value", 10000, 2,false);
        try {
            processor.start();
            for (int index = 0; index < 100; index++) {
                processor.process(new Event("Test").set("value", index));
            }
            Metric metric = (Metric) processor.process(new Timestamp());
            Assert.assertEquals(0, metric.getMinimum(),2);
            Assert.assertEquals(99, metric.getMaximum(),2);
            Assert.assertEquals(50, metric.getMean(),2);
            Assert.assertEquals(99, metric.getLast(),2);

        } finally {
            processor.stop();
        }
    }
}