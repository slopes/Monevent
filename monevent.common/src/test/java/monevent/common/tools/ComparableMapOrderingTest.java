package monevent.common.tools;

import monevent.common.model.Entity;
import monevent.common.model.IEntity;
import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Stephane on 19/11/2015.
 */
public class ComparableMapOrderingTest {

    @Test
    public void testCompare() throws Exception {
        List<IEntity> entities = new ArrayList<IEntity>();
        DateTime now =DateTime.now();
        IEntity entityNowMinus1 = new Entity().set("timestamp", now.minusHours(1));
        IEntity entityNow = new Entity().set("timestamp", now);
        IEntity entityPlus1 = new Entity().set("timestamp", now.plusHours(1));
        entities.add(entityPlus1);
        entities.add(entityNow);
        entities.add(entityNowMinus1);
        Collections.sort(entities);
        Assert.assertEquals(entityNowMinus1,entities.get(0));
        Assert.assertEquals(entityNow,entities.get(1));
        Assert.assertEquals(entityPlus1,entities.get(2));
    }
}