package monevent.common.model.command;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;

import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by slopes on 05/02/17.
 */
public class CommandParser {
    public static Command parse(String commnandString) {
        if (Strings.isNullOrEmpty(commnandString)) {
            throw new CommandException("The command line cannot be null");
        }
        StringTokenizer tokenizer = new StringTokenizer(commnandString, " ");
        List<String> tokens = Lists.newArrayList();
        while (tokenizer.hasMoreTokens()) {
            tokens.add(tokenizer.nextToken());
        }
        ;
        if (tokens.size() == 0) {
            throw new CommandException("The command must have at least have one token.");
        }

        String commandToken = tokens.get(0);

        if (CopyCommand.Type.equalsIgnoreCase(commandToken)) {
            return CopyCommand.Parse(tokens);
        }

        if (MaximumCommand.Type.equalsIgnoreCase(commandToken)) {
            return MaximumCommand.Parse(tokens);
        }

        if (MinimumCommand.Type.equalsIgnoreCase(commandToken)) {
            return MinimumCommand.Parse(tokens);
        }

        if (MetricCommand.Type.equalsIgnoreCase(commandToken)) {
            return MetricCommand.Parse(tokens);
        }

        if (DateCommand.Type.equalsIgnoreCase(commandToken)) {
            return DateCommand.Parse(tokens);
        }

        if (ComputeCommand.Type.equalsIgnoreCase(commandToken)) {
            return ComputeCommand.Parse(tokens);
        }

        throw new CommandException(String.format("Uknown command %s.", commandToken));
    }
}
