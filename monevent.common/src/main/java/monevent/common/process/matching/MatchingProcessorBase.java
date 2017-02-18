package monevent.common.process.matching;

import com.google.common.collect.Lists;
import monevent.common.communication.EntityBusManager;
import monevent.common.model.IEntity;
import monevent.common.model.command.Command;
import monevent.common.model.command.CommandParser;
import monevent.common.model.query.IQuery;
import monevent.common.process.ProcessorBase;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;

/**
 * Created by slopes on 01/02/17.
 */
public abstract class MatchingProcessorBase extends ProcessorBase {


    private final EntityBusManager entityBusManager;
    private final String resultBus;
    private final List<Matching> matchingList;
    private final Map<Matching, List<Command>> commands;


    public MatchingProcessorBase(String name, IQuery query, List<Matching> matchingList, EntityBusManager entityBusManager, String resultBus) {
        super(name, query);
        this.resultBus = resultBus;
        this.entityBusManager = entityBusManager;
        this.matchingList = matchingList;
        this.commands = new ConcurrentHashMap<>();
        if (matchingList != null) {
            matchingList.forEach(m -> this.commands.put(m, Lists.transform(m.getCommands(), s -> CommandParser.parse(s))));
        }
    }

    @Override
    protected IEntity doProcess(IEntity entity) throws Exception {


        for (Matching matching : this.matchingList) {
            if (matching == null) continue;

            if (matching.getQuery() != null && !matching.getQuery().match(entity)) continue;

            MatchingResult result = doMatch(matching, entity);
            if (result != null) {
                result.addMatch();

                doApplyCommands(matching, entity, result);

                if (doCheck(matching, result)) {
                    //info("Publishing result %s %s",result.getType(),result.getId());
                    publish(entityBusManager, this.resultBus, result);
                }
            }
        }

        return entity;
    }

    protected abstract boolean doCheck(Matching matching, MatchingResult result);

    private void doApplyCommands(Matching matching, IEntity entity, MatchingResult result) {
        if (matching == null) return;
        List<Command> commands = this.commands.get(matching);
        if (commands == null) return;
        commands.forEach(c -> c.execute(entity, result));
    }

    protected abstract MatchingResult doMatch(Matching matching, IEntity entity) throws ExecutionException;

    @Override
    protected void doStart() {

    }

    @Override
    protected void doStop() {

    }
}