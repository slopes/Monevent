package monevent.common.managers;

import monevent.common.managers.configuration.ConfigurationManager;
import monevent.common.model.configuration.Configuration;
import monevent.common.model.configuration.ConfigurationException;
import monevent.common.model.configuration.factory.IConfigurationFactory;

/**
 * Created by slopes on 04/03/17.
 */
public class ManageableFactory extends ManageableBase {

    private final ConfigurationManager configurationManager;

    public ManageableFactory() {
        this(new ConfigurationManager());
    }

    public ManageableFactory(ConfigurationManager configurationManager) {
        super("manageableFactory");
        this.configurationManager = configurationManager;
    }

    public IManageable build(String manageableFullName, Manager manager) {
        Configuration configuration = configurationManager.get(manageableFullName);
        if (configuration != null) {
            return configuration.build(manager);
        }
        throw new ConfigurationException(String.format("Cannot find configuration for %s ", manageableFullName));
    }


    public void addFactory(IConfigurationFactory factory) {
        this.configurationManager.setFactory(factory);
    }


    @Override
    protected void doStart() {
        configurationManager.start();
    }

    @Override
    protected void doStop() {
        configurationManager.stop();
    }
}