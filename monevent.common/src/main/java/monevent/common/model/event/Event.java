package monevent.common.model.event;


import monevent.common.model.Entity;

import java.util.Map;

public class Event extends Entity {

    protected static String index = "index";
    protected static String workflow = "workflow";
    protected static String activity = "activity";
    protected static String component = "component";
    protected static String host = "host";
    protected static String process = "process";
    protected static String thread = "threads";

    public Event(Map data) {
        super(data);
    }

    public Event() {
        super();
    }

    public Event(String type) {
        super(type);
    }

    public Event(String name, String type) {
        super(name, type);
    }

    /* Workflow */
    public String getWorkflow() {
        return getValueAsString(Event.workflow);
    }

    public void setWorkflow(String workflow) {
        setValue(Event.workflow, workflow);
    }

    /* Activity */
    public String getActivity() {
        return getValueAsString(Event.activity);
    }

    public void setActivity(String activity) {
        setValue(Event.activity, activity);
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

    /* Index */
    public String getIndex() {
        return getValueAsString(Event.index);
    }

    public void setIndex(String index) {
        setValue(Event.index, index);
    }

}