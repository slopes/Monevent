package monevent.common.model.store;

import monevent.common.model.Entity;
import monevent.common.model.fault.Fault;

import java.util.Map;

/**
 * Created by slopes on 18/02/17.
 */
public class StoreEvent extends Entity {
    protected static String action = "action";
    protected static String data = "data";

    public StoreEvent() {
        super("storeEvent");
        setAction(StoreAction.CREATE);
    }

    public StoreEvent(StoreAction action, Object data) {
        setAction(action);
        setData(data);
    }

    public StoreEvent(Map other) {
        super(other);
    }


    public StoreAction getAction() {
        return getValueAsEnum(StoreEvent.action,StoreAction.class,StoreAction.CREATE);
    }

    public void setAction(StoreAction action) {
        setValue(StoreEvent.action, action);
    }

    public Object getData() {
        return getValue(StoreEvent.data);
    }

    public void setData(Object data) {
        setValue(StoreEvent.data, data);
    }


}
