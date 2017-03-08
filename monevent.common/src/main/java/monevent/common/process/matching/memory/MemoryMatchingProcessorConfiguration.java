package monevent.common.process.matching.memory;

import monevent.common.managers.Manager;
import monevent.common.model.query.IQuery;
import monevent.common.process.IProcessor;
import monevent.common.process.matching.Matching;
import monevent.common.process.matching.MatchingProcessorConfiguration;

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
    protected IProcessor doBuild(Manager manager) {
        return new MemoryMatchingProcessor(getName(), getQuery(), getMatchingList(), manager, getResultBus());
    }



}
