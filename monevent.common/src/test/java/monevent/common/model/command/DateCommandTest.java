package monevent.common.model.command;

import monevent.common.model.Entity;
import monevent.common.model.IEntity;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by slopes on 05/02/17.
 */
public class DateCommandTest {


    @Test
    public void testComputeSubstract() throws Exception {
        String commandLine = "DATE SUBSTRACT a b c";
        DateTime now = DateTime.now();
        DateTime yesterday = now.minusDays(1);
        Command command = CommandParser.parse(commandLine);
        Entity entity1 = new Entity("source").set("a", now);
        Entity entity2 = new Entity("destination").set("b", yesterday);
        IEntity result = command.execute(entity1, entity2);
        assertNotNull(result);
        assertEquals(1000*24*3600, (long) result.getValueAsLong("c"));
    }

    @Test
    public void testComputeFormat() throws Exception {
        String commandLine = "DATE FORMAT a YYYY:MM:dd c";
        Command command = CommandParser.parse(commandLine);
        DateTime now = DateTime.now();
        Entity entity1 = new Entity("source").set("a", now);
        Entity entity2 = new Entity("destination");
        IEntity result = command.execute(entity1, entity2);
        assertNotNull(result);
        assertEquals(now.toString(DateTimeFormat.forPattern("YYYY:MM:dd")), result.getValueAsString("c"));
    }

    @Test
    public void testComputeEpochFrom() throws Exception {
        String commandLine = "DATE EPOCH a TO c";
        Command command = CommandParser.parse(commandLine);
        DateTime now = DateTime.now();
        Entity entity1 = new Entity("source").set("a", now);
        Entity entity2 = new Entity("destination");
        IEntity result = command.execute(entity1, entity2);
        assertNotNull(result);
        assertEquals(now.getMillis(), (long) result.getValueAsLong("c"));
    }

    @Test
    public void testComputeEpochTo() throws Exception {
        String commandLine = "DATE EPOCH a FROM c";
        Command command = CommandParser.parse(commandLine);
        DateTime now = DateTime.now();
        Entity entity1 = new Entity("source").set("a",now.getMillis());
        Entity entity2 = new Entity("destination");
        IEntity result = command.execute(entity1, entity2);
        assertNotNull(result);
        assertEquals(now, result.getValueAsDateTime("c"));
    }

    @Test
    public void testComputeUtcTo() throws Exception {
        String commandLine = "DATE UTC a TO c";
        Command command = CommandParser.parse(commandLine);
        DateTime now = DateTime.now();
        Entity entity1 = new Entity("source").set("a", now);
        Entity entity2 = new Entity("destination");
        IEntity result = command.execute(entity1, entity2);
        assertNotNull(result);
        assertEquals(now.toDateTime(DateTimeZone.UTC).getMillis(), result.getValueAsDateTime("c").getMillis());
    }

    @Test
    public void testComputeUtcFrom() throws Exception {
        String commandLine = "DATE UTC a FROM c";
        Command command = CommandParser.parse(commandLine);
        DateTime now = DateTime.now();
        Entity entity1 = new Entity("source").set("a", now.toDateTime(DateTimeZone.UTC));
        Entity entity2 = new Entity("destination");
        IEntity result = command.execute(entity1, entity2);
        assertNotNull(result);
        assertEquals(now, result.getValueAsDateTime("c"));
    }

}