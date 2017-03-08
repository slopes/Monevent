package monevent.common.managers.configuration;

import com.google.common.base.Strings;
import monevent.common.managers.ManageableBase;
import monevent.common.model.configuration.Configuration;
import monevent.common.model.configuration.ConfigurationException;
import monevent.common.model.configuration.factory.IConfigurationFactory;
import monevent.common.model.configuration.factory.memory.MemoryConfigurationFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by slopes on 25/02/17.
 */
public class ConfigurationManager extends ManageableBase {

    public final static String defaultFactory = "default";

    private Map<String, IConfigurationFactory> configurationFactories;

    public ConfigurationManager() {
       this(new MemoryConfigurationFactory(ConfigurationManager.defaultFactory));
    }

    public ConfigurationManager(IConfigurationFactory defaultFactory) {
        super("configurationManager");
        this.configurationFactories = new ConcurrentHashMap<>();
        this.configurationFactories.put(ConfigurationManager.defaultFactory, defaultFactory);
    }

    public <T extends Configuration> IConfigurationFactory<T> getFactory(String factory) {
        return configurationFactories.get(factory);
    }

    public <T extends Configuration> void setFactory(IConfigurationFactory<T> configurationFactory) {
        if (configurationFactory == null) {
            warn("Cannot add null factory");
            return;
        }
        configurationFactories.put(configurationFactory.getName(), configurationFactory);
    }

    public Configuration get(String manageableFullName) {
        if (Strings.isNullOrEmpty(manageableFullName))
            throw new ConfigurationException("The manageable full name cannot be null or empty.");

        String[] manageableNames = manageableFullName.split("\\.");
        if (manageableNames.length > 2) {
            throw new ConfigurationException(String.format("The manageable full name must comply to the template : <factory>.<name>. The name %s is not valid.", manageableFullName));
        }

        IConfigurationFactory factory = null;
        if (manageableNames.length == 2) {

            factory = this.configurationFactories.get(manageableNames[0]);
        } else {
            for (IConfigurationFactory configurationFactory : this.configurationFactories.values()) {
                if (configurationFactory != null && configurationFactory.canBuild(manageableNames[0])) {
                    factory = configurationFactory;
                    break;
                }
            }
        }


        if (factory == null) {
            throw new ConfigurationException(String.format("The configuration factory %s has not been loaded.", manageableNames[0]));
        }

        return factory.build(manageableFullName);
    }

    public void set(Configuration configuration) {

        if (Strings.isNullOrEmpty(configuration.getName()))
            throw new ConfigurationException("The manageable full name cannot be null or empty.");

        String[] manageableNames = configuration.getName().split("\\.");
        if (manageableNames.length > 2) {
            throw new ConfigurationException(String.format("The manageable full name must comply to the template : <factory>.<name>. The name %s is not valid.", configuration.getName()));
        }

        IConfigurationFactory factory = null;
        if (manageableNames.length == 2) {

            factory = this.configurationFactories.get(manageableNames[0]);
        } else {
            for (IConfigurationFactory configurationFactory : this.configurationFactories.values()) {
                if (configurationFactory != null && configurationFactory.canBuild(manageableNames[0])) {
                    factory = configurationFactory;
                    break;
                }
            }
        }

        if (factory == null) {
            factory = this.configurationFactories.get(ConfigurationManager.defaultFactory);
        }
        factory.add(configuration);

    }

    @Override
    protected void doStart() {
        IConfigurationFactory factory = this.configurationFactories.get(ConfigurationManager.defaultFactory);
        if (factory == null) {
            throw new ConfigurationException("No default factory has been defined.");
        }
        configurationFactories.values().forEach(f -> f.start());
    }

    @Override
    protected void doStop() {
        configurationFactories.values().forEach(f -> f.stop());
    }
}
