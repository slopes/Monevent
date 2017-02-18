package monevent.common.model.command;

import com.google.common.base.Strings;
import monevent.common.model.IEntity;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;

import java.util.List;

/**
 * Created by slopes on 05/02/17.
 */
public class DateCommand extends Command {

    public static String Type = "DATE";

    private DateCommandOption option;
    private String firstOperand;
    private String secondOperand;
    private String destinationField;


    public DateCommand(String option, String firstOperand, String secondOperand, String destinationField) {
        super(DateCommand.Type);
        this.option = Enum.valueOf(DateCommandOption.class, option);
        this.firstOperand = firstOperand;
        this.secondOperand = secondOperand;
        this.destinationField = destinationField;
    }

    public String getFirstOperand() {
        return firstOperand;
    }

    public void setFirstOperand(String firstOperand) {
        this.firstOperand = firstOperand;
    }

    public String getSecondOperand() {
        return secondOperand;
    }

    public void setSecondOperand(String secondOperand) {
        this.secondOperand = secondOperand;
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

            switch (this.option) {
                case SUBSTRACT:
                    destination.setValue(this.destinationField, substract(source, destination));
                    break;
                case FORMAT:
                    destination.setValue(this.destinationField, format(source));
                    break;
                case EPOCH:
                    destination.setValue(this.destinationField, epoch(source));
                    break;
                case UTC:
                    destination.setValue(this.destinationField, utc(source));
                    break;
                default:
                    throw new CommandException(String.format("Unhandled operation %s. Available operation are : SUBSTRACT,FORMAT,EPOCH,UTC", this.option));
            }
            return destination;
        }


        throw new CommandException("Source or destination cannot be null");

    }

    private Comparable utc(IEntity source) {
        if (!source.contains(this.firstOperand)) {
            throw new CommandException(String.format("Source does not contains field %s", this.firstOperand));
        }

        if (Strings.isNullOrEmpty(this.secondOperand)) {
            throw new CommandException(String.format("The conversion direction  cannot be null or empty %s. Should be TO or FROM", this.secondOperand));
        }

        if ("TO".equalsIgnoreCase(this.secondOperand)) {
            DateTime firstDate = source.getValueAsDateTime(firstOperand);
            return firstDate.toDateTime(DateTimeZone.UTC);
        }

        if ("FROM".equalsIgnoreCase(this.secondOperand)) {
            DateTime firstDate = source.getValueAsDateTime(firstOperand);
            return new DateTime(firstDate.getMillis());
        }

        throw new CommandException(String.format("The conversion direction is unknown %s. Should be TO or FROM", this.secondOperand));
    }

    private Comparable epoch(IEntity source) {
        if (!source.contains(this.firstOperand)) {
            throw new CommandException(String.format("Source does not contains field %s", this.firstOperand));
        }

        if (Strings.isNullOrEmpty(this.secondOperand)) {
            throw new CommandException(String.format("The conversion direction  cannot be null or empty %s. Should be TO or FROM", this.secondOperand));
        }

        if ("TO".equalsIgnoreCase(this.secondOperand)) {

            DateTime firstDate = source.getValueAsDateTime(firstOperand);
            return firstDate.getMillis();
        }

        if ("FROM".equalsIgnoreCase(this.secondOperand)) {
            long firstDate = source.getValueAsLong(firstOperand);
            return new DateTime(firstDate);
        }

        throw new CommandException(String.format("The conversion direction is unknown %s. Should be TO or FROM", this.secondOperand));

    }

    private Comparable format(IEntity source) {
        if (!source.contains(this.firstOperand)) {
            throw new CommandException(String.format("Source does not contains field %s", this.firstOperand));
        }

        if (Strings.isNullOrEmpty(this.secondOperand)) {
            throw new CommandException(String.format("The date format cannot be null or empty %s", this.secondOperand));
        }

        DateTime firstDate = source.getValueAsDateTime(firstOperand);
        return firstDate.toString(DateTimeFormat.forPattern(this.secondOperand));
    }

    private Comparable substract(IEntity source, IEntity destination) {
        if (!source.contains(this.firstOperand)) {
            throw new CommandException(String.format("Source does not contains field %s", this.firstOperand));
        }

        if (!destination.contains(this.secondOperand)) {
            throw new CommandException(String.format("Destination does not contains field %s", this.secondOperand));
        }

        DateTime firstDate = source.getValueAsDateTime(this.firstOperand);
        DateTime secondDate = destination.getValueAsDateTime(this.secondOperand);
        return firstDate.getMillis() - secondDate.getMillis();
    }

    public static Command Parse(List<String> tokens) {

        if (tokens.size() == 5) {
            return new DateCommand(tokens.get(1), tokens.get(2), tokens.get(3), tokens.get(4));
        } else {
            throw new CommandException("Invalid commande line for compute command. Must be : DATE option firstOperand secondOperand destinationField");
        }
    }
}
