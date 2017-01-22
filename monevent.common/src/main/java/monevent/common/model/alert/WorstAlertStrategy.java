package monevent.common.model.alert;

/**
 * Created by Stephane on 26/12/2015.
 */
public class WorstAlertStrategy extends AlertStrategyBase {
    protected WorstAlertStrategy(String name) {
        super(name);
    }

    @Override
    protected Alert doAnalyze(Alert alert) {
        if (alert == null) return getAlert();
        if (alert.getPriority().getValue() > getAlert().getPriority().getValue()) {
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
