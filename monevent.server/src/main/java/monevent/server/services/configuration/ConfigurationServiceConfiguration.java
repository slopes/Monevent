package monevent.server.services.configuration;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import monevent.common.managers.Manager;
import monevent.common.managers.configuration.ConfigurationManager;
import monevent.common.model.configuration.ConfigurationException;
import monevent.server.services.ServiceConfiguration;

/**
 * Created by slopes on 22/01/17.
 */
@ApiModel
public class ConfigurationServiceConfiguration extends ServiceConfiguration {

    @JsonIgnore
    private final ConfigurationManager configurationManager;

    public ConfigurationServiceConfiguration(ConfigurationManager configurationManager) {
        super(ConfigurationService.NAME);
        this.configurationManager = configurationManager;
    }

    @Override
    public ConfigurationService build(Manager manager) throws ConfigurationException {
        return new ConfigurationService(getEnvironment(), this.configurationManager);
    }


}
