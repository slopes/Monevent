package monevent.common.managers;

/**
 * Created by Stephane on 20/11/2015.
 */

public interface IManageable {
    String getName();
    void start() throws ManageableException;
    void stop() throws ManageableException;
}
