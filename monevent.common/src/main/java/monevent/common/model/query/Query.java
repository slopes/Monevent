package monevent.common.model.query;

import com.eaio.uuid.UUID;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import monevent.common.model.IEntity;
import monevent.common.tools.OptionalObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Stephane on 20/11/2015.
 */
@JsonDeserialize(builder = Query.Deserializer.class)
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY, property = "@class")
public class Query implements IQuery {

    private QueryOrder sortOrder;
    private String sortField;
    private String name;
    private String id;
    private List<QueryCriterion> criteria;

    public Query() {
        setId(new UUID().toString());
        setCriteria(new ArrayList<>());
    }

    public Query(String name) {
        setId(new UUID().toString());
        setName(name);
        setCriteria(new ArrayList<>());
    }

    public Query(Query query) {
        setId(query.getId());
        setName(query.getName());
        setSortField(query.getSortField());
        setSortOrder(query.getSortOrder());
        setCriteria(query.getCriteria());
    }

    @Override
    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public QueryOrder getSortOrder() {
        return this.sortOrder;
    }

    public void setSortOrder(QueryOrder sortOrder) {
        this.sortOrder = sortOrder;
    }

    public String getSortField() {
        return this.sortField;
    }

    public void setSortField(String sortField) {
        this.sortField = sortField;
    }

    public List<QueryCriterion> getCriteria() {
        return this.criteria;
    }

    public void setCriteria(List<QueryCriterion> criteria) {
        this.criteria = new ArrayList<>(criteria);
    }

    public Query addCriterion(List<QueryCriterion> criteria) {
        getCriteria().addAll(criteria);
        return this;
    }

    public Query addCriterion(QueryCriterion... criterions) {
        for (QueryCriterion criterion : criterions) {
            getCriteria().add(criterion);
        }
        return this;
    }

    public Query addCriterion(String field, Comparable value, QueryCriterionType type) {
        return addCriterion(new QueryCriterion(field, value, type));
    }


    @Override
    public boolean match(IEntity entity) {
        if (entity != null) {
            for (QueryCriterion criterion : getCriteria()) {
                if (!criterion.match(entity)) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }



    public static IQuery AND(IQuery leftQuery,IQuery rightQuery) {
        return new AndQueryOperator(leftQuery,rightQuery);
    }

    public static IQuery OR(IQuery leftQuery,IQuery rightQuery) {
        return new OrQueryOperator(leftQuery,rightQuery);
    }

    public static IQuery NOT(IQuery query) {
        return new NotQueryOperator(query);
    }


    @JsonPOJOBuilder(buildMethodName = "build", withPrefix = "add")
    public static class Deserializer {

        private Query query;

        public Deserializer() {
        }

        public Deserializer(@JsonProperty("id") String id,
                            @JsonProperty("name") String name,
                            @JsonProperty("sortOrder") String sortOrder,
                            @JsonProperty("sortField") String sortField) {
            this.query = new Query();
            query.setId(id);
            query.setName(name);
            query.setSortField(sortField);
            query.setSortOrder(OptionalObject.enumValue(sortOrder, QueryOrder.class, QueryOrder.None));
        }

        public Deserializer(Query query) {
            this.query = new Query(query);
        }

        public Deserializer addCriteria(List<Map> criteria) {
            if (criteria != null) {
                criteria.stream().forEach( c-> {
                    String field = c.get("field").toString();
                    Comparable value = (Comparable) c.get("value");
                    QueryCriterionType type = OptionalObject.enumValue(c.get("type"), QueryCriterionType.class, QueryCriterionType.None);
                    QueryCriterion criterion=  new QueryCriterion(field,value,type);
                    query.addCriterion(criterion);
                });
            }
            return this;
        }

        public Query build() {
            return this.query;
        }
    }
}

