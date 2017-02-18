package monevent.common.process.matching.memory;

import com.google.common.collect.Lists;
import monevent.common.model.Entity;
import monevent.common.model.IEntity;
import monevent.common.process.matching.Matching;
import org.joda.time.DateTime;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by slopes on 01/02/17.
 */
public class MatchingCacheKeyTest {

    @Test
    public void testEntity() {
        Matching matching = new Matching();
        matching.setType("basicEntityA");

        MemoryCacheKeyFactory factory = new MemoryCacheKeyFactory(matching);

        IMemoryCacheKey key = factory.buildKey(null);
        assertNotNull(key);
        assertEquals("basicEntityA", key.build().getType());

    }


    @Test
    public void testWithAllProperties() {
        Matching matching = new Matching();
        matching.setType("basicEntityB");
        matching.setFields(Lists.newArrayList("a"));

        MemoryCacheKeyFactory factory = new MemoryCacheKeyFactory(matching);

        IMemoryCacheKey key = factory.buildKey(new Entity("otherEntity").set("a",12));
        assertNotNull(key);

        IEntity entity = key.build();
        assertNotNull(entity);
        assertEquals("basicEntityB", entity.getType());
        assertEquals(12, (int) entity.getValueAsInteger("a"));
    }

    @Test
    public void testWithMissingProperties() {
        Matching matching = new Matching();
        matching.setType("basicEntityC");
        matching.setFields(Lists.newArrayList("a", "b", "c"));

        MemoryCacheKeyFactory factory = new MemoryCacheKeyFactory(matching);

        IMemoryCacheKey key = factory.buildKey(new Entity("otherEntity").set("a",12));
        assertNull(key);
    }

    @Test
    public void testEquals() {
        Matching matching = new Matching();
        matching.setType("basicEntityD");
        matching.setFields(Lists.newArrayList("a", "b", "c"));

        MemoryCacheKeyFactory factory = new MemoryCacheKeyFactory(matching);

        DateTime today = DateTime.now();
        DateTime yesterday = today.minusDays(1);
        IMemoryCacheKey keyA = factory.buildKey(null);
        assertNotNull(keyA);
        keyA.setValue("a", 12);
        keyA.setValue("b", false);
        keyA.setValue("c", "c");
        keyA.setValue("d", yesterday);

        IMemoryCacheKey keyB = factory.buildKey(null);
        assertNotNull(keyB);
        keyB.setValue("a", 12);
        keyB.setValue("b", false);
        keyB.setValue("c", "c");
        keyB.setValue("d", yesterday);

        IMemoryCacheKey keyC = factory.buildKey(null);
        assertNotNull(keyC);
        keyC.setValue("a", 21);
        keyC.setValue("b", false);
        keyC.setValue("c", today);

        assertEquals(keyA, keyB);
        assertNotEquals(keyA, keyC);
        assertNotEquals(keyB, keyC);
    }
}