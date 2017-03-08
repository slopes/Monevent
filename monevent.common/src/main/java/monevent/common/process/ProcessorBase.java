package monevent.common.process;

import com.google.common.eventbus.AllowConcurrentEvents;
import com.google.common.eventbus.Subscribe;
import monevent.common.managers.ManageableBase;
import monevent.common.model.Entity;
import monevent.common.model.IEntity;
import monevent.common.model.query.IQuery;

/**
 * Created by Stephane on 21/11/2015.
 */
public abstract class ProcessorBase extends ManageableBase implements IProcessor {

    private final IQuery query;

    protected ProcessorBase(String name, IQuery query) {
        super(name);
        this.query = query;
    }

    @Override
    @Subscribe
    @AllowConcurrentEvents
    public IEntity process(IEntity entity) throws ProcessorException {
        String entityName = Entity.getEntityId(entity);

        try {
            if (entity == null) return entity;
            if (this.query != null && !this.query.match(entity)) return entity;
                return doProcess(entity.clone());
        } catch (Exception error) {
            error("Cannot process entity %s.", error, entityName);
            throw new ProcessorException(trace("Cannot process entity %s.", entityName), error);
        } finally {
            debug(" ... entity %s processed.", entityName);
        }
    }


    protected abstract IEntity doProcess(IEntity entity) throws Exception;
}
