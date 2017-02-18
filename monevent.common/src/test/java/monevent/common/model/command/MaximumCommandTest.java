package monevent.common.model.command;

import monevent.common.model.Entity;
import monevent.common.model.IEntity;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by slopes on 05/02/17.
 */
public class MaximumCommandTest {
    @Test
    public void testParseWithAllToken() throws Exception {
        String commandLine = "MAXIMUM a b c";
        Command command = CommandParser.parse(commandLine);
        Entity entity1 = new Entity("source").set("a", 3.25);
        Entity entity2 = new Entity("destination").set("b", 1.0/3.0);
        IEntity result = command.execute(entity1, entity2);
        assertNotNull(result);
        assertEquals(3.25, result.getValueAsDouble("c"),0.0);
    }

    @Test
    public void testParseWithFiledToCopyOnly() throws Exception {
        String commandLine = "MAXIMUM a b c";
        Command command = CommandParser.parse(commandLine);
        Entity entity1 = new Entity("source").set("a", "BBBBBBBB");
        Entity entity2 = new Entity("destination").set("b", "AAAAAAAAAA");
        IEntity result = command.execute(entity1, entity2);
        assertNotNull(result);
        assertEquals("BBBBBBBB", result.getValueAsString("c"));
    }
}