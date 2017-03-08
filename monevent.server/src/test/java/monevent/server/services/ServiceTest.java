package monevent.server.services;

import monevent.common.managers.configuration.ConfigurationManager;
import monevent.common.model.configuration.Configuration;

/**
 * Created by slopes on 05/03/17.
 */
public class ServiceTest {

    protected ConfigurationManager configurationManager;
    protected ServiceConfigurationFactory factory;

    protected ServiceTest() {
        this.factory = new ServiceConfigurationFactory(null);
        this.configurationManager = new ConfigurationManager();
        this.configurationManager.setFactory(this.factory);
    }

    protected ConfigurationManager getConfigurationManager() {
        return configurationManager;
    }

    protected void setConfigurationManager(ConfigurationManager configurationManager) {
        this.configurationManager = configurationManager;
    }

    protected ServiceConfigurationFactory getFactory() {
        return factory;
    }

    protected void setFactory(ServiceConfigurationFactory factory) {
        this.factory = factory;
    }

    protected void addConfiguration(ServiceConfiguration serviceConfiguration) {
        this.configurationManager.set(serviceConfiguration);
    }
}
