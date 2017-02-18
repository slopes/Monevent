package monevent.common.process.command;

import com.google.common.collect.Lists;
import monevent.common.communication.EntityBusManager;
import monevent.common.model.query.IQuery;
import monevent.common.process.IProcessor;
import monevent.common.process.ProcessorConfiguration;
import monevent.common.process.ProcessorManager;
import monevent.common.store.StoreManager;

import java.util.List;

/**
 * Created by slopes on 07/02/17.
 */
public class CommandProcessorConfiguration extends ProcessorConfiguration {
    List<String> commands;

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
    protected IProcessor doBuild(EntityBusManager entityBusManager, StoreManager storeManager, ProcessorManager processorManager) {
        return new CommandProcessor(getName(),getQuery(),getCommands());
    }
}
