package monevent.common.model.configuration;

import com.eaio.uuid.UUID;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import monevent.common.model.IDistinguishable;

/**
 * Created by steph on 28/02/2016.
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY, property = "@class")
public class Configuration implements IDistinguishable {
    private String id;
    private String name;
    public Configuration() {
        this.id = new UUID().toString();
    }

    public Configuration(String name) {
        this();
        this.name = name;
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
        return this.id;
    }





}
