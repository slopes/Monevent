package monevent.common.process.matching.memory;

import monevent.common.process.matching.MatchingResult;

/**
 * Created by slopes on 01/02/17.
 */
public interface IMemoryCacheKey extends Comparable {
    MatchingResult build();

    void setValue(String field, Object value);
}
