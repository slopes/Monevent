package monevent.server;

import com.codahale.metrics.MetricRegistry;
import io.dropwizard.Application;
import io.dropwizard.assets.AssetsBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.federecio.dropwizard.swagger.SwaggerBundle;
import io.federecio.dropwizard.swagger.SwaggerBundleConfiguration;
import monevent.common.managers.IManageable;
import monevent.common.managers.ManageableException;
import monevent.server.services.DefaultServiceConfigurationFactory;
import monevent.server.services.IService;
import monevent.server.services.IServiceConfigurationFactory;
import monevent.server.services.ServiceManager;
import monevent.server.services.configuration.ConfigurationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by slopes on 22/01/17.
 */

public class Server extends Application<ServerConfiguration> implements IManageable {

    private final static String DEFAULT_INDEX = "index.html";
    private final static Logger logger;


    private ServiceManager serviceManager;

    static {
        logger = LoggerFactory.getLogger("Server");
    }

    public Server() {

    }


    public static void main(String[] args) throws Exception {

        Server server = new Server();
        try {
            server.run(args);
        } finally {
            server.stop();
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
                configuration.setResourcePackage("monevent.server.services.configuration");
                return configuration;
            }
        });
    }


    @Override

    public void run(ServerConfiguration configuration, Environment environment) {

        MetricRegistry registry = environment.metrics();

        serviceManager = new ServiceManager(new DefaultServiceConfigurationFactory(), environment);

        environment.jersey().setUrlPattern("/api/*");

        start();

    }


    @Override
    public void start() throws ManageableException {
        serviceManager.start();


    }

    @Override
    public void stop() throws ManageableException {
        serviceManager.stop();
    }
}
