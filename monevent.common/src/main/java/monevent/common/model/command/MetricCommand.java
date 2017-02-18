package monevent.common.model.command;

import monevent.common.model.IEntity;
import monevent.common.model.metric.Metric;

import java.util.List;

/**
 * Created by slopes on 05/02/17.
 */
public class MetricCommand extends Command {

    public static String Type = "METRIC";

    private String numericField;
    private String destinationField;


    public MetricCommand(String numericField, String destinationField) {
        super(MetricCommand.Type);
        this.numericField = numericField;
        this.destinationField = destinationField;
    }

    public String getNumericField() {
        return numericField;
    }

    public void setNumericField(String numericField) {
        this.numericField = numericField;
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
            if (source.contains(this.numericField)) {
                double value = source.getValueAsDouble(this.numericField);
                Metric metric = (Metric) destination.getValue(this.destinationField);
                if (metric == null) {
                    metric = new Metric(this.destinationField);
                    destination.setValue(this.destinationField, metric);
                }
                metric.add(value);
                return destination;
            }
            throw new CommandException(String.format("Source does not contains numeric field %s", this.numericField));
        }
        throw new CommandException("Source or destination cannot be null");
    }


    public static Command Parse(List<String> tokens) {

        if (tokens.size() == 3) {
            return new MetricCommand(tokens.get(1), tokens.get(2));
        } else {
            throw new CommandException("Invalid command line for metric command. Must be : METRIC numericField destinationField");
        }
    }
}
