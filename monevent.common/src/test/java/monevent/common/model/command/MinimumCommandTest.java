package monevent.common.model.command;

import monevent.common.model.Entity;
import monevent.common.model.IEntity;
import org.joda.time.DateTime;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by slopes on 05/02/17.
 */
public class MinimumCommandTest {
    @Test
    public void testParseWithAllToken() throws Exception {
        String commandLine = "MINIMUM a b c";
        Command command = CommandParser.parse(commandLine);
        DateTime now = DateTime.now();
        DateTime yesterday = DateTime.now().minusDays(1);
        Entity entity1 = new Entity("source").set("a", now);
        Entity entity2 = new Entity("destination").set("b", yesterday);
        IEntity result = command.execute(entity1, entity2);
        assertNotNull(result);
        assertEquals(yesterday, result.getValueAsDateTime("c"));
    }

    @Test
    public void testParseWithFiledToCopyOnly() throws Exception {
        String commandLine = "MINIMUM a b c";
        Command command = CommandParser.parse(commandLine);
        Entity entity1 = new Entity("source").set("a", -2);
        Entity entity2 = new Entity("destination").set("b", 1);
        IEntity result = command.execute(entity1, entity2);
        assertNotNull(result);
        assertEquals(-2, (int) result.getValueAsInteger("c"));
    }
}