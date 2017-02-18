package monevent.common.process.command;

import com.google.common.collect.Lists;
import monevent.common.model.Entity;
import monevent.common.model.IEntity;
import monevent.common.model.event.Event;
import monevent.common.model.query.IQuery;
import monevent.common.model.query.Query;
import monevent.common.model.query.QueryCriterionType;
import monevent.common.process.IProcessor;
import monevent.common.process.ProcessorConfiguration;
import monevent.common.process.ProcessorTest;
import org.junit.Test;

import java.io.File;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by slopes on 07/02/17.
 */
public class CommandProcessorTest extends ProcessorTest {


    @Test
    public void testConfiguration() {
        String name = "commandProcessor";
        List<String> commands = Lists.newArrayList();
        commands.add("MINIMUM @timestart start");
        commands.add("MAXIMUM @timestart stop");
        commands.add("COPY a value");
        IQuery query = new Query().addCriterion(Entity.type, "Test", QueryCriterionType.Is);
        ProcessorConfiguration configurationWrite = new CommandProcessorConfiguration(name, query, commands);
        File file = new File("src/test/resources/config/processors/" + name + ".json");
        try {
            write(file, configurationWrite);
            CommandProcessorConfiguration configurationRead = (CommandProcessorConfiguration) read(file);
            assertEquals(name, configurationRead.getName());
            assertEquals(commands.size(), configurationRead.getCommands().size());
        } finally {
            file.delete();
        }
    }

    @Test
    public void testProcess() throws Exception {

        String name = "commandProcessor";
        List<String> commands = Lists.newArrayList();
        commands.add("MAXIMUM a max max");
        commands.add("MINIMUM b min min");
        commands.add("COMPUTE SUBSTRACT max min d");
        ProcessorConfiguration configuration = new CommandProcessorConfiguration(name, null, commands);
        IProcessor processor = configuration.build(null, null, null);

        try {
            processor.start();

            IEntity event = new Event("Test");
            for (int index = 0; index < 100; index++) {
                event.setValue("a", index);
                event.setValue("b", index);
                event = processor.process(event);
            }

            assertEquals(0, (int) event.getValueAsInteger("min"));
            assertEquals(99, (int) event.getValueAsInteger("max"));
            assertEquals(99, (int) event.getValueAsInteger("d"));

        } finally {
            processor.stop();
        }

    }
}