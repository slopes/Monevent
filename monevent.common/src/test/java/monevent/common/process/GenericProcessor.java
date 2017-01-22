package monevent.common.process;

import monevent.common.model.IEntity;
import org.junit.Assert;

import java.util.function.Predicate;

/**
 * Created by slopes on 15/01/17.
 */
public class GenericProcessor extends ProcessorBase{

    private final Predicate<IEntity> predicate;
    protected GenericProcessor(String name, Predicate<IEntity> predicate) {
        super(name, null);
        this.predicate = predicate;
    }

    @Override
    protected IEntity doProcess(IEntity entity) throws Exception {
        Assert.assertTrue(this.predicate.test(entity));
        return entity;
    }

    @Override
    protected void doStart() {

    }

    @Override
    protected void doStop() {

    }
}
