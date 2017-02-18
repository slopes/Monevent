package monevent.common.model.alert;

import java.util.List;


public class WorstAlertStrategy extends AlertStrategyBase {
    public WorstAlertStrategy() {
        super("WorstAlertStrategy");
    }

    @Override
    protected Alert doAnalyze(List<Alert> alerts) {
        if (alerts == null) return null;
        Alert alert = new Alert();
        alert.setPriority(AlertPriority.Undefined);
        for (Alert alertToAnalyze : alerts) {
            if ( alertToAnalyze == null) continue;
            if (alertToAnalyze.getPriority().getValue() >= alert.getPriority().getValue()) {
                alert = alertToAnalyze;
            }
        }
        return alert;
    }


}
