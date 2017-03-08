package monevent.common.process.matching.memory;

import com.google.common.collect.Lists;
import monevent.common.communication.EntityBusConfiguration;
import monevent.common.communication.IEntityBus;
import monevent.common.model.Entity;
import monevent.common.model.IEntity;
import monevent.common.model.configuration.factory.memory.MemoryConfigurationFactory;
import monevent.common.model.event.Event;
import monevent.common.model.query.IQuery;
import monevent.common.model.query.Query;
import monevent.common.model.query.QueryCriterionType;
import monevent.common.process.*;
import monevent.common.process.matching.Matching;
import monevent.common.process.store.StoreProcessorConfiguration;
import monevent.common.store.IStore;
import monevent.common.store.StoreConfiguration;
import monevent.common.store.StoreException;
import monevent.common.store.memory.MemoryStoreConfiguration;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.List;
import java.util.function.Predicate;

import static org.junit.Assert.*;

/**
 * Created by slopes on 01/02/17.
 */
public class MemoryMatchingProcessorTest extends ProcessorTest {


    @Before
    public void setUpClass() {
        start();
    }

    @After
    public void tearDownClass() {
        stop();
    }

    @Override
    protected void addConfiguration() {

        MemoryConfigurationFactory<EntityBusConfiguration> entityBusConfigurationFactory = new MemoryConfigurationFactory("entityBusConfigurationFactory");
        entityBusConfigurationFactory.add(new EntityBusConfiguration("resultBus"));
        this.factory.addFactory( entityBusConfigurationFactory);

        MemoryConfigurationFactory<StoreConfiguration> storeConfigurationFactory = new MemoryConfigurationFactory("storeConfigurationFactory");
        storeConfigurationFactory.add(new MemoryStoreConfiguration("store"));
        this.factory.addFactory(storeConfigurationFactory);

        MemoryConfigurationFactory<ProcessorConfiguration> processorConfigurationFactory = new MemoryConfigurationFactory("processorConfigurationFactory");
        Predicate<IEntity> predicate = entity -> {
            IStore store = this.manager.get("store");
            try {
                store.create(entity);
            } catch (StoreException error) {
                fail(error.getMessage());
            }
            return true;
        };

        processorConfigurationFactory.add(new GenericProcessorConfiguration("genericProcessor",predicate) );

        List<Matching> matchings = Lists.newArrayList();
        IQuery queryA = new Query().addCriterion("name", "TestA", QueryCriterionType.Is);
        List<String> commandsA = Lists.newArrayList();
        commandsA.add("MINIMUM timestamp start start");
        commandsA.add("MAXIMUM timestamp stop stop");
        commandsA.add("COPY a value");
        Matching matchingA = new Matching(Lists.newArrayList("a"), 2, "nodeA", queryA, commandsA);
        matchings.add(matchingA);
        List<String> commandsAB = Lists.newArrayList();
        commandsAB.add("MINIMUM timestamp start start");
        commandsAB.add("MAXIMUM timestamp stop stop");
        commandsAB.add("COPY b value");
        IQuery queryAB = new Query().addCriterion("name", "TestAB", QueryCriterionType.Is);
        Matching matchingAB = new Matching(Lists.newArrayList("a", "b"), 2, "nodeAB", queryAB, commandsAB);
        matchings.add(matchingAB);
        ProcessorConfiguration matchingProcessorConfiguration = new MemoryMatchingProcessorConfiguration("matchingProcessor", null, matchings, "resultBus");
        processorConfigurationFactory.add(matchingProcessorConfiguration);

        ProcessorConfiguration storeProcessorConfiguration = new StoreProcessorConfiguration("storeProcessor",null,"store");
        processorConfigurationFactory.add(storeProcessorConfiguration );
        this.factory.addFactory( processorConfigurationFactory);
    }


    @Test
    public void testConfiguration() {
        String name = "matchingProcessor";
        String resultBus = "resultBus";
        List<Matching> matchings = Lists.newArrayList();
        IQuery queryA = new Query().addCriterion(Entity.type, "TestA", QueryCriterionType.Is);
        List<String> commandsA = Lists.newArrayList();
        commandsA.add("MINIMUM @timestart start");
        commandsA.add("MAXIMUM @timestart stop");
        commandsA.add("COPY a value");
        Matching matchingA = new Matching(Lists.newArrayList("a"), 2, "nodeA", queryA, commandsA);
        matchings.add(matchingA);
        List<String> commandsAB = Lists.newArrayList();
        commandsAB.add("MINIMUM @timestart start");
        commandsAB.add("MAXIMUM @timestart stop");
        commandsAB.add("COPY b value");
        IQuery queryAB = new Query().addCriterion(Entity.type, "TestAB", QueryCriterionType.Is);
        Matching matchingAB = new Matching(Lists.newArrayList("a", "b"), 2, "nodeAB", queryAB, commandsAB);
        matchings.add(matchingAB);
        ProcessorConfiguration configurationWrite = new MemoryMatchingProcessorConfiguration(name, null, matchings, resultBus);
        File file = new File("src/test/resources/config/processor/" + name + ".json");
        try {
            write(file, configurationWrite);
            MemoryMatchingProcessorConfiguration configurationRead = (MemoryMatchingProcessorConfiguration) read(file);
            assertEquals(name, configurationRead.getName());
            assertEquals(matchings.size(), configurationRead.getMatchingList().size());
            assertEquals(resultBus, configurationRead.getResultBus());
        } finally {
            file.delete();
        }
    }

    @Test
    public void testProcess() throws Exception {


        IProcessor processor = this.manager.get("matchingProcessor");
        IProcessor storeProcessor =  this.manager.get("storeProcessor");
        IEntityBus resultBus = this.manager.get("resultBus");
        resultBus.register(storeProcessor);

        try {
            processor.start();

            IEntity entity1 = processor.process(new Event("TestA").set("a", 12));
            Thread.sleep(50);
            assertNotNull(entity1);
            IEntity entity2 = processor.process(new Event("TestA").set("a", 12));
            assertNotNull(entity2);
            IEntity entity3 = processor.process(new Event("TestA").set("a", 13));
            assertNotNull(entity3);
            IEntity entity4 = processor.process(new Event("TestAB").set("a", 13).set("b", false));
            assertNotNull(entity4);
            IEntity entity5 = processor.process(new Event("TestAB").set("a", 13).set("b", true));
            assertNotNull(entity5);
            Thread.sleep(50);
            IEntity entity6 = processor.process(new Event("TestAB").set("a", 13).set("b", true));
            assertNotNull(entity6);
            Thread.sleep(50);

            IStore store = this.manager.get("store");

            List<IEntity> nodesA = store.read(new Query().addCriterion(Entity.type, "nodeA", QueryCriterionType.Is));
            assertEquals(1, nodesA.size());
            assertEquals(12, (int) nodesA.get(0).getValueAsInteger("a"));
            assertEquals(2, (int) nodesA.get(0).getValueAsInteger("match"));

            List<IEntity> nodesAB = store.read(new Query().addCriterion(Entity.type, "nodeAB", QueryCriterionType.Is));
            assertEquals(1, nodesAB.size());
            assertEquals(13, (int) nodesAB.get(0).getValueAsInteger("a"));
            assertTrue(nodesAB.get(0).getValueAsBoolean("b"));

        } finally {
            resultBus.unregister(storeProcessor);
        }

    }

}