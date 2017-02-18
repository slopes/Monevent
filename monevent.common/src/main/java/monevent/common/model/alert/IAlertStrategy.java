package monevent.common.model.alert;

import monevent.common.managers.IManageable;

import java.util.List;


public interface IAlertStrategy extends IManageable{
    Alert analyze(List<Alert> alerts);
}
