package monevent.common.process.matching.memory;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import monevent.common.communication.EntityBusManager;
import monevent.common.model.IEntity;
import monevent.common.model.query.IQuery;
import monevent.common.process.matching.*;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;

/**
 * Created by slopes on 01/02/17.
 */
public class MemoryMatchingProcessor extends MatchingProcessorBase {

    protected static String cacheKey = "cache.key";

    private final Map<Matching, MemoryCacheKeyFactory> factories;
    private final Map<Matching, LoadingCache<IMemoryCacheKey, MatchingResult>> results;
    private final Map<Matching, IMatchingChecker> checkers;

    public MemoryMatchingProcessor(String name, IQuery query, List<Matching> matchingList, EntityBusManager entityBusManager, String resultBus) {
        super(name, query, matchingList, entityBusManager, resultBus);

        this.factories = new ConcurrentHashMap<>();
        matchingList.forEach(m -> this.factories.put(m, new MemoryCacheKeyFactory(m)));

        CacheLoader<IMemoryCacheKey, MatchingResult> loader = new CacheLoader<IMemoryCacheKey, MatchingResult>() {
            public MatchingResult load(IMemoryCacheKey key) throws Exception {
                return key.build();
            }
        };

        this.results = new ConcurrentHashMap<>();
        matchingList.forEach(m -> this.results.put(m, CacheBuilder.newBuilder().build(loader)));

        this.checkers = new ConcurrentHashMap<>();
        matchingList.forEach(m -> this.checkers.put(m, new ExpectedMatchChecker(m.getExpectedMatch())));
    }

    @Override
    protected boolean doCheck(Matching matching, MatchingResult result) {
        if (result == null) return false;
        IMatchingChecker checker = this.checkers.get(matching);
        if (checker == null) return false;

        if (checker.isComplete(result)) {
            IMemoryCacheKey key = (IMemoryCacheKey) result.getValue(MemoryMatchingProcessor.cacheKey);
            if (key != null) {
                LoadingCache<IMemoryCacheKey, MatchingResult> cache = this.results.get(matching);
                if (cache != null) {
                   // info("Invalidating cache key");
                    cache.invalidate(key);
                }
                //info("Removing cache key");
                result.remove(MemoryMatchingProcessor.cacheKey);

            }
            return true;
        }
        return false;

    }

    @Override
    protected MatchingResult doMatch(Matching matching, IEntity entity) throws ExecutionException {
        MemoryCacheKeyFactory factory = this.factories.get(matching);
        if (factory == null) return null;

        IMemoryCacheKey key = factory.buildKey(entity);
        if (key != null) {
            LoadingCache<IMemoryCacheKey, MatchingResult> cache = this.results.get(matching);
            MatchingResult result = cache.get(key);
            if (result != null) {
                result.setValue(MemoryMatchingProcessor.cacheKey, key);
            }
            return result;
        }
        return null;
    }

    @Override
    protected void doStart() {

    }

    @Override
    protected void doStop() {

    }
}
