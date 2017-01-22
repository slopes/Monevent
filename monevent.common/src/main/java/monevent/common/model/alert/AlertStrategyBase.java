package monevent.common.model.alert;

import monevent.common.managers.ManageableBase;
import monevent.common.process.ProcessorException;

/**
 * Created by Stephane on 26/12/2015.
 */
public abstract class AlertStrategyBase extends ManageableBase implements IAlertStrategy{
    private Alert alert;

    protected AlertStrategyBase(String name) {
        super(name);

    }

    @Override
    public Alert analyze(Alert alert) {
        debug("Processing %d alert...", alert.getName());
        try {
            return doAnalyze(alert);
        } catch (Exception error) {
            error("Cannot process %d alert.", error,  alert.getName());
            throw new ProcessorException(trace("Cannot process %d alert.",  alert.getName()), error);
        } finally {
            debug("... %d alert processed", alert.getName());
        }
    }

    protected abstract Alert doAnalyze(Alert alert);

    @Override
    public Alert getAlert() {
        return alert;
    }

    protected void setAlert(Alert alert) {
        this.alert = alert;
    }

    @Override
    protected void doStart() {
        alert = buildAlert();
    }

    protected abstract Alert buildAlert();

    @Override
    protected void doStop() {
        ManageableBase.log(alert);
    }


}
