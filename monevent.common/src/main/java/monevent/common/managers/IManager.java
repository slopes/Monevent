package monevent.common.managers;

import com.google.common.collect.ImmutableMap;

/**
 * Created by steph on 12/03/2016.
 */
public interface IManager {
    <T extends IManageable> T get(String manageableFullName);
}
