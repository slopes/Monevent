package monevent.common.managers.extension;

import monevent.common.managers.IManageable;
import ro.fortsoft.pf4j.ExtensionPoint;

/**
 * Created by steph on 09/01/2016.
 */
public interface IManageableFactory extends IManageable, ExtensionPoint {

    IManageable build();

}