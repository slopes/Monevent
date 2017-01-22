package monevent.common.model.alert;

import org.joda.time.DateTime;

/**
 * Created by Stephane on 26/12/2015.
 */
public class LastAlertStrategy extends AlertStrategyBase {
    protected LastAlertStrategy(String name) {
        super(name);
    }

    @Override
    protected Alert doAnalyze(Alert alert) {
        if (alert == null) return getAlert();
        if (alert.getTimestamp().isAfter(getAlert().getTimestamp())) {
            setAlert(new Alert(alert));
        }
        return getAlert();
    }

    @Override
    protected Alert buildAlert() {
        Alert alert = new Alert();
        alert.setTimestamp(new DateTime(0L));
        return alert;
    }


}
