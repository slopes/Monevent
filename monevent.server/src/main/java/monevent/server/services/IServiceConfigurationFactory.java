package monevent.server.services;

/**
 * Created by slopes on 22/01/17.
 */
public interface IServiceConfigurationFactory {
    ServiceConfiguration build(String name);
}
