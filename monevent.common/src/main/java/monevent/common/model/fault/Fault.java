package monevent.common.model.fault;

import monevent.common.model.Entity;

import java.util.Map;


public class Fault extends Entity {

    private static String message = "message";
    private static String stackTrace = "stackTrace";
    private static String component = "component";
    private static String host = "host";
    public Fault() {
        super("fault");
    }

    public Fault(Map data) {
        super(data);
    }

    public Fault(String name) {
        super(name, "fault");
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
}

