package monevent.common.model.alert;

import monevent.common.managers.ManageableBase;
import monevent.common.process.ProcessorException;

import java.util.List;

/**
 * Created by Stephane on 26/12/2015.
 */
public abstract class AlertStrategyBase extends ManageableBase implements IAlertStrategy {


    protected AlertStrategyBase(String name) {
        super(name);

    }

    @Override
    public Alert analyze(List<Alert> alerts) {
        debug("Start alert processing");
        try {
            return doAnalyze(alerts);
        } catch (Exception error) {
            throw new ProcessorException("Cannot process alerts.", error);
        } finally {
            debug("... alert processing stopped.");
        }
    }

    protected abstract Alert doAnalyze(List<Alert> alerts);


    @Override
    protected void doStart() {

    }

    @Override
    protected void doStop() {

    }


}
