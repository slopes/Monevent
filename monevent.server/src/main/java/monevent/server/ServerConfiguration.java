package monevent.server;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;
import monevent.common.managers.ManageableBase;
import monevent.common.model.configuration.factory.IConfigurationFactory;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ServerConfiguration extends Configuration {


    private final static Logger logger;

    public final static String defaultConfigurationFactory = "fileConfigurationFactory";
    public final static String defaultConfigurationDirectory = "config";

    static {
        logger = LoggerFactory.getLogger("ServerConfiguration");
    }


    @JsonProperty
    private String name;

    @JsonProperty
    private String configurationFactory;


    public ServerConfiguration() {
        setConfigurationFactory(ServerConfiguration.defaultConfigurationFactory);
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getConfigurationFactory() {
        return configurationFactory;
    }

    public void setConfigurationFactory(String configurationFactory) {
        this.configurationFactory = configurationFactory;
    }
}