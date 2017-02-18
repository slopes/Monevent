package monevent.common.process.matching.memory;

import monevent.common.model.IEntity;

/**
 * Created by slopes on 07/02/17.
 */
public interface IMemoryCacheKeyFactory {
    IMemoryCacheKey buildKey(IEntity entity);
}
