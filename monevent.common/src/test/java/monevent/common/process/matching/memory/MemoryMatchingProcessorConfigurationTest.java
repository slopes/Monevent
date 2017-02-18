package monevent.common.process.matching.memory;

import com.google.common.collect.Lists;
import monevent.common.model.configuration.ConfigurationException;
import monevent.common.model.query.Query;
import monevent.common.process.matching.Matching;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by slopes on 18/02/17.
 */
public class MemoryMatchingProcessorConfigurationTest {
    @Test()
    public void testCheckNoError() throws Exception {
        try {
            MemoryMatchingProcessorConfiguration configuration = new MemoryMatchingProcessorConfiguration();
            configuration.setName("MemoryMatchingProcessor");
            configuration.setResultBus("resultBus");
            Matching matchingA = new Matching();
            matchingA.setName("matchingA");
            matchingA.setType("matchingA");
            matchingA.setQuery(new Query());
            matchingA.setFields(Lists.newArrayList("field"));
            matchingA.setExpectedMatch(2);
            matchingA.setCommands(Lists.newArrayList());
            Matching matchingB = new Matching();
            matchingB.setName("matchingB");
            matchingB.setType("matchingA");
            matchingB.setQuery(new Query());
            matchingB.setFields(Lists.newArrayList("field"));
            matchingB.setExpectedMatch(2);
            matchingB.setCommands(Lists.newArrayList());
            configuration.setMatchingList(Lists.newArrayList(matchingA, matchingB));
            configuration.check();
        } catch (Throwable error) {
            fail(error.getMessage());
        }
    }

    @Test(expected = ConfigurationException.class)
    public void testCheckWrongExpectedMatch() throws Exception {
        MemoryMatchingProcessorConfiguration configuration = new MemoryMatchingProcessorConfiguration();
        configuration.setName("MemoryMatchingProcessor");
        configuration.setResultBus("resultBus");
        Matching matching = new Matching();
        matching.setName("matchingA");
        matching.setType("matchingA");
        matching.setQuery(new Query());
        matching.setFields(Lists.newArrayList("field"));
        matching.setExpectedMatch(-2);
        matching.setCommands(Lists.newArrayList());
        configuration.setMatchingList(Lists.newArrayList(matching));
        configuration.check();
    }


    @Test(expected = ConfigurationException.class)
    public void testCheckNoMatchings() throws Exception {
        MemoryMatchingProcessorConfiguration configuration = new MemoryMatchingProcessorConfiguration();
        configuration.setName("MemoryMatchingProcessor");
        configuration.setResultBus("resultBus");
        configuration.check();
    }

    @Test(expected = ConfigurationException.class)
    public void testCheckNoResultBus() throws Exception {
        MemoryMatchingProcessorConfiguration configuration = new MemoryMatchingProcessorConfiguration();
        configuration.setName("MemoryMatchingProcessor");
        Matching matching = new Matching();
        matching.setName("matchingA");
        matching.setType("matchingA");
        matching.setQuery(new Query());
        matching.setFields(Lists.newArrayList("field"));
        matching.setExpectedMatch(2);
        matching.setCommands(Lists.newArrayList());
        configuration.setMatchingList(Lists.newArrayList(matching));
        configuration.check();
    }


    @Test(expected = ConfigurationException.class)
    public void testCheckNoName() throws Exception {
        MemoryMatchingProcessorConfiguration configuration = new MemoryMatchingProcessorConfiguration();
        configuration.setName("MemoryMatchingProcessor");
        configuration.setResultBus("resultBus");
        Matching matching = new Matching();
        matching.setType("matchingA");
        matching.setQuery(new Query());
        matching.setFields(Lists.newArrayList("field"));
        matching.setExpectedMatch(2);
        matching.setCommands(Lists.newArrayList());
        configuration.setMatchingList(Lists.newArrayList(matching));
        configuration.check();
    }

    @Test(expected = ConfigurationException.class)
    public void testCheckNoFields() throws Exception {
        MemoryMatchingProcessorConfiguration configuration = new MemoryMatchingProcessorConfiguration();
        configuration.setName("MemoryMatchingProcessor");
        configuration.setResultBus("resultBus");
        Matching matching = new Matching();
        matching.setName("matchingA");
        matching.setType("matchingA");
        matching.setQuery(new Query());
        matching.setExpectedMatch(2);
        matching.setCommands(Lists.newArrayList());
        configuration.setMatchingList(Lists.newArrayList(matching));
        configuration.check();
    }


    @Test(expected = ConfigurationException.class)
    public void testCheckNoType() throws Exception {
        MemoryMatchingProcessorConfiguration configuration = new MemoryMatchingProcessorConfiguration();
        configuration.setName("MemoryMatchingProcessor");
        configuration.setResultBus("resultBus");
        Matching matching = new Matching();
        matching.setName("matchingA");
        matching.setQuery(new Query());
        matching.setFields(Lists.newArrayList("field"));
        matching.setExpectedMatch(2);
        matching.setCommands(Lists.newArrayList());
        configuration.setMatchingList(Lists.newArrayList(matching));
        configuration.check();
    }

}