package monevent.common.process.chain;

import com.google.common.collect.Lists;
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
}
