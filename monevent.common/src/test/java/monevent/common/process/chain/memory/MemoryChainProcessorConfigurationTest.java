package monevent.common.process.chain.memory;

import com.google.common.collect.Lists;
import monevent.common.model.configuration.ConfigurationException;
import monevent.common.model.query.Query;
import monevent.common.process.chain.Chaining;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by slopes on 18/02/17.
 */
public class MemoryChainProcessorConfigurationTest {
    @Test()
    public void testCheckNoError() throws Exception {
        try {
            MemoryChainProcessorConfiguration configuration = new MemoryChainProcessorConfiguration();
            configuration.setName("MemoryChainProcessor");
            configuration.setStore("store");
            configuration.setResultBus("resultBus");
            Chaining chainingA = new Chaining();
            chainingA.setName("chainingA");
            chainingA.setQuery(new Query());
            chainingA.setSubNodeQuery(new Query());
            chainingA.setSuperNodeQuery(new Query());
            chainingA.setCompleteQuery(new Query());
            chainingA.setCommands(Lists.newArrayList());
            chainingA.setSubFields(Lists.newArrayList());
            chainingA.setSuperFields(Lists.newArrayList());
            Chaining chainingB = new Chaining();
            chainingB.setName("chainingB");
            chainingB.setQuery(new Query());
            chainingB.setSubNodeQuery(new Query());
            chainingB.setSuperNodeQuery(new Query());
            chainingB.setCompleteQuery(new Query());
            chainingB.setCommands(Lists.newArrayList());
            chainingB.setSubFields(Lists.newArrayList());
            chainingB.setSuperFields(Lists.newArrayList());
            configuration.setChainingList(Lists.newArrayList(chainingA, chainingB));
            configuration.check();
        } catch (Throwable error) {
            fail(error.getMessage());
        }
    }

    @Test(expected = ConfigurationException.class)
    public void testCheckNoComplete() throws Exception {
        MemoryChainProcessorConfiguration configuration = new MemoryChainProcessorConfiguration();
        configuration.setName("MemoryChainProcessor");
        configuration.setStore("store");
        configuration.setResultBus("resultBus");
        Chaining chaining = new Chaining();
        chaining.setName("chaining");
        chaining.setQuery(new Query());
        chaining.setSubNodeQuery(new Query());
        chaining.setSuperNodeQuery(new Query());
        chaining.setCommands(Lists.newArrayList());
        chaining.setSubFields(Lists.newArrayList());
        chaining.setSuperFields(Lists.newArrayList());
        configuration.setChainingList(Lists.newArrayList(chaining));
        configuration.check();
    }

    @Test(expected = ConfigurationException.class)
    public void testCheckNoSubOrSuperQueries() throws Exception {
        MemoryChainProcessorConfiguration configuration = new MemoryChainProcessorConfiguration();
        configuration.setName("MemoryChainProcessor");
        configuration.setStore("store");
        configuration.setResultBus("resultBus");
        Chaining chaining = new Chaining();
        chaining.setName("chaining");
        chaining.setQuery(new Query());
        chaining.setCompleteQuery(new Query());
        chaining.setCommands(Lists.newArrayList());
        chaining.setSubFields(Lists.newArrayList());
        chaining.setSuperFields(Lists.newArrayList());
        configuration.setChainingList(Lists.newArrayList(chaining));
        configuration.check();
    }

}