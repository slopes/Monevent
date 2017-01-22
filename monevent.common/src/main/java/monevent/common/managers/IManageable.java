package monevent.common.managers;

import monevent.common.process.ProcessorException;

/**
 * Created by Stephane on 20/11/2015.
 */

public interface IManageable {
    String getName();
    void start() throws ProcessorException;
    void stop() throws ProcessorException;
}
