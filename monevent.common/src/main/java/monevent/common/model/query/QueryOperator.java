package monevent.common.model.query;

import com.eaio.uuid.UUID;

/**
 * Created by steph on 23/03/2016.
 */
public abstract class QueryOperator implements  IQuery {

    private String name;
    private String id;

    protected QueryOperator(String name) {
        this.name = name;
        this.id = new UUID().toString();
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getId() {
        return id;
    }
}
