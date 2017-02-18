package monevent.common.model.command;

import com.google.common.base.Strings;
import monevent.common.model.IEntity;

import java.util.List;

/**
 * Created by slopes on 05/02/17.
 */
public class CopyCommand extends Command {

    public static String Type = "COPY";

    private String fieldToCopy;
    private String destinationField;


    public CopyCommand(String fieldToCopy, String destinationField) {
        super(CopyCommand.Type);
        this.fieldToCopy = fieldToCopy;
        this.destinationField = destinationField;
    }

    public String getFieldToCopy() {
        return fieldToCopy;
    }

    public void setFieldToCopy(String fieldToCopy) {
        this.fieldToCopy = fieldToCopy;
    }

    public String getDestinationField() {
        return destinationField;
    }

    public void setDestinationField(String destinationField) {
        this.destinationField = destinationField;
    }

    @Override
    public IEntity execute(IEntity source, IEntity destination) throws CommandException {
        if (source != null && destination != null) {
            if (source.contains(this.fieldToCopy)) {
                Object value = source.getValue(this.fieldToCopy);
                if (Strings.isNullOrEmpty(this.destinationField)) {
                    destination.setValue(this.fieldToCopy, value);
                } else {
                    destination.setValue(this.destinationField, value);
                }
                return destination;
            }
            throw new CommandException(String.format("Source does not contains field %s", this.fieldToCopy));
        }
        throw new CommandException("Source or destination cannot be null");
    }

    public static Command Parse(List<String> tokens) {

        if (tokens.size() == 3) {
            return new CopyCommand(tokens.get(1), tokens.get(2));
        } else {
            throw new CommandException("Invalid commande line for copy command. Must be : COPY fieldToCopy destinationField");
        }
    }
}
