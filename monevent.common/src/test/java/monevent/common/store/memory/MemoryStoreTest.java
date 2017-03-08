package monevent.common.store.memory;

import com.google.common.collect.Lists;
import monevent.common.model.Entity;
import monevent.common.model.IEntity;
import monevent.common.model.configuration.ConfigurationTest;
import monevent.common.model.query.IQuery;
import monevent.common.model.query.Query;
import monevent.common.model.query.QueryCriterionType;
import monevent.common.model.store.StoreAction;
import monevent.common.model.store.StoreEvent;
import monevent.common.process.IProcessor;
import monevent.common.process.ProcessorException;
import monevent.common.process.store.StoreProcessor;
import monevent.common.store.CrashingStore;
import monevent.common.store.IStore;
import monevent.common.store.StoreConfiguration;
import monevent.common.store.StoreException;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by steph on 13/03/2016.
 */
public class MemoryStoreTest extends ConfigurationTest {

    @Test
    public void testConfiguration() {
        String name = "memoryStore";

        StoreConfiguration configurationWrite = new MemoryStoreConfiguration(name);
        File file = new File("src/test/resources/config/store/"+name+".json");
        try {
            write(file, configurationWrite);
            MemoryStoreConfiguration configurationRead = (MemoryStoreConfiguration) read(file);
            Assert.assertEquals(name, configurationRead.getName());
        } finally {
            file.delete();
        }
    }

    @Test
    public void testCreateEntity() throws StoreException {
        IStore store = new MemoryStore("MemoryStore");
        store.start();
        store.create(new Entity("B"));

        IProcessor processor = new StoreProcessor("storeProcessor",null, store);
        processor.start();

        try {

            StoreEvent storeEvent = new StoreEvent(StoreAction.CREATE, new Entity("A"));
            processor.process(storeEvent);
            IQuery query = new Query().addCriterion(Entity.type,"A", QueryCriterionType.Is);
            List<IEntity> entities = store.read(query);
            assertNotNull(entities);
            assertEquals(1,entities.size());
        }
        finally {
            processor.stop();
            store.start();
        }
    }

    @Test
    public void testCreateEntityList() throws StoreException {
        IStore store = new MemoryStore("MemoryStore");
        store.start();
        store.create(new Entity("B"));

        IProcessor processor = new StoreProcessor("storeProcessor",null, store);
        processor.start();

        try {
            StoreEvent storeEvent = new StoreEvent(StoreAction.CREATE, Lists.newArrayList( new Entity("A"),new Entity("A") ));
            processor.process(storeEvent);
            IQuery query = new Query().addCriterion(Entity.type,"A", QueryCriterionType.Is);
            List<IEntity> entities = store.read(query);
            assertNotNull(entities);
            assertEquals(2,entities.size());
        }
        finally {
            processor.stop();
            store.start();
        }

    }

    @Test
    public void testUpdateEntity() throws StoreException {
        IStore store = new MemoryStore("MemoryStore");
        store.start();
        IEntity entity = new Entity("A");
        store.create(entity);

        IProcessor processor = new StoreProcessor("storeProcessor",null, store);
        processor.start();

        try {
            entity.setValue("a",12);
            StoreEvent storeEvent = new StoreEvent(StoreAction.UPDATE, entity);
            processor.process(storeEvent);
            IQuery query = new Query().addCriterion(Entity.type,"A", QueryCriterionType.Is);
            List<IEntity> entities = store.read(query);
            assertNotNull(entities);
            assertEquals(12,(int) entities.get(0).getValueAsInteger("a"));
        }
        finally {
            processor.stop();
            store.start();
        }

    }

    @Test
    public void testUpdateEntityList() throws StoreException {
        IStore store = new MemoryStore("MemoryStore");
        store.start();
        IEntity entity1 = new Entity("A");
        store.create(entity1);

        IEntity entity2 = new Entity("A");
        store.create(entity2);

        IProcessor processor = new StoreProcessor("storeProcessor",null, store);
        processor.start();

        try {
            entity1.setValue("a",21);
            entity2.setValue("a",12);
            StoreEvent storeEvent = new StoreEvent(StoreAction.UPDATE, Lists.newArrayList(entity1,entity2));
            processor.process(storeEvent);
            assertEquals(21,(int) store.read(entity1.getId()).getValueAsInteger("a"));
            assertEquals(12,(int) store.read(entity2.getId()).getValueAsInteger("a"));
        }
        finally {
            processor.stop();
            store.start();
        }
    }

    @Test
    public void testDeleteEntity() throws StoreException {
        IStore store = new MemoryStore("MemoryStore");
        store.start();
        IEntity entity1 = new Entity("A");
        store.create(entity1);

        IEntity entity2 = new Entity("A");
        store.create(entity2);

        IProcessor processor = new StoreProcessor("storeProcessor",null, store);
        processor.start();

        try {
            StoreEvent storeEvent = new StoreEvent(StoreAction.DELETE, entity1.getId());
            processor.process(storeEvent);
            assertTrue(null== store.read(entity1.getId()));
            assertNotNull(store.read(entity2.getId()));
        }
        finally {
            processor.stop();
            store.start();
        }
    }

    @Test
    public void testDeleteByQuery() throws StoreException {
        IStore store = new MemoryStore("MemoryStore");
        store.start();
        IEntity entity1 = new Entity("A");
        store.create(entity1);

        IEntity entity2 = new Entity("A");
        store.create(entity2);

        IEntity entity3 = new Entity("B");
        store.create(entity3);

        IProcessor processor = new StoreProcessor("storeProcessor",null, store);
        processor.start();

        try {
            IQuery query = new Query().addCriterion(Entity.type,"A", QueryCriterionType.Is);
            StoreEvent storeEvent = new StoreEvent(StoreAction.DELETE, query);
            processor.process(storeEvent);
            assertTrue(null== store.read(entity1.getId()));
            assertTrue(null== store.read(entity2.getId()));
            assertNotNull(store.read(entity3.getId()));
        }
        finally {
            processor.stop();
            store.start();
        }
    }

}