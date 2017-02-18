package monevent.common.process.chain;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import monevent.common.model.configuration.ConfigurationException;
import monevent.common.model.query.IQuery;
import monevent.common.process.ProcessorConfiguration;

import java.util.List;

/**
 * Created by slopes on 08/02/17.
 */
public abstract class ChainProcessorConfiguration extends ProcessorConfiguration {

    private List<Chaining> chainingList;
    private String resultBus;


    public ChainProcessorConfiguration() {
        this.chainingList = Lists.newArrayList();
    }

    public ChainProcessorConfiguration(String name, IQuery query, List<Chaining> chainingList, String resultBus) {
        super(name, query);
        this.chainingList = chainingList;
        this.resultBus = resultBus;
    }

    public List<Chaining> getChainingList() {
        return chainingList;
    }

    public void setChainingList(List<Chaining> chainingList) {
        this.chainingList = chainingList;
    }

    public String getResultBus() {
        return resultBus;
    }

    public void setResultBus(String resultBus) {
        this.resultBus = resultBus;
    }

    @Override
    public void check() throws ConfigurationException {
        super.check();
        if (Strings.isNullOrEmpty(getResultBus()))
            throw new ConfigurationException("The result bus cannot be null or empty.");
        if (getChainingList() == null || getChainingList().size() ==0)
            throw new ConfigurationException("The list of chaining cannot be null or empty.");
        getChainingList().forEach(c->c.check());
    }
}
