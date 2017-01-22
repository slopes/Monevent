package monevent.common.model.alert;

/**
 * Created by Stephane on 26/12/2015.
 */
public class LeastAlertStrategy extends AlertStrategyBase {
    protected LeastAlertStrategy(String name) {
        super(name);
    }

    @Override
    protected Alert doAnalyze(Alert alert) {
        if (alert == null) return getAlert();
        if (getAlert().getPriority() == AlertPriority.Undefined || alert.getPriority().getValue() < getAlert().getPriority().getValue()) {
            setAlert(new Alert(alert));
        }
        return getAlert();
    }

    @Override
    protected Alert buildAlert() {
        Alert alert = new Alert();
        alert.setPriority(AlertPriority.Undefined);
        return alert;
    }


}