package monevent.common.store;

import com.google.common.collect.Lists;
import monevent.common.model.Entity;
import monevent.common.model.IEntity;
import monevent.common.model.event.Event;
import monevent.common.model.query.Query;
import monevent.common.model.query.QueryCriterionType;
import monevent.common.model.store.StoreAction;
import monevent.common.model.store.StoreEvent;
import monevent.common.process.IProcessor;
import monevent.common.process.ProcessorException;
import monevent.common.process.store.StoreProcessor;
import org.junit.Assert;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by slopes on 18/02/17.
 */
public class StoreExceptionTest {

    @Test
    public void testCreateWithError() {
        String name = "memoryStore";

        IStore store = new CrashingStore("Does it really matter to have a name?");
        store.start();

        IProcessor processor = new StoreProcessor("storeProcessor",null, store);
        processor.start();

        try {

            for (int index = 0; index < 100; index++) {
                processor.process(new Event("Test").set("value", index));
            }
        }
        catch (ProcessorException error) {
            assertTrue(error.getMessage().contains("Cannot process entity"));
            assertNotNull(error.getCause());
            assertTrue(error.getCause() instanceof  StoreException);
            assertEquals("Cannot create entity",error.getCause().getMessage());
        }
        finally {
            processor.stop();
            store.start();
        }
    }

    @Test
    public void testUpdateWithError() {
        IStore store = new CrashingStore("Does it really matter to have a name?");
        store.start();

        IProcessor processor = new StoreProcessor("storeProcessor",null, store);
        processor.start();

        try {

            StoreEvent storeEvent = new StoreEvent(StoreAction.UPDATE,new Entity());
            processor.process(storeEvent);
        }
        catch (ProcessorException error) {
            assertTrue(error.getMessage().contains("Cannot process entity"));
            assertNotNull(error.getCause());
            assertTrue(error.getCause() instanceof  StoreException);
            assertEquals("java.lang.Exception: Cannot update entity",error.getCause().getMessage());
        }
        finally {
            processor.stop();
            store.start();
        }
    }

    @Test
    public void testUpdateListWithError() {
        IStore store = new CrashingStore("Does it really matter to have a name?");
        store.start();

        IProcessor processor = new StoreProcessor("storeProcessor",null, store);
        processor.start();

        try {

            StoreEvent storeEvent = new StoreEvent(StoreAction.UPDATE, Lists.newArrayList());
            processor.process(storeEvent);
        }
        catch (ProcessorException error) {
            assertTrue(error.getMessage().contains("Cannot process entity"));
            assertNotNull(error.getCause());
            assertTrue(error.getCause() instanceof  StoreException);
            assertEquals("Cannot update entity",error.getCause().getMessage());
        }
        finally {
            processor.stop();
            store.start();
        }
    }

}