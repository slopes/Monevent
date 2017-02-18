package monevent.common.process.chain;

import monevent.common.model.IEntity;
import monevent.common.process.matching.MatchingResult;

/**
 * Created by slopes on 04/02/17.
 */
public interface IChainingChecker {
    boolean isComplete(IEntity entity);
}
