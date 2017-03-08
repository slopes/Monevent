package monevent.common.model.configuration;

import com.eaio.uuid.UUID;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import monevent.common.managers.IManageable;
import monevent.common.managers.Manager;
import monevent.common.model.IDistinguishable;

/**
 * Created by steph on 28/02/2016.
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY, property = "@class")
public abstract class Configuration implements IDistinguishable {
    private String id;
    private String name;
    private String category;

    public Configuration() {
        this.id = new UUID().toString();
    }

    public Configuration(String name) {
        this();
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    protected void setCategory(String category) {
        this.category = category;
    }

    @Override
    public String getId() {
        return this.id;
    }

    public abstract <T extends IManageable> T build(Manager manager) throws ConfigurationException;

    public abstract void check() throws ConfigurationException;

}
