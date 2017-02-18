package monevent.common.model.command;

import monevent.common.model.IEntity;

/**
 * Created by slopes on 05/02/17.
 */
public abstract class Command {
    private String type;

    public Command() {
    }

    public Command(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public IEntity execute(IEntity source) throws CommandException {
        return execute(source, source);
    }

    public abstract IEntity execute(IEntity source, IEntity destination) throws CommandException;

}
