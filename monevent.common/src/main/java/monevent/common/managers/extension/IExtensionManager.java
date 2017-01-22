package monevent.common.managers.extension;

/**
 * Created by steph on 09/01/2016.
 */

import monevent.common.managers.IManageable;

import java.util.List;

public interface IExtensionManager extends IManageable {

    <T extends IManageableFactory> List<T> getExtensions(Class extensionClass);

}
