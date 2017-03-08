package monevent.common.process.chain;

import com.google.common.collect.Lists;
import monevent.common.managers.Manager;
import monevent.common.model.IEntity;
import monevent.common.model.command.Command;
import monevent.common.model.command.CommandParser;
import monevent.common.model.query.IQuery;
import monevent.common.process.QueuedProcessorBase;
import monevent.common.store.StoreException;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by slopes on 08/02/17.
 */
public abstract class ChainProcessorBase extends QueuedProcessorBase {

    private final Manager manager;
    private final String resultBus;
    private final List<Chaining> chainingList;
    private final Map<Chaining, List<Command>> commands;

    protected ChainProcessorBase(String name, IQuery query, List<Chaining> chainingList, Manager manager, String resultBus) {
        super(name, query);

        this.resultBus = resultBus;
        this.manager = manager;
        this.chainingList = chainingList;
        this.commands = new ConcurrentHashMap<>();
        if (chainingList != null) {
            chainingList.forEach(c -> {
                this.commands.put(c, Lists.transform(c.getCommands(), s -> CommandParser.parse(s)));
            });
        }
    }

    protected void chain(final IEntity entity) throws StoreException {
        for (Chaining chaining : this.chainingList) {
            if (chaining == null) continue;
            if (chaining.getQuery() != null && !chaining.getQuery().match(entity)) continue;
            doChain(chaining, entity);
        }
    }

    protected void check(final IEntity entity) throws StoreException {
        for (Chaining chaining : this.chainingList) {
            if ( chaining.getQuery() != null && !chaining.getQuery().match(entity)) continue;
            if (doCheck(chaining, entity)) {
                publish(manager, this.resultBus, entity);
            }
        }
    }

    protected abstract boolean doCheck(Chaining chaining, IEntity entity) throws StoreException;

    protected abstract void doChain(Chaining chaining, IEntity entity) throws StoreException;

    @Override
    protected void doExecute(List<IEntity> items) {
        items.forEach(e -> {
            try {

                chain(e);
                check(e);
            } catch (StoreException error) {
                warn("Cannot process entity : %s",error.getMessage());
            }

        });
    }


}
