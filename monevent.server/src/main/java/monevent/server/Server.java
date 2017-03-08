package monevent.server;

import io.dropwizard.Application;
import io.dropwizard.assets.AssetsBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.federecio.dropwizard.swagger.SwaggerBundle;
import io.federecio.dropwizard.swagger.SwaggerBundleConfiguration;
import monevent.common.managers.*;
import monevent.common.managers.configuration.ConfigurationManager;
import monevent.common.model.configuration.Configuration;
import monevent.common.model.configuration.factory.file.FileConfigurationFactory;
import monevent.server.services.IService;
import monevent.server.services.ServiceConfigurationFactory;
import monevent.server.services.configuration.ConfigurationService;
import monevent.server.services.configuration.ConfigurationServiceConfiguration;
import monevent.server.services.entity.EntityService;
import monevent.server.services.entity.EntityServiceConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by slopes on 22/01/17.
 */

public class Server extends Application<ServerConfiguration> implements IManageable {

    private final static String DEFAULT_INDEX = "index.html";
    private final static Logger logger;

    private ConfigurationManager configurationManager;
    private ManageableFactory factory;
    private Manager Manager;

    static {
        logger = LoggerFactory.getLogger("Server");
    }

    public Server() {

    }


    public static void main(String[] args) throws Exception {
        ManageableBase.info(logger, "Starting Monevent server ...");
        Server server = new Server();
        try {
            server.run(args);
        } finally {

            ManageableBase.info(logger, "... Monevent server stopped");
        }

    }


    @Override
    public void initialize(Bootstrap<ServerConfiguration> bootstrap) {
        bootstrap.addBundle(new AssetsBundle("/site/", "/", Server.DEFAULT_INDEX));
        bootstrap.addBundle(new SwaggerBundle<ServerConfiguration>() {
            @Override
            protected SwaggerBundleConfiguration getSwaggerBundleConfiguration(ServerConfiguration serverConfiguration) {
                SwaggerBundleConfiguration configuration = new SwaggerBundleConfiguration();
                configuration.setUriPrefix("/api");
                configuration.setResourcePackage("monevent.server.services");
                return configuration;
            }
        });
    }


    @Override
    public void run(ServerConfiguration configuration, Environment environment) {
        //step 0 : Configure dropwizard
        environment.jersey().setUrlPattern("/api/*");
        this.configurationManager = new ConfigurationManager(new FileConfigurationFactory<>(ConfigurationManager.defaultFactory,ServerConfiguration.defaultConfigurationDirectory));
        ServiceConfigurationFactory serviceConfigurationFactory = new ServiceConfigurationFactory(environment);
        this.configurationManager.setFactory(serviceConfigurationFactory);

        this.factory = new ManageableFactory(this.configurationManager);
        this.Manager = new Manager(this.factory);

        configure(configuration);
        start();
    }


    @Override
    public void start() throws ManageableException {
        //step 1 : create the configuration manager

        configurationManager.start();

        this.factory.start();

        this.Manager.start();

        IService eventService = this.Manager.get(EntityService.NAME);
        IService configurationService = this.Manager.get(ConfigurationService.NAME);

    }

    private void configure(ServerConfiguration configuration) {
        configurationManager.set(new ConfigurationServiceConfiguration(configurationManager));
        configurationManager.set(new EntityServiceConfiguration());

        if (ServerConfiguration.defaultConfigurationDirectory.equals(configuration.getConfigurationFactory())) {
            configurationManager.setFactory(new FileConfigurationFactory<Configuration>("default",ServerConfiguration.defaultConfigurationDirectory));
         }
    }

    @Override
    public void stop() throws ManageableException {

        this.Manager.stop();

        this.factory.start();

        this.configurationManager.stop();
    }
}
