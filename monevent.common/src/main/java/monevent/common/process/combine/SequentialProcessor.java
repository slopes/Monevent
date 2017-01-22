package monevent.common.process.combine;

import com.google.common.collect.ImmutableList;
import monevent.common.model.IEntity;
import monevent.common.model.query.IQuery;
import monevent.common.process.IProcessor;
import monevent.common.process.ProcessorBase;
import monevent.common.process.ProcessorException;
import monevent.common.process.ProcessorManager;

import java.util.List;

/**
 * Created by slopes on 15/01/17.
 */
public class SequentialProcessor extends ProcessorBase {

    private final ProcessorManager processorManager;
    private final List<String> processors;


    protected SequentialProcessor(String name, IQuery query, ProcessorManager processorManager, List<String> processors) {
        super(name, query);
        this.processorManager = processorManager;
        this.processors = ImmutableList.copyOf(processors);
    }

    @Override
    protected IEntity doProcess(IEntity entity) throws Exception {
        for (String processorName : this.processors) {
            IProcessor processor = this.processorManager.load(processorName);
            if (processor != null) {
                entity = processor.process(entity);
            } else {
                throw new ProcessorException(String.format("The processor %s does not exist", processorName));
            }
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
