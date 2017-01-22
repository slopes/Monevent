package monevent.common.tools;

import com.eaio.uuid.UUID;
import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by Stephane on 19/11/2015.
 */
public class ComparableMapTest {

    @org.junit.Test
    public void testCompareToNotEquals() throws Exception {
        ComparableMap map = new ComparableMap();
        map.setValue("anInteger", 2);
        map.setValue("aString", "2");
        ComparableMap anotherMap = new ComparableMap();
        anotherMap.setValue("anInteger", 3);
        anotherMap.setValue("aString", "3");
        Assert.assertFalse(map.compareTo(anotherMap) == 0);
    }

    @org.junit.Test
    public void testCompareToEquals() throws Exception {
        ComparableMap map = new ComparableMap();
        map.setValue("anInteger", 2);
        map.setValue("aString", "2");
        map.setValue("aMap.value", "2");
        ComparableMap anotherMap = new ComparableMap();
        anotherMap.putAll(map);
        Assert.assertTrue(map.compareTo(anotherMap) == 0);
    }

    @org.junit.Test
    public void testCompareToNull() throws Exception {
        ComparableMap map = new ComparableMap();
        map.setValue("anInteger", 2);
        map.setValue("aString", "2");
        Assert.assertFalse(map.compareTo(null) == 0);
    }

    @org.junit.Test
    public void testCompareToValueNull() throws Exception {
        ComparableMap map = new ComparableMap();
        map.setValue("aMap.field.value", "2");
        ComparableMap anotherMap = new ComparableMap();
        anotherMap.setValue("aMap.field.value", null);
        Assert.assertFalse(map.compareTo(anotherMap) == 0);
    }

    @org.junit.Test
    public void testCompareToValueNotExist() throws Exception {
        ComparableMap map = new ComparableMap();
        map.setValue("anInteger", 2);
        map.setValue("aString", "2");
        ComparableMap anotherMap = new ComparableMap();
        anotherMap.setValue("anotherInteger", 3);
        anotherMap.setValue("aString", "2");
        Assert.assertFalse(map.compareTo(anotherMap) == 0);
    }

    @Test
    public void testGetValueAsDateTime() throws Exception {
        DateTime aDate = DateTime.now();
        ComparableMap map = new ComparableMap();
        map.setValue("aDate", aDate);
        Assert.assertEquals(aDate, map.getValueAsDateTime("aDate"));
    }

    @Test
    public void testGetValueAsBoolean() throws Exception {
        boolean aBoolean = true;
        ComparableMap map = new ComparableMap();
        map.setValue("aBoolean", aBoolean);
        Assert.assertEquals(aBoolean, map.getValueAsBoolean("aBoolean"));
    }

    @Test
    public void testGetValueAsString() throws Exception {
        String aString = "sfd";
        ComparableMap map = new ComparableMap();
        map.setValue("aString", aString);
        Assert.assertEquals(aString, map.getValueAsString("aString"));
    }

    @Test
    public void testGetValueAsLong() throws Exception {
        Long aLong = 3L;
        ComparableMap map = new ComparableMap();
        map.setValue("aLong", aLong);
        Assert.assertEquals(aLong, map.getValueAsLong("aLong"));
    }

    @Test
    public void testGetValueAsInteger() throws Exception {
        Integer anInteger = 3;
        ComparableMap map = new ComparableMap();
        map.setValue("anInteger", anInteger);
        Assert.assertEquals(anInteger, map.getValueAsInteger("anInteger"));
    }

    @Test
    public void testGetValueAsDouble() throws Exception {
        Double aDouble = 3.0;
        ComparableMap map = new ComparableMap(1,1);
        map.setValue("aDouble", aDouble);
        Assert.assertEquals(aDouble, map.getValueAsDouble("aDouble"));
    }

    @Test
    public void testGetValueAsUUID() throws Exception {
        UUID aUUID = new UUID();
        ComparableMap map = new ComparableMap(1);
        map.setValue("aUUID", aUUID);
        Assert.assertEquals(aUUID, map.getValueAsUUID("aUUID"));
    }

    @Test
    public void testGetValueAsMap() throws Exception {
        ComparableMap map = new ComparableMap(1);
        map.setValue("aMap.value", 12);
        Assert.assertEquals(ComparableMap.class, map.getValueAsMap("aMap").getClass());
    }
}