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
public class CopyCommandTest {
    @Test
    public void testParseWithAllTokenWhenSourceIsMaximum() throws Exception {
        String commandLine = "MAXIMUM a b c";
        Command command = CommandParser.parse(commandLine);
        DateTime now = DateTime.now();
        DateTime yesterday = DateTime.now().minusDays(1);
        Entity entity1 = new Entity("source").set("a", now);
        Entity entity2 = new Entity("destination").set("b", yesterday);
        IEntity result = command.execute(entity1, entity2);
        assertNotNull(result);
        assertEquals(now, result.getValueAsDateTime("c"));
    }

    @Test
    public void testParseWithAllTokenWhenDestinationIsMaximum() throws Exception {
        String commandLine = "MAXIMUM a b c";
        Command command = CommandParser.parse(commandLine);
        DateTime now = DateTime.now();
        DateTime yesterday = DateTime.now().minusDays(1);
        Entity entity1 = new Entity("source").set("a", yesterday);
        Entity entity2 = new Entity("destination").set("b", now);
        IEntity result = command.execute(entity1, entity2);
        assertNotNull(result);
        assertEquals(now, result.getValueAsDateTime("c"));
    }


}