package monevent.common.model.time;

import monevent.common.model.Entity;
import org.joda.time.DateTime;

import java.util.Map;

/**
 * Created by steph on 06/03/2016.
 */
public class JobExecution extends Timestamp {

    public static String fireId = "fireId";
    public static String fireTime = "fireTime";
    public static String status = "status";
    public static String processor = "processor";

    public JobExecution() {
        super("jobExecution");
    }

    public JobExecution(String name) {
        super(name, "jobExecution");
    }

    public JobExecution(Map data) {
        super(data);
    }

    public JobExecution(String name, Entity entity, String... fieldsToCopy) {
        super(name, "jobExecution", entity, fieldsToCopy);
    }

    public String getProcessor() {
        return getValueAsString(JobExecution.processor);
    }

    public void setProcessor(String processor) {
        setValue(JobExecution.processor, processor);
    }

    public String getFireId() {
        return getValueAsString(JobExecution.fireId);
    }

    public void setFireId(String fireId) {
        setValue(JobExecution.fireId, fireId);
    }

    public DateTime getFireTime() {
        return getValueAsDateTime(JobExecution.fireTime);
    }

    public void setFireTime(DateTime fireTime) {
        setValue(JobExecution.fireTime, fireTime);
    }

    public String getStatus() {
        return getValueAsString(JobExecution.status);
    }

    public void setStatus(String status) {
        setValue(JobExecution.status, status);
    }

}
