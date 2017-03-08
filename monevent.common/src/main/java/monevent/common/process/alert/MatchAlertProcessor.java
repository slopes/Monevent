package monevent.common.process.alert;

import monevent.common.managers.ManageableBase;
import monevent.common.managers.Manager;
import monevent.common.model.Entity;
import monevent.common.model.IEntity;
import monevent.common.model.alert.Alert;
import monevent.common.model.alert.AlertPriority;
import monevent.common.model.alert.AlertStatus;
import monevent.common.model.query.IQuery;

import java.util.concurrent.TimeUnit;

/**
 * Created by Stephane on 21/11/2015.
 */
public class MatchAlertProcessor extends AlertProcessorBase {
    private final String userMessage;
    private final AlertPriority priority;

    public MatchAlertProcessor(String name,
                               IQuery query,
                               Manager manager,
                               String alertBus,
                               String userMessage,
                               int closeAfterDelay,
                               TimeUnit closeAfterDelayTimeUnitString,
                               AlertPriority priority) {
        super(name, query, manager, alertBus, userMessage, closeAfterDelay, closeAfterDelayTimeUnitString);
        this.userMessage = userMessage;
        this.priority = priority;
    }

    @Override
    protected Alert doAlert(IEntity entity) {
        Alert alert = new Alert();
        alert.setPriority(this.priority);
        alert.setComponent(ManageableBase.getComponent());
        alert.setLastUpdate(alert.getTimestamp());
        alert.setStatus(AlertStatus.Open);
        alert.setUserMessage(this.userMessage);
        alert.setTechnicalMessage(trace("The entity %s matches the query of alert processor %s", Entity.getEntityId(entity), getName()));
        alert.setOrigin(getName());
        return alert;
    }

    @Override
    protected void doStart() {

    }

    @Override
    protected void doStop() {

    }
}
