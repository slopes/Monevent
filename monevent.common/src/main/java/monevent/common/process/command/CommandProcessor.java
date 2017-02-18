package monevent.common.process.command;

import com.google.common.collect.Lists;
import monevent.common.model.IEntity;
import monevent.common.model.command.Command;
import monevent.common.model.command.CommandParser;
import monevent.common.model.query.IQuery;
import monevent.common.process.ProcessorBase;

import java.util.List;

/**
 * Created by slopes on 07/02/17.
 */
public class CommandProcessor extends ProcessorBase {
    private final List<Command> commands;

    public CommandProcessor(String name, IQuery query, List<String> commands) {
        super(name, query);
        this.commands = Lists.transform(commands, c -> CommandParser.parse(c));
    }

    @Override
    protected IEntity doProcess(IEntity entity) throws Exception {
        if ( this.commands != null ) {
            this.commands.forEach(c->c.execute(entity));
        }
        return entity;
    }

    @Override
    protected void doStart() {

    }

    @Override
    protected void doStop() {

    }
}
