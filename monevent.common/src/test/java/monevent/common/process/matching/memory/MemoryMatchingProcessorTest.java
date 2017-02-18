package monevent.common.process.matching.memory;

import com.google.common.collect.Lists;
import monevent.common.communication.EntityBusManager;
import monevent.common.model.Entity;
import monevent.common.model.IEntity;
import monevent.common.model.event.Event;
import monevent.common.model.query.IQuery;
import monevent.common.model.query.Query;
import monevent.common.model.query.QueryCriterionType;
import monevent.common.process.*;
import monevent.common.process.communication.BusProcessor;
import monevent.common.process.matching.Matching;
import monevent.common.store.StoreException;
import monevent.common.store.memory.MemoryStore;
import org.junit.Test;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Predicate;

import static org.junit.Assert.*;

/**
 * Created by slopes on 01/02/17.
 */
public class MemoryMatchingProcessorTest extends ProcessorTest {

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
        File file = new File("src/test/resources/config/processors/" + name + ".json");
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

        MemoryStore store = new MemoryStore();
        store.start();

        Predicate<IEntity> predicate = entity -> {
            try {
                store.create(entity);
            } catch (StoreException error) {
                fail(error.getMessage());
            }
            return true;
        };


        Map<String, Predicate<IEntity>> predicates = new ConcurrentHashMap<>();
        predicates.put("storeProcessor", predicate);
        IProcessorFactory factory = new GenericProcessorFactory(predicates);
        ProcessorManager processorManager = new ProcessorManager("processorManager", factory);
        processorManager.start();

        EntityBusManager entityBusManager = new EntityBusManager("entityBusManager");
        entityBusManager.start();

        IProcessor busProcessor = new BusProcessor("busProcessor", null, entityBusManager, Lists.newArrayList("resultBus"), processorManager, Lists.newArrayList("storeProcessor"));
        busProcessor.start();


        String name = "matchingProcessor";
        List<Matching> matchings = Lists.newArrayList();
        IQuery queryA = new Query().addCriterion(Entity.type, "TestA", QueryCriterionType.Is);
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
        IQuery queryAB = new Query().addCriterion(Entity.type, "TestAB", QueryCriterionType.Is);
        Matching matchingAB = new Matching(Lists.newArrayList("a", "b"), 2, "nodeAB", queryAB, commandsAB);
        matchings.add(matchingAB);
        ProcessorConfiguration configuration = new MemoryMatchingProcessorConfiguration(name, null, matchings, "resultBus");
        IProcessor processor = configuration.build(entityBusManager, null, processorManager);

        try {
            processor.start();

            IEntity entity1 = processor.process(new Event("TestA", "TestA").set("a", 12));
            Thread.sleep(50);
            assertNotNull(entity1);
            IEntity entity2 = processor.process(new Event("TestA", "TestA").set("a", 12));
            assertNotNull(entity2);
            IEntity entity3 = processor.process(new Event("TestA", "TestA").set("a", 13));
            assertNotNull(entity3);
            IEntity entity4 = processor.process(new Event("TestAB", "TestAB").set("a", 13).set("b", false));
            assertNotNull(entity4);
            IEntity entity5 = processor.process(new Event("TestAB", "TestAB").set("a", 13).set("b", true));
            assertNotNull(entity5);
            Thread.sleep(50);
            IEntity entity6 = processor.process(new Event("TestAB", "TestAB").set("a", 13).set("b", true));
            assertNotNull(entity6);
            Thread.sleep(50);

            List<IEntity> nodesA = store.read(new Query().addCriterion(Entity.type, "nodeA", QueryCriterionType.Is));
            assertEquals(1, nodesA.size());
            assertEquals(12, (int) nodesA.get(0).getValueAsInteger("a"));
            assertEquals(2, (int) nodesA.get(0).getValueAsInteger("match"));

            List<IEntity> nodesAB = store.read(new Query().addCriterion(Entity.type, "nodeAB", QueryCriterionType.Is));
            assertEquals(1, nodesAB.size());
            assertEquals(13, (int) nodesAB.get(0).getValueAsInteger("a"));
            assertTrue(nodesAB.get(0).getValueAsBoolean("b"));

        } finally {
            processor.stop();
            processorManager.stop();
            busProcessor.start();
            entityBusManager.stop();
            store.stop();
        }

    }

}