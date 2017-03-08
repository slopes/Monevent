package monevent.common.model.fault;

import monevent.common.model.Entity;
import monevent.common.model.event.Event;

import java.util.Map;


public class Fault extends Entity {

    private static String message = "message";
    private static String stackTrace = "stackTrace";
    private static String component = "component";
    private static String host = "host";
    private static String name = "name";
    public Fault() {
        super("fault");
    }

    public Fault(Map data) {
        super(data);
    }

    public Fault(String name) {
        super("fault");
        setName(name);
    }

    public String getMessage() {
        return getValueAsString(Fault.message);
    }

    public void setMessage(String message) {
        setValue(Fault.message, message);
    }

    public String getStackTrace() {
        return getValueAsString(Fault.stackTrace);
    }

    public void setStackTrace(String stackTrace) {
        setValue(Fault.stackTrace, stackTrace);
    }

    public String getComponent() {
        return getValueAsString(Fault.component);
    }

    public void setComponent(String component) {
        setValue(Fault.component, component);
    }

    public String getHost() {
        return getValueAsString(Fault.host);
    }

    public void setHost(String host) {
        setValue(Fault.host, host);
    }

    public String getName() {
        return getValueAsString(Fault.name);
    }

    public void setName(String name) {
        setValue(Fault.name, name);
    }
}

