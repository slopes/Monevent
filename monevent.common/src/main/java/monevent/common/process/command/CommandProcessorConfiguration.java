package monevent.common.process.command;

import com.google.common.collect.Lists;
import monevent.common.managers.Manager;
import monevent.common.model.configuration.ConfigurationException;
import monevent.common.model.query.IQuery;
import monevent.common.process.IProcessor;
import monevent.common.process.ProcessorConfiguration;

import java.util.List;

/**
 * Created by slopes on 07/02/17.
 */
public class CommandProcessorConfiguration extends ProcessorConfiguration {
    private List<String> commands;

    public CommandProcessorConfiguration() {
        this.commands = Lists.newArrayList();
    }

    public CommandProcessorConfiguration(String name, IQuery query, List<String> commands) {
        super(name, query);
        this.commands = commands;
    }

    public List<String> getCommands() {
        return commands;
    }

    public void setCommands(List<String> commands) {
        this.commands = commands;
    }


    @Override
    public void check() throws ConfigurationException {
        super.check();
        if (getCommands() == null || getCommands().size() == 0)
            throw new ConfigurationException("The list of commands cannot be null or empty.");

    }

    @Override
    protected IProcessor doBuild(Manager manager) {
        return new CommandProcessor(getName(), getQuery(), getCommands());
    }
}
