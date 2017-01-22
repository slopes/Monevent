package monevent.server;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;
import monevent.common.managers.ManageableBase;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ServerConfiguration extends Configuration {


    private final static Logger logger;
    private final static String hostname;
    private final static DateTimeFormatter formatter;


    static {
        logger = LoggerFactory.getLogger("ServerConfiguration");
        hostname = ManageableBase.getComponent();
        formatter = DateTimeFormat.forPattern("yyyy.MM.dd");
    }


    @JsonProperty
    private String name;

    public ServerConfiguration() {
        ServerConfiguration.logger.debug("Creating Server configuration on " + ServerConfiguration.hostname);
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}