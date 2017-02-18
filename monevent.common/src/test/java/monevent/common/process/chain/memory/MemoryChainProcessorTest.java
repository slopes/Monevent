package monevent.common.process.chain.memory;


import com.eaio.uuid.UUID;
import com.google.common.collect.Lists;
import monevent.common.communication.EntityBusManager;
import monevent.common.managers.ManageableBase;
import monevent.common.model.Entity;
import monevent.common.model.IEntity;
import monevent.common.model.configuration.factory.FileConfigurationFactory;
import monevent.common.model.event.Event;
import monevent.common.model.query.IQuery;
import monevent.common.model.query.Query;
import monevent.common.model.query.QueryCriterionType;
import monevent.common.process.*;
import monevent.common.process.chain.Chaining;
import monevent.common.process.command.CommandProcessorConfiguration;
import monevent.common.process.matching.memory.MemoryMatchingProcessorConfiguration;
import monevent.common.store.IStore;
import monevent.common.store.StoreException;
import monevent.common.store.StoreFactory;
import monevent.common.store.StoreManager;
import org.junit.Test;

import java.io.File;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by slopes on 06/02/17.
 */
public class MemoryChainProcessorTest extends ProcessorTest {





    private Event getEvent(String type) {
        Event event = new Event(type);
        return event;
    }


    @Test
    public void testConfiguration() {
        String name = "chainProcessor";

        IQuery query = new Query().addCriterion(Entity.type,"nodeB",QueryCriterionType.Is);
        List<String> subFields = Lists.newArrayList("idC");
        IQuery subNodeQuery = new Query().addCriterion(Entity.type,"nodeC",QueryCriterionType.Is).addCriterion(MemoryChainProcessor.superNode,null,QueryCriterionType.NotExists);
        List<String> superFields = Lists.newArrayList("idA");
        IQuery supêrNodeQuery = new Query().addCriterion(Entity.type,"nodeA",QueryCriterionType.Is);
        IQuery completeQuery = new Query().addCriterion(MemoryChainProcessor.superNode, null, QueryCriterionType.Exists).addCriterion(MemoryChainProcessor.subNodes,1,QueryCriterionType.Count);
        List<String> commands = Lists.newArrayList();
        commands.add("MINIMUM @timestart start");
        commands.add("MAXIMUM @timestart stop");
        commands.add("COPY a value");

        Chaining chaining = new Chaining();
        chaining.setQuery(query);
        chaining.setSubFields(subFields);
        chaining.setSubNodeQuery(subNodeQuery);
        chaining.setSuperFields(superFields);
        chaining.setSuperNodeQuery(supêrNodeQuery);
        chaining.setCompleteQuery(completeQuery);
        chaining.setCompleteQuery(completeQuery);
        chaining.setCommands(commands);

        MemoryChainProcessorConfiguration configurationWrite = new MemoryChainProcessorConfiguration();
        configurationWrite.setName(name);
        configurationWrite.setChainingList(Lists.newArrayList(chaining));
        File file = new File("src/test/resources/config/processors/" + name + ".json");
        try {
            write(file, configurationWrite);
            MemoryChainProcessorConfiguration configurationRead = (MemoryChainProcessorConfiguration) read(file);
            assertEquals(name, configurationRead.getName());
            assertEquals(1, configurationRead.getChainingList().size());
        } finally {
            file.delete();
        }
    }

