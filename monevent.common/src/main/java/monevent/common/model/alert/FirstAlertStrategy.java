package monevent.common.model.alert;

import org.joda.time.DateTime;

import java.util.List;


public class FirstAlertStrategy extends AlertStrategyBase {
    public FirstAlertStrategy() {
        super("FirstAlertStrategy");
    }


    @Override
    protected Alert doAnalyze(List<Alert> alerts) {
        if (alerts == null) return null;
        Alert alert = new Alert();
        alert.setTimestamp(DateTime.now().plusYears(100));
        for (Alert alertToAnalyze : alerts) {
            if ( alertToAnalyze == null) continue;
            if (alertToAnalyze.getTimestamp().isBefore(alert.getTimestamp())) {
                alert = alertToAnalyze;
            }
        }
        return alert;
    }


}