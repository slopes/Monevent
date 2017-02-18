package monevent.common.process.matching;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import monevent.common.model.configuration.ConfigurationException;
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

    public void check() throws ConfigurationException {
        super.check();
        if (Strings.isNullOrEmpty(resultBus))
            throw new ConfigurationException("The result bus cannot be null or empty.");
        if (getMatchingList() == null || getMatchingList().size() ==0)
            throw new ConfigurationException("The list of matchings cannot be null or empty.");
        getMatchingList().forEach(m->m.check());

    }
}
