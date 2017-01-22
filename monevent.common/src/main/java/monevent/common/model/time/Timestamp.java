package monevent.common.model.time;

import monevent.common.model.Entity;
import org.joda.time.DateTime;

import java.util.Map;

/**
 * Created by steph on 06/03/2016.
 */
public class Timestamp extends Entity {

    public Timestamp() {
        super("timestamp");
    }

    public Timestamp(String name) {
        super("timestamp");
        setName(name);
    }

    protected Timestamp(String name, String type) {
        super(name,type);
    }

    public Timestamp(Map data) {
        super(data);
    }

    public Timestamp(String name , Entity entity, String... fieldsToCopy) {
        super(name,"timestamp",entity);
    }

    protected Timestamp(String name ,String type, Entity entity, String... fieldsToCopy) {
        super(name,type,entity);
    }

}
