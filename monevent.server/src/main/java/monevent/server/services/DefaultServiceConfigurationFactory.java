package monevent.server.services;

import monevent.server.services.configuration.ConfigurationService;
import monevent.server.services.configuration.ConfigurationServiceConfiguration;

/**
 * Created by slopes on 22/01/17.
 */
public class DefaultServiceConfigurationFactory implements IServiceConfigurationFactory {
    @Override
    public ServiceConfiguration build(String name) {

        if (name.equals(ConfigurationService.NAME)) {
            return new ConfigurationServiceConfiguration();
        }
        return null;
    }
}
