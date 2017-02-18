package monevent.common.model.command;

import monevent.common.model.Entity;
import monevent.common.model.IEntity;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by slopes on 05/02/17.
 */
public class ComputeCommandTest {

    @Test
    public void testComputeAdd() throws Exception {
        String commandLine = "COMPUTE ADD a b c";
        Command command = CommandParser.parse(commandLine);
        Entity entity1 = new Entity("source").set("a", 1.0);
        Entity entity2 = new Entity("destination").set("b", -1.5);
        IEntity result = command.execute(entity1, entity2);
        assertNotNull(result);
        assertEquals(-0.5, result.getValueAsDouble("c"),0.0);
    }

    @Test
    public void testComputeSubstract() throws Exception {
        String commandLine = "COMPUTE SUBSTRACT a b c";
        Command command = CommandParser.parse(commandLine);
        Entity entity1 = new Entity("source").set("a", 4);
        Entity entity2 = new Entity("destination").set("b", -1);
        IEntity result = command.execute(entity1, entity2);
        assertNotNull(result);
        assertEquals(5, (int) result.getValueAsInteger("c"));
    }

    @Test
    public void testComputeMultiply() throws Exception {
        String commandLine = "COMPUTE MULTIPLY a b c";
        Command command = CommandParser.parse(commandLine);
        Entity entity1 = new Entity("source").set("a", 1.0/3.0);
        Entity entity2 = new Entity("destination").set("b", 3.0);
        IEntity result = command.execute(entity1, entity2);
        assertNotNull(result);
        assertEquals(1.0, result.getValueAsDouble("c"),0.0);
    }

    @Test
    public void testComputeDivide() throws Exception {
        String commandLine = "COMPUTE DIVIDE a b c";
        Command command = CommandParser.parse(commandLine);
        Entity entity1 = new Entity("source").set("a", 10);
        Entity entity2 = new Entity("destination").set("b", 3);
        IEntity result = command.execute(entity1, entity2);
        assertNotNull(result);
        assertEquals(3, (int) result.getValueAsInteger("c"));
    }

}