package monevent.common.tools;

import com.eaio.uuid.UUID;
import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.assertEquals;


/**
 * Created by Stephane on 19/11/2015.
 */
public class SafeMapTest {


    @Test
    public void testGetValueAsDateTime() throws Exception {
        DateTime aDate = DateTime.now();
        SafeMap map = new SafeMap();
        map.setValue("aDate", aDate);
        assertEquals(aDate, map.getValueAsDateTime("aDate"));
    }

    @Test
    public void testGetValueAsBoolean() throws Exception {
        boolean aBoolean = true;
        SafeMap map = new SafeMap();
        map.setValue("aBoolean", aBoolean);
        assertEquals(aBoolean, map.getValueAsBoolean("aBoolean"));
    }

    @Test
    public void testGetValueAsString() throws Exception {
        String aString = "sfd";
        SafeMap map = new SafeMap();
        map.setValue("aString", aString);
        assertEquals(aString, map.getValueAsString("aString"));
    }

    @Test
    public void testGetValueAsLong() throws Exception {
        Long aLong = 3L;
        SafeMap map = new SafeMap();
        map.setValue("aLong", aLong);
        assertEquals(aLong, map.getValueAsLong("aLong"));
    }

    @Test
    public void testGetValueAsInteger() throws Exception {
        Integer anInteger = 3;
        SafeMap map = new SafeMap();
        map.setValue("anInteger", anInteger);
        assertEquals(anInteger, map.getValueAsInteger("anInteger"));
    }

    @Test
    public void testGetValueAsDouble() throws Exception {
        Double aDouble = 3.0;
        SafeMap map = new SafeMap(1,1);
        map.setValue("aDouble", aDouble);
        assertEquals(aDouble, map.getValueAsDouble("aDouble"));
    }

    @Test
    public void testGetValueAsUUID() throws Exception {
        UUID aUUID = new UUID();
        SafeMap map = new SafeMap(1);
        map.setValue("aUUID", aUUID);
        assertEquals(aUUID, map.getValueAsUUID("aUUID"));
    }

    @Test
    public void testGetValueAsMap() throws Exception {
        SafeMap map = new SafeMap(1);
        map.setValue("aMap.value", 12);
        assertEquals(SafeMap.class, map.getValueAsMap("aMap").getClass());
    }
}