    @Test
    public void testChaine() throws Exception {

        FileConfigurationFactory factory = new FileConfigurationFactory("src/test/resources/chain");
        factory.start();

        EntityBusManager entityBusManager = new EntityBusManager("entityBusManager");
        entityBusManager.start();

        StoreFactory storeFactory = new StoreFactory("storeFactory", factory);
        storeFactory.start();

        StoreManager storeManager = new StoreManager("storeManager", storeFactory);
        storeManager.start();

        ProcessorFactory processorFactory = new ProcessorFactory(entityBusManager, storeManager, factory);
        processorFactory.start();

        ProcessorManager processorManager = new ProcessorManager("processorManager", processorFactory);
        processorManager.start();


        IProcessor eventProcessor = processorManager.load("eventProcessor");
        IProcessor nodeBusProcessor = processorManager.load("nodeBusProcessor");
        IProcessor storeProcessor = processorManager.load("storeProcessor");
        IProcessor latencyProcessor = processorManager.load("latencyProcessor");
        IProcessor nodeProcessor = processorManager.load("nodeProcessor");
        IProcessor chainProcessor = processorManager.load("chainProcessor");
        IProcessor chainBusProcessor = processorManager.load("chainBusProcessor");


        int chainCount = 100;
        for (int index = 0; index < chainCount; index++) {
            String idA = new UUID().toString();
            String idB = new UUID().toString();
            String idC = new UUID().toString();
            String idD = new UUID().toString();
            String idE = new UUID().toString();
            Event event = null;
            //Component A
            event = getEvent("eventA");
            event.setValue("idA", idA);
            eventProcessor.process(event);
            event = getEvent("eventA");
            event.setValue("idA", idA);
            eventProcessor.process(event);

            //Component B
            event = getEvent("eventB");
            event.setValue("idA", idA);
            event.setValue("idB", idB);
            eventProcessor.process(event);
            event = getEvent("eventB");
            event.setValue("idA", idA);
            event.setValue("idB", idB);
            eventProcessor.process(event);

            //Component C
            event = getEvent("eventC");
            event.setValue("idC", idC);
            event.setValue("idB", idB);
            eventProcessor.process(event);
            event = getEvent("eventC");
            event.setValue("idC", idC);
            event.setValue("idB", idB);
            eventProcessor.process(event);

            //Component D
            event = getEvent("eventD");
            event.setValue("idC", idC);
            event.setValue("idD", idD);
            eventProcessor.process(event);

            event = getEvent("eventD");
            event.setValue("idC", idC);
            event.setValue("idD", idD);
            eventProcessor.process(event);

            //Component E
            event = getEvent("eventE");
            event.setValue("idE", idE);
            event.setValue("idD", idD);
            eventProcessor.process(event);

            for (int indexF = 0; indexF < 10; indexF++) {
                String idF = new UUID().toString();
                event = getEvent("eventF");
                event.setValue("idE", idE);
                event.setValue("idF", idF);
                eventProcessor.process(event);


                for (int indexG = 0; indexG < 10; indexG++) {
                    String idG = new UUID().toString();
                    event = getEvent("eventG");
                    event.setValue("idG", idG);
                    event.setValue("idF", idF);
                    eventProcessor.process(event);
                    event = getEvent("eventG");
                    event.setValue("idG", idG);
                    event.setValue("idF", idF);
                    eventProcessor.process(event);
                }

                event = getEvent("eventF");
                event.setValue("idE", idE);
                event.setValue("idF", idF);
                eventProcessor.process(event);


            }

            event = getEvent("eventE");
            event.setValue("idE", idE);
            event.setValue("idD", idD);
            eventProcessor.process(event);

        }
        Thread.sleep(500);

        IStore nodeStore = storeManager.load("store");

        checkNodes("nodeA", chainCount, 2, nodeStore);
        checkNodes("nodeB", chainCount, 2, nodeStore);
        checkNodes("nodeC", chainCount, 2, nodeStore);
        checkNodes("nodeD", chainCount, 2, nodeStore);
        checkNodes("nodeE", chainCount, 10, nodeStore);
        checkNodes("nodeF", chainCount * 10, 10, nodeStore);
        checkNodes("nodeG", chainCount * 10 * 10, 0, nodeStore);


    }

    private void checkNodes(String nodeType, int nodeCount, int expectedMatch, IStore nodeStore) throws StoreException {
        List<IEntity> nodes = nodeStore.read(new Query().addCriterion(Entity.type, nodeType, QueryCriterionType.Is));
        assertNotNull(nodes);
        assertEquals(nodeCount, nodes.size());
        for (IEntity node : nodes) {
            checkNode(nodeType,node,nodeStore);
        }
    }

