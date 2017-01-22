package monevent.common.model.alert;


import com.eaio.uuid.UUID;
import monevent.common.model.Entity;
import org.joda.time.DateTime;

import java.util.Map;

public class Alert extends Entity {

    public static String priority = "priority";
    public static String lastUpdate = "lastUpdate";
    public static String operator = "operator";
    public static String component = "component";
    public static String origin = "origin";
    public static String userMessage = "userMessage";
    public static String technicalMessage = "technicalMessage";
    public static String status = "status";
    public static String resolutionCause = "resolutionCause";
    public static String toBeClosed = "toBeClosed";
    public static String closingDate = "closingDate";


    public Alert() {
        super("alert");
    }

    public Alert(Map data) {
        super(data);
    }

    public Alert(AlertPriority priority, String message, Entity entity, String... fieldsToCopy) {
        super(entity.getName(),"alert",entity,fieldsToCopy);
        setPriority(priority);
        setUserMessage(message);
        setTechnicalMessage(message);
        setUserMessage(message);
        setStatus(AlertStatus.Open);
    }

    public Alert(AlertPriority priority, String name, String userMessage, String technicalMessage, String origin) {
        super(name, "alert");
        setPriority(priority);
        setUserMessage(userMessage);
        setTechnicalMessage(technicalMessage);
        setOrigin(origin);
        setStatus(AlertStatus.Open);
    }

    /* Priority */
    public AlertPriority getPriority() {
        return getValueAsEnum(Alert.priority, AlertPriority.class, AlertPriority.Undefined);
    }

    public void setPriority(AlertPriority priority) {
        setValue(Alert.priority, priority);
    }

    /* Last update */
    public DateTime getLastUpdate() {
        return getValueAsDateTime(Alert.lastUpdate);
    }

    public void setLastUpdate(DateTime lastUpdate) {
        setValue(Alert.lastUpdate, lastUpdate);
    }

    /* Operator */
    public UUID getOperator() {
        return getValueAsUUID(Alert.operator);
    }

    public void setOperator(UUID operator) {
        setValue(Alert.operator, operator);
    }

    /* Component */
    public String getComponent() {
        return getValueAsString(Alert.component);
    }

    public void setComponent(String component) {
        setValue(Alert.component, component);
    }

    /* Origin */
    public String getOrigin() {
        return getValueAsString(Alert.origin);
    }

    public void setOrigin(String origin) {
        put(Alert.origin, origin);
    }

    /* User message */
    public String getUserMessage() {
        return getValueAsString(Alert.userMessage);
    }

    public void setUserMessage(String userMessage) {
        setValue(Alert.userMessage, userMessage);
    }

    /* Technical message */
    public String getTechnicalMessage() {
        return getValueAsString(Alert.technicalMessage);
    }

    public void setTechnicalMessage(String technicalMessage) {
        setValue(Alert.technicalMessage, technicalMessage);
    }

    /* Status */
    public AlertStatus getStatus() {
        return getValueAsEnum(Alert.status, AlertStatus.class, AlertStatus.Undefined);
    }

    public void setStatus(AlertStatus status) {
        setValue(Alert.status, status);
    }

    /* Closing date */
    public DateTime getClosingDate() {
        return getValueAsDateTime(Alert.closingDate);
    }

    public void setClosingDate(DateTime closingDate) {
        setValue(Alert.closingDate, closingDate);
    }


    /* To be closed */
    public Boolean isToBeClosed() {
        return getValueAsBoolean(Alert.toBeClosed);
    }

    public void isToBeClosed(Boolean toBeClosed) {
        setValue(Alert.toBeClosed, toBeClosed);
    }


    /* Resolution cause */
    public String getResolutionCause() {
        return getValueAsString(Alert.resolutionCause);
    }

    public void setResolutionCause(String resolutionCause) {
        setValue(Alert.resolutionCause, resolutionCause);
    }

}
