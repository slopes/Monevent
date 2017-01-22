package monevent.common.model.query;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import monevent.common.model.IDistinguishable;
import monevent.common.model.IEntity;

import java.io.Serializable;

/**
 * Created by steph on 23/03/2016.
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY, property = "@class")
public interface IQuery extends IDistinguishable, Serializable {
    boolean match(IEntity entity);
}
