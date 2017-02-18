package monevent.common.model.alert;

import org.joda.time.DateTime;

import java.util.List;


public class LastAlertStrategy extends AlertStrategyBase {
    public LastAlertStrategy() {
        super("LastAlertStrategy");
    }

    @Override
    protected Alert doAnalyze(List<Alert> alerts) {
        if (alerts == null) return null;
        Alert alert = new Alert();
        alert.setTimestamp(DateTime.now().minusYears(100));
        for (Alert alertToAnalyze : alerts) {
            if ( alertToAnalyze == null) continue;
            if (alertToAnalyze.getTimestamp().isAfter(alert.getTimestamp())) {
                alert = alertToAnalyze;
            }
        }
        return alert;
    }


}
