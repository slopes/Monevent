package monevent.common.model.command;

import monevent.common.model.Entity;
import monevent.common.model.IEntity;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by slopes on 05/02/17.
 */
public class MetricCommandTest {

    @Test
    public void testParseWithAllToken() throws Exception {
        String commandLine = "METRIC a m";
        Command command = CommandParser.parse(commandLine);
        IEntity result = new Entity("destination");
        for (int index = 0; index < 10; index++) {
            Entity entity1 = new Entity("source").set("a", index);
            result = command.execute(entity1, result);
        }

        assertNotNull(result.getValue("m"));
        assertEquals(4.5, result.getValueAsDouble("m.mean"), 0.0);
        assertEquals(45, result.getValueAsDouble("m.sum"), 0.0);
    }

}