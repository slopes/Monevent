package monevent.common.model.time;

import monevent.common.model.Entity;

import java.util.Map;

/**
 * Created by steph on 06/03/2016.
 */
public class Timestamp extends Entity {

    public Timestamp() {
        super("timestamp");
    }

    public Timestamp(Map data) {
        super(data);
    }

    public Timestamp(Entity entity, String... fieldsToCopy) {
        super("timestamp",entity,fieldsToCopy);
    }
    
}
