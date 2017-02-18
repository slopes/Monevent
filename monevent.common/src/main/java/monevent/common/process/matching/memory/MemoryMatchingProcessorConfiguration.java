package monevent.common.process.matching.memory;

import monevent.common.communication.EntityBusManager;
import monevent.common.model.query.IQuery;
import monevent.common.process.IProcessor;
import monevent.common.process.ProcessorManager;
import monevent.common.process.matching.Matching;
import monevent.common.process.matching.MatchingProcessorConfiguration;
import monevent.common.store.StoreManager;

import java.util.List;

/**
 * Created by slopes on 04/02/17.
 */
public class MemoryMatchingProcessorConfiguration extends MatchingProcessorConfiguration {

    public MemoryMatchingProcessorConfiguration() {
        super();
    }

    public MemoryMatchingProcessorConfiguration(List<Matching> matchings, String resultBus) {
        super(matchings, resultBus);
    }

    public MemoryMatchingProcessorConfiguration(String name, IQuery query, List<Matching> matchings, String resultBus) {
        super(name, query, matchings, resultBus);
    }

    @Override
    protected IProcessor doBuild(EntityBusManager entityBusManager, StoreManager storeManager, ProcessorManager processorManager) {
        return new MemoryMatchingProcessor(getName(), getQuery(), getMatchingList(), entityBusManager, getResultBus());
    }
}
