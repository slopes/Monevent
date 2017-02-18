package monevent.common.model.command;

import monevent.common.model.IEntity;

import java.util.List;

/**
 * Created by slopes on 05/02/17.
 */
public class MinimumCommand extends Command {
    public static String Type = "MINIMUM";

    private String sourceFieldToCompare;
    private String destinationFieldToCompare;
    private String destinationField;


    public MinimumCommand(String sourceFieldToCompare, String destinationFieldToCompare, String destinationField) {
        super(MinimumCommand.Type);
        this.sourceFieldToCompare = sourceFieldToCompare;
        this.destinationFieldToCompare = destinationFieldToCompare;
        this.destinationField = destinationField;
    }

    public String getSourceFieldToCompare() {
        return sourceFieldToCompare;
    }

    public void setSourceFieldToCompare(String sourceFieldToCompare) {
        this.sourceFieldToCompare = sourceFieldToCompare;
    }

    public String getDestinationFieldToCompare() {
        return destinationFieldToCompare;
    }

    public void setDestinationFieldToCompare(String destinationFieldToCompare) {
        this.destinationFieldToCompare = destinationFieldToCompare;
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
            if (source.contains(this.sourceFieldToCompare)) {
                Comparable sourceValue = (Comparable) source.getValue(this.sourceFieldToCompare);

                if (destination.contains(this.destinationFieldToCompare)) {
                    Comparable destinationValue = (Comparable) destination.getValue(this.destinationFieldToCompare);
                    if (destinationValue.compareTo(sourceValue) > 0) {
                        destination.setValue(this.destinationField, sourceValue);
                    } else {
                        destination.setValue(this.destinationField, destinationValue);
                    }
                } else {
                    destination.setValue(this.destinationField, sourceValue);
                }
            }
            return destination;
        }
        throw new CommandException("Source or destination cannot be null");
    }

    public static Command Parse(List<String> tokens) {

        if (tokens.size() == 4) {
            return new MinimumCommand(tokens.get(1), tokens.get(2), tokens.get(3));
        } else {
            throw new CommandException("Invalid command line for minimum command. Must be : MINIMUM sourceFieldToCompare destinationFieldToCompare destinationField");
        }
    }
}
