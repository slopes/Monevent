package monevent.common.model.alert;

import org.joda.time.DateTime;

import java.util.List;


public class LeastAlertStrategy extends AlertStrategyBase {
    public LeastAlertStrategy() {
        super("LeastAlertStrategy");
    }

    @Override
    protected Alert doAnalyze(List<Alert> alerts) {
        if (alerts == null) return null;
        Alert alert = new Alert();
        alert.setPriority(AlertPriority.Fatal);
        for (Alert alertToAnalyze : alerts) {
            if ( alertToAnalyze == null) continue;
            if (alertToAnalyze.getPriority().getValue() <= alert.getPriority().getValue()) {
                alert = alertToAnalyze;
            }
        }
        return alert;
    }


}