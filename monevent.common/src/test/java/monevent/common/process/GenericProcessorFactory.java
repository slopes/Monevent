package monevent.common.process;

import monevent.common.model.IEntity;

import java.util.Map;
import java.util.function.Predicate;

/**
 * Created by slopes on 15/01/17.
 */
public class GenericProcessorFactory implements  IProcessorFactory{


    private final Map<String,Predicate<IEntity>> predicates;

    public GenericProcessorFactory(Map<String,Predicate<IEntity>> predicates) {
        this.predicates = predicates;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public void start() throws ProcessorException {

    }

    @Override
    public void stop() throws ProcessorException {

    }

    @Override
    public IProcessor build(String processorName, ProcessorManager processorManager) {
        return new GenericProcessor(processorName, predicates.get(processorName));
    }
}
