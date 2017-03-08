package monevent.common.process;

import monevent.common.managers.Manager;
import monevent.common.model.IEntity;

import java.util.function.Predicate;

/**
 * Created by slopes on 22/02/17.
 */
public class GenericProcessorConfiguration extends ProcessorConfiguration {
    private final Predicate<IEntity> predicate;

    public GenericProcessorConfiguration(String name, Predicate<IEntity> predicate) {
        super(name, null);
        this.predicate = predicate;
    }
    
    @Override
    protected IProcessor doBuild(Manager manager) {
        return new GenericProcessor(getName(), this.predicate);
    }
}
