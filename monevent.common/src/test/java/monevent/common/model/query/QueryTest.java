package monevent.common.model.query;

import monevent.common.model.Entity;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by steph on 23/03/2016.
 */
public class QueryTest {

    @Test
    public void testAND() throws Exception {
        Entity entity = new Entity("test");
        entity.setValue("a", 10);
        entity.setValue("b", 100);

        IQuery leftQuery = new Query().addCriterion("a", 1, QueryCriterionType.GreaterThan);
        IQuery rightQuery = new Query().addCriterion("b", 10, QueryCriterionType.GreaterThan);

        boolean result = Query.AND(leftQuery, rightQuery).match(entity);
        Assert.assertTrue(result);

        leftQuery = new Query().addCriterion("a", 100, QueryCriterionType.GreaterThan);
        rightQuery = new Query().addCriterion("b", 10, QueryCriterionType.GreaterThan);

        result = Query.AND(leftQuery, rightQuery).match(entity);
        Assert.assertFalse(result);

        leftQuery = new Query().addCriterion("a", 1, QueryCriterionType.GreaterThan);
        rightQuery = new Query().addCriterion("b", 1000, QueryCriterionType.GreaterThan);

        result = Query.AND(leftQuery, rightQuery).match(entity);
        Assert.assertFalse(result);

        leftQuery = new Query().addCriterion("a", 100, QueryCriterionType.GreaterThan);
        rightQuery = new Query().addCriterion("b", 1000, QueryCriterionType.GreaterThan);

        result = Query.AND(leftQuery, rightQuery).match(entity);
        Assert.assertFalse(result);
    }

    @Test
    public void testOR() throws Exception {
        Entity entity = new Entity("test");
        entity.setValue("a", 10);
        entity.setValue("b", 100);

        IQuery leftQuery = new Query().addCriterion("a", 1, QueryCriterionType.GreaterThan);
        IQuery rightQuery = new Query().addCriterion("b", 10, QueryCriterionType.GreaterThan);

        boolean result = Query.OR(leftQuery, rightQuery).match(entity);
        Assert.assertTrue(result);

        leftQuery = new Query().addCriterion("a", 100, QueryCriterionType.GreaterThan);
        rightQuery = new Query().addCriterion("b", 10, QueryCriterionType.GreaterThan);

        result = Query.OR(leftQuery, rightQuery).match(entity);
        Assert.assertTrue(result);

        leftQuery = new Query().addCriterion("a", 1, QueryCriterionType.GreaterThan);
        rightQuery = new Query().addCriterion("b", 1000, QueryCriterionType.GreaterThan);

        result = Query.OR(leftQuery, rightQuery).match(entity);
        Assert.assertTrue(result);

        leftQuery = new Query().addCriterion("a", 100, QueryCriterionType.GreaterThan);
        rightQuery = new Query().addCriterion("b", 1000, QueryCriterionType.GreaterThan);

        result = Query.OR(leftQuery, rightQuery).match(entity);
        Assert.assertFalse(result);
    }

    @Test
    public void testNOT() throws Exception {
        Entity entity = new Entity("test");
        entity.setValue("a", 10);
        entity.setValue("b", 100);

        IQuery query = new Query().addCriterion("a", 1, QueryCriterionType.GreaterThan);

        boolean result = Query.NOT(query).match(entity);
        Assert.assertFalse(result);

        query = new Query().addCriterion("a", 100, QueryCriterionType.GreaterThan);

        result = Query.NOT(query).match(entity);
        Assert.assertTrue(result);
    }
}