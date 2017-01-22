package monevent.server.services.configuration;

import com.codahale.metrics.annotation.Timed;
import com.codahale.metrics.health.HealthCheck;
import io.dropwizard.setup.Environment;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import monevent.server.services.DefaultServiceConfigurationFactory;
import monevent.server.services.IServiceConfigurationFactory;
import monevent.server.services.ServiceBase;
import monevent.server.services.ServiceConfiguration;
import monevent.server.services.exception.ServiceException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by slopes on 22/01/17.
 */
@Path("/configuration")
@Produces(MediaType.APPLICATION_JSON)
@Api(value = "/configuration")
public class ConfigurationService extends ServiceBase {

    public static final String NAME = "configurationService";

    private final IServiceConfigurationFactory serviceConfigurationFactory;

    protected ConfigurationService() {
        super(ConfigurationService.NAME, null);
        this.serviceConfigurationFactory = new DefaultServiceConfigurationFactory();
    }

    public ConfigurationService(final Environment environment, final IServiceConfigurationFactory serviceConfigurationFactory) {
        super(ConfigurationService.NAME, environment);
        this.serviceConfigurationFactory = serviceConfigurationFactory;
    }

    @GET
    @Timed()
    @Path("/service/{service}")
    @ApiOperation(value = "Return configuration of a given service", response = ServiceConfiguration.class)
    public ServiceConfiguration getServiceConfiguration(@ApiParam() @PathParam("service") final String service) throws ServiceException {
        if (service == null) {
            throw new ServiceException("service name cannot be null", Response.Status.BAD_REQUEST);
        }

        try {
            return serviceConfigurationFactory.build(service);
        } catch (Exception error) {
            throw new ServiceException("An error occured while retrieving the configuration", error, Response.Status.INTERNAL_SERVER_ERROR);
        }
    }


    @Override
    public HealthCheck getHealthCheck() {
        return new HealthCheck() {
            @Override
            protected Result check() throws Exception {
                return HealthCheck.Result.healthy();
            }
        };
    }
}
