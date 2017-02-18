package monevent.common.model.command;

import monevent.common.model.IEntity;

import java.util.List;

/**
 * Created by slopes on 05/02/17.
 */
public class ComputeCommand extends Command {

    public static String Type = "COMPUTE";

    private ComputeCommandOptions option;
    private String firstOperand;
    private String secondOperand;
    private String destinationField;


    public ComputeCommand(String option, String firstOperand, String secondOperand, String destinationField) {
        super(ComputeCommand.Type);
        this.option = Enum.valueOf(ComputeCommandOptions.class, option);
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
            if (!source.contains(this.firstOperand)) {
                throw new CommandException(String.format("Source does not contains field %s", this.firstOperand));
            }
            ;

            if (!destination.contains(this.secondOperand)) {
                throw new CommandException(String.format("Destination does not contains field %s", this.secondOperand));
            }

            double firstValue = source.getValueAsDouble(this.firstOperand);
            double secondValue = destination.getValueAsDouble(this.secondOperand);

            switch (this.option) {

                case ADD:
                    destination.setValue(this.destinationField, firstValue + secondValue);
                    break;
                case SUBSTRACT:
                    destination.setValue(this.destinationField, firstValue - secondValue);
                    break;
                case MULTIPLY:
                    destination.setValue(this.destinationField, firstValue * secondValue);
                    break;
                case DIVIDE:
                    destination.setValue(this.destinationField, firstValue / secondValue);
                    break;
                default:
                    throw new CommandException(String.format("Unhandled operation %s. Available operation are : ADD,SUBSTRACT,MULTIPLY,DIVIDE", this.option));
            }
            return destination;
        }


        throw new CommandException("Source or destination cannot be null");

    }

    public static Command Parse(List<String> tokens) {

        if (tokens.size() == 5) {
            return new ComputeCommand(tokens.get(1), tokens.get(2), tokens.get(3), tokens.get(4));
        } else {
            throw new CommandException("Invalid commande line for compute command. Must be : COMPUTE option firstOperand secondOperand destinationField");
        }
    }
}
