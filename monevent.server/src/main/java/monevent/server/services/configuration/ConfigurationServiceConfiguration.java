package monevent.server.services.configuration;

import io.dropwizard.setup.Environment;
import io.swagger.annotations.ApiModel;
import monevent.common.model.configuration.factory.IConfigurationFactory;
import monevent.server.services.IService;
import monevent.server.services.IServiceConfigurationFactory;
import monevent.server.services.ServiceConfiguration;

/**
 * Created by slopes on 22/01/17.
 */
@ApiModel
public class ConfigurationServiceConfiguration extends ServiceConfiguration {


    public ConfigurationServiceConfiguration() {
        super(ConfigurationService.NAME);
    }

    @Override
    public IService build(Environment environment, IServiceConfigurationFactory serviceConfigurationFactory) {
        return new ConfigurationService(environment, serviceConfigurationFactory);
    }
}
