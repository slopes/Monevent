package monevent.common.process;

import monevent.common.managers.ManagerBase;

/**
 * Created by steph on 12/03/2016.
 */
public class ProcessorManager extends ManagerBase<IProcessor> {
    private final IProcessorFactory processorFactory;


    public ProcessorManager(String name,IProcessorFactory processorFactory) {
        super(name);
        this.processorFactory = processorFactory;
    }

    @Override
    protected IProcessor build(String key) {
        return processorFactory.build(key,this);
    }



}
