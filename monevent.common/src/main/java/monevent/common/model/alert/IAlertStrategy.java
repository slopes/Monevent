package monevent.common.model.alert;

import monevent.common.managers.IManageable;

/**
 * Created by Stephane on 26/12/2015.
 */
public interface IAlertStrategy extends IManageable{
    Alert analyze(Alert alert);
    Alert getAlert();
}
