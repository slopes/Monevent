package monevent.common.communication;

/**
 * Created by steph on 20/03/2016.
 */
public interface IEntityBusConfigurationFactory {
    EntityBusConfiguration build(String entityBusName);
}
