package monevent.common.process.matching;

import com.google.common.collect.Lists;
import monevent.common.model.query.IQuery;
import monevent.common.process.ProcessorConfiguration;

import java.util.List;


/**
 * Created by slopes on 01/02/17.
 */
public abstract class MatchingProcessorConfiguration extends ProcessorConfiguration {
    private List<Matching> matchingList;
    private String resultBus;

    public MatchingProcessorConfiguration() {
        this.matchingList = Lists.newArrayList();
    }

    public MatchingProcessorConfiguration(List<Matching> matchingList, String resultBus) {
        this.matchingList = matchingList;
        this.resultBus = resultBus;
    }

    public MatchingProcessorConfiguration(String name, IQuery query, List<Matching> matchingList, String resultBus) {
        super(name, query);
        this.matchingList = matchingList;
        this.resultBus = resultBus;
    }

    public List<Matching> getMatchingList() {
        return matchingList;
    }

    public void setMatchingList(List<Matching> matchingList) {
        this.matchingList = matchingList;
    }

    public String getResultBus() {
        return resultBus;
    }

    public void setResultBus(String resultBus) {
        this.resultBus = resultBus;
    }
}
