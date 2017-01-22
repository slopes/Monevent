package monevent.common.model.alert;

import org.joda.time.DateTime;

/**
 * Created by Stephane on 26/12/2015.
 */
public class FirstAlertStrategy extends AlertStrategyBase {
    protected FirstAlertStrategy(String name) {
        super(name);
    }

    @Override
    protected Alert doAnalyze(Alert alert) {
        if (alert == null) return getAlert();
        if (alert.getTimestamp().isBefore(getAlert().getTimestamp())) {
            setAlert(new Alert(alert));
        }
        return getAlert();
    }

    @Override
    protected Alert buildAlert() {
        Alert alert = new Alert();
        alert.setTimestamp(new DateTime(Long.MAX_VALUE));
        return alert;
    }


}