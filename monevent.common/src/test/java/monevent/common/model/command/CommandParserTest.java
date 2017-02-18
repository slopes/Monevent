package monevent.common.model.command;

import monevent.common.model.Entity;
import monevent.common.model.IEntity;
import org.junit.Test;

/**
 * Created by slopes on 05/02/17.
 */
public class CommandParserTest {

    @Test(expected = CommandException.class)
    public void testParseNoField() throws Exception {
        String commandLine = "COPY";
        Command command = CommandParser.parse(commandLine);
        Entity entity1 = new Entity("source").set("a", 12);
        Entity entity2 = new Entity("destination").set("a", true);
        IEntity result = command.execute(entity1, entity2);
    }

    @Test(expected = CommandException.class)
    public void testParseNoLine() throws Exception {
        String commandLine = "";
        Command command = CommandParser.parse(commandLine);
    }

    @Test(expected = CommandException.class)
    public void testParseUnknownCommand() throws Exception {
        String commandLine = "UNKNOWN";
        Command command = CommandParser.parse(commandLine);
    }
}