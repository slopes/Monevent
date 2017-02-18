package monevent.common.process.command;

import com.google.common.collect.Lists;
import monevent.common.model.configuration.ConfigurationException;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by slopes on 18/02/17.
 */
public class CommandProcessorConfigurationTest {
    @Test
    public void testCheckNoError() throws Exception {
        try {
            CommandProcessorConfiguration configuration = new CommandProcessorConfiguration();
            configuration.setName("CommandProcessor");
            configuration.setCommands(Lists.newArrayList("COMMAND"));
            configuration.check();
        } catch (Throwable error) {
            fail(error.getMessage());
        }
    }

    @Test(expected = ConfigurationException.class)
    public void testCheckNoCommands() throws Exception {
        CommandProcessorConfiguration configuration = new CommandProcessorConfiguration();
        configuration.setName("CommandProcessor");
        configuration.check();
    }

}