    private void checkNode(String nodeType, IEntity node,IStore nodeStore) throws StoreException {
        if ( "nodeA".equals(nodeType)) {
            List<String> subNodes = node.getValueAsList(MemoryChainProcessor.subNodes);
            if (subNodes.size() > 1 ) {
              fail(String.format("nodeA : %s  subNodes %s %s",node.getId(), subNodes.get(0),subNodes.get(1)));
            }
            assertEquals(1,subNodes.size());
            IEntity subNode = nodeStore.read(subNodes.get(0));
            assertNotNull(subNode);
            assertEquals("nodeB",subNode.getType());
            assertEquals(node.getValueAsString("idA"),subNode.getValueAsString("idA"));
            IEntity superNode = nodeStore.read(node.getValueAsString(MemoryChainProcessor.superNode));
            assertNull(superNode);
        }

        if ( "nodeB".equals(nodeType)) {
            List<String> subNodes = node.getValueAsList(MemoryChainProcessor.subNodes);
            assertEquals(1,subNodes.size());
            IEntity subNode = nodeStore.read(subNodes.get(0));
            assertNotNull(subNode);
            assertEquals("nodeC",subNode.getType());
            assertEquals(node.getValueAsString("idB"),subNode.getValueAsString("idB"));
            IEntity superNode = nodeStore.read(node.getValueAsString(MemoryChainProcessor.superNode));
            assertNotNull(superNode);
            assertEquals("nodeA", superNode.getType());
            assertEquals(node.getValueAsString("idA"), superNode.getValueAsString("idA"));
        }


        if ( "nodeC".equals(nodeType)) {
            List<String> subNodes = node.getValueAsList(MemoryChainProcessor.subNodes);
            assertEquals(1,subNodes.size());
            IEntity subNode = nodeStore.read(subNodes.get(0));
            assertNotNull(subNode);
            assertEquals("nodeD",subNode.getType());
            assertEquals(node.getValueAsString("idC"),subNode.getValueAsString("idC"));
            IEntity superNode = nodeStore.read(node.getValueAsString(MemoryChainProcessor.superNode));
            assertNotNull(superNode);
            assertEquals("nodeB", superNode.getType());
            assertEquals(node.getValueAsString("idB"), superNode.getValueAsString("idB"));
        }

        if ( "nodeD".equals(nodeType)) {
            List<String> subNodes = node.getValueAsList(MemoryChainProcessor.subNodes);
            assertEquals(1,subNodes.size());
            IEntity subNode = nodeStore.read(subNodes.get(0));
            assertNotNull(subNode);
            assertEquals("nodeE",subNode.getType());
            assertEquals(node.getValueAsString("idD"),subNode.getValueAsString("idD"));
            IEntity superNode = nodeStore.read(node.getValueAsString(MemoryChainProcessor.superNode));
            assertNotNull(superNode);
            assertEquals("nodeC", superNode.getType());
            assertEquals(node.getValueAsString("idC"), superNode.getValueAsString("idC"));
        }

        if ( "nodeE".equals(nodeType)) {
            List<String> subNodes = node.getValueAsList(MemoryChainProcessor.subNodes);
            assertEquals(10,subNodes.size());
            subNodes.forEach( n-> {
                IEntity subNode = null;
                try {
                    subNode = nodeStore.read(n);
                } catch (StoreException e) {
                    fail(e.getMessage());
                }
                assertNotNull(subNode);
                assertEquals("nodeF",subNode.getType());
                assertEquals(node.getValueAsString("idE"),subNode.getValueAsString("idE"));
            });
            IEntity superNode = nodeStore.read(node.getValueAsString(MemoryChainProcessor.superNode));
            assertNotNull(superNode);
            assertEquals("nodeD", superNode.getType());
            assertEquals(node.getValueAsString("idD"), superNode.getValueAsString("idD"));
        }

        if ( "nodeF".equals(nodeType)) {
            List<String> subNodes = node.getValueAsList(MemoryChainProcessor.subNodes);
            assertEquals(10,subNodes.size());
            subNodes.forEach( n-> {
                IEntity subNode = null;
                try {
                    subNode = nodeStore.read(n);
                } catch (StoreException e) {
                    fail(e.getMessage());
                }
                assertNotNull(subNode);
                assertEquals("nodeG", subNode.getType());
                assertEquals(node.getValueAsString("idF"), subNode.getValueAsString("idF"));
            });
            IEntity superNode = nodeStore.read(node.getValueAsString(MemoryChainProcessor.superNode));
            assertNotNull(superNode);
            assertEquals("nodeE", superNode.getType());
            assertEquals(node.getValueAsString("idE"), superNode.getValueAsString("idE"));
        }

        if ( "nodeG".equals(nodeType)) {
            List<String> subNodes = node.getValueAsList(MemoryChainProcessor.subNodes);
            assertNull(subNodes);
            IEntity superNode = nodeStore.read(node.getValueAsString(MemoryChainProcessor.superNode));
            assertNotNull(superNode);
            assertEquals(node.getValueAsString("idF"), superNode.getValueAsString("idF"));
        }

        // fail("Unknown node type "+nodeType);
    }


}
