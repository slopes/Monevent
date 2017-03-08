package monevent.server.services.configuration;

import com.codahale.metrics.annotation.Timed;
import com.codahale.metrics.health.HealthCheck;
import com.google.common.base.Strings;
import io.dropwizard.setup.Environment;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import monevent.common.managers.configuration.ConfigurationManager;
import monevent.common.model.configuration.Configuration;
import monevent.server.services.ServiceBase;
import monevent.server.services.ServiceConfiguration;
import monevent.server.services.exception.ServiceException;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by slopes on 22/01/17.
 */
@Path("/configuration")
@Produces(MediaType.APPLICATION_JSON)
@Api(value = "/configuration")
public class ConfigurationService extends ServiceBase {

    public static final String NAME = "serviceConfigurationFactory.configurationService";

    private final ConfigurationManager configurationManager;


    public ConfigurationService(final Environment environment, ConfigurationManager configurationManager) {
        super(ConfigurationService.NAME, environment);
        this.configurationManager = configurationManager;
    }

    @GET
    @Timed()
    @Path("/{fullName}")
    @ApiOperation(value = "Return the configuration of a given service", response = ServiceConfiguration.class)
    public Configuration getServiceConfiguration(@ApiParam() @PathParam("fullName") final String fullName) throws ServiceException {

        if (Strings.isNullOrEmpty(fullName)) {
            throw new ServiceException("The configuration full name cannot be null or empty", Response.Status.BAD_REQUEST);
        }

        try {
            Configuration configuration = configurationManager.get(fullName);
            return configuration;
        } catch (Exception error) {
            throw new ServiceException("An error occurred while retrieving the configuration", error, Response.Status.INTERNAL_SERVER_ERROR);
        }
    }

    @POST
    @Timed()
    @Path("/")
    @ApiOperation(value = "Return the configuration of a given service", response = ServiceConfiguration.class)
    public void setServiceConfiguration(@ApiParam() final Configuration configuration) throws ServiceException {

        if (configuration == null) {
            throw new ServiceException("The configuration cannot be null", Response.Status.BAD_REQUEST);
        }

        try {
            configurationManager.set(configuration);
        } catch (Exception error) {
            throw new ServiceException("An error occurred while retrieving the configuration", error, Response.Status.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public HealthCheck.Result check() {
        //TODO : implements health check
        return HealthCheck.Result.healthy();
    }
}
