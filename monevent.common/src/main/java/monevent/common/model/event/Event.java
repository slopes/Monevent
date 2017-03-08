package monevent.common.model.event;


import monevent.common.model.Entity;

import java.util.Map;

public class Event extends Entity {

    protected static String component = "component";
    protected static String host = "host";
    protected static String process = "process";
    protected static String thread = "threads";
    protected static String name = "name";


    public Event(Map data) {
        super(data);
    }

    public Event() {
        super("event");
    }

    public Event(String name) {
        super(type);
        setName(name);
    }


    /* Host */
    public String getHost() {
        return getValueAsString(Event.host);
    }

    public void setHost(String host) {
        setValue(Event.host, host);
    }

    /* Process */
    public int getProcess() {
        return getValueAsInteger(Event.process);
    }

    public void setProcess(int process) {
        setValue(Event.process, process);
    }

    /* Thread */
    public int getThread() {
        return getValueAsInteger(Event.thread);
    }

    public void setThread(int thread) {
        setValue(Event.thread, thread);
    }

    /* Component */
    public String getComponent() {
        return getValueAsString(Event.component);
    }

    public void setComponent(String component) {
        setValue(Event.component, component);
    }

    public String getName() {
        return getValueAsString(Event.name);
    }

    public void setName(String name) {
        setValue(Event.name, name);
    }

}