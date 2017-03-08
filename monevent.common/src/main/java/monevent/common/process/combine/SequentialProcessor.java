package monevent.common.process.combine;

import com.google.common.collect.ImmutableList;
import monevent.common.managers.Manager;
import monevent.common.model.IEntity;
import monevent.common.model.query.IQuery;
import monevent.common.process.IProcessor;
import monevent.common.process.ProcessorBase;
import monevent.common.process.ProcessorException;

import java.util.List;

/**
 * Created by slopes on 15/01/17.
 */
public class SequentialProcessor extends ProcessorBase {

    private final Manager manager;
    private final List<String> processors;


    protected SequentialProcessor(String name, IQuery query, Manager manager, List<String> processors) {
        super(name, query);
        this.manager = manager;
        this.processors = ImmutableList.copyOf(processors);
    }

    @Override
    protected IEntity doProcess(IEntity entity) throws Exception {
        try {
            for (String processorName : this.processors) {
                IProcessor processor = this.manager.get(processorName);
                if (processor != null) {
                    entity = processor.process(entity);
                } else {
                    throw new ProcessorException(String.format("The processor %s does not exist", processorName));
                }
            }

        } catch (Exception error) {
            error("Aborting sequence", error);
        }
        return entity;
    }

    @Override
    protected void doStart() {


    }

    @Override
    protected void doStop() {

    }


}
