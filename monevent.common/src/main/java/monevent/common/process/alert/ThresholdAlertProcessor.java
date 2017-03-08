package monevent.common.process.alert;

import monevent.common.managers.Manager;
import monevent.common.model.IEntity;
import monevent.common.model.alert.Alert;
import monevent.common.model.alert.AlertPriority;
import monevent.common.model.alert.AlertStatus;
import monevent.common.model.query.IQuery;

import java.util.concurrent.TimeUnit;

/**
 * Created by Stephane on 28/12/2015.
 */
public class ThresholdAlertProcessor extends AlertProcessorBase {
    private double fatalThreshold;
    private double criticalThreshold;
    private double mediumThreshold;
    private double lowThreshold;
    private double infoThreshold;
    private String valueField;


    public ThresholdAlertProcessor(String name,
                                   IQuery query,
                                   Manager manager,
                                   String alertBus,
                                   String userMessage,
                                   int closeAfterDelay,
                                   TimeUnit closeAfterDelayTimeUnitString,
                                   double fatalThreshold,
                                   double criticalThreshold,
                                   double mediumThreshold,
                                   double lowThreshold,
                                   double infoThreshold,
                                   String valueField) {
        super(name, query, manager, alertBus, userMessage, closeAfterDelay, closeAfterDelayTimeUnitString);
        this.fatalThreshold = fatalThreshold;
        this.criticalThreshold = criticalThreshold;
        this.mediumThreshold = mediumThreshold;
        this.lowThreshold = lowThreshold;
        this.infoThreshold = infoThreshold;
        this.valueField = valueField;
    }

    @Override
    protected Alert doAlert(IEntity entity) {
        Alert alert = null;
        if (entity.contains(valueField)) {
            double value = compute(entity);
            if (value > fatalThreshold && alert == null) {
                alert = buildAlert(value, fatalThreshold, AlertPriority.Fatal);
            }
            if (value > criticalThreshold && alert == null) {
                alert = buildAlert(value, criticalThreshold, AlertPriority.Critical);
            }
            if (value > mediumThreshold && alert == null) {
                alert = buildAlert(value, mediumThreshold, AlertPriority.Medium);
            }
            if (value > lowThreshold && alert == null) {
                alert = buildAlert(value, lowThreshold, AlertPriority.Low);
            }
            if (value > infoThreshold && alert == null) {
                alert = buildAlert(value, infoThreshold, AlertPriority.Info);
            }
        }
        return alert;
    }

    private Alert buildAlert(double value, double threshold, AlertPriority priority) {
        Alert alert = new Alert();
        alert.setPriority(priority);
        alert.setStatus(AlertStatus.Open);
        alert.setTechnicalMessage(String.format("The current value %.2f is higher than the threshold %.2f", value, threshold));
        return alert;
    }

    private double compute(IEntity entity) {
        return entity.getValueAsDouble(valueField);
    }


    @Override
    protected void doStart() {

    }

    @Override
    protected void doStop() {

    }

}
