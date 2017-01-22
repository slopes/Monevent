package monevent.common.process.alert;

import monevent.common.communication.EntityBusManager;
import monevent.common.communication.IEntityBus;
import monevent.common.model.IEntity;
import monevent.common.model.alert.Alert;
import monevent.common.model.query.IQuery;
import monevent.common.process.ProcessorBase;

import java.util.concurrent.TimeUnit;

/**
 * Created by steph on 23/03/2016.
 */
public abstract class AlertProcessorBase extends ProcessorBase {
    private final EntityBusManager entityBusManager;
    private final String alertBus;
    private final String userMessage;
    private final int closeAfterDelay;
    private final TimeUnit closeAfterDelayTimeUnit;


    protected AlertProcessorBase(String name, IQuery query, EntityBusManager entityBusManager, String alertBus, String userMessage, int closeAfterDelay, TimeUnit closeAfterDelayTimeUnit) {
        super(name, query);
        this.userMessage = userMessage;
        this.closeAfterDelay = closeAfterDelay;
        this.closeAfterDelayTimeUnit = closeAfterDelayTimeUnit;
        this.entityBusManager = entityBusManager;
        this.alertBus = alertBus;
    }

    @Override
    protected IEntity doProcess(IEntity entity) throws Exception {
        Alert alert = doAlert(entity);

        if (alert != null) {
            alert.setUserMessage(userMessage);
            if (this.closeAfterDelay > 0) {
                switch (this.closeAfterDelayTimeUnit) {
                    case MINUTES:
                        alert.setClosingDate(alert.getTimestamp().plusMinutes(this.closeAfterDelay));
                        break;
                    case HOURS:
                        alert.setClosingDate(alert.getTimestamp().plusHours(this.closeAfterDelay));
                        break;
                    case DAYS:
                        alert.setClosingDate(alert.getTimestamp().plusDays(this.closeAfterDelay));
                        break;
                }
                alert.isToBeClosed(true);
            }

            if (alertBus != null && entityBusManager != null) {
                IEntityBus entityBus = entityBusManager.load(alertBus);
                if (entityBus != null) {
                    entityBus.publish(alert);
                }
            }
        }
        return alert;
    }

    protected abstract Alert doAlert(IEntity entity);

    @Override
    protected void doStart() {

    }

    @Override
    protected void doStop() {

    }
}
