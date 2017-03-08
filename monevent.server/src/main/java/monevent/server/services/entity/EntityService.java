package monevent.server.services.entity;

import com.codahale.metrics.annotation.Timed;
import com.codahale.metrics.health.HealthCheck;
import com.google.common.base.Strings;
import io.dropwizard.setup.Environment;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import monevent.common.managers.Manager;
import monevent.common.model.Entity;
import monevent.common.model.IEntity;
import monevent.common.process.IProcessor;
import monevent.server.services.ServiceBase;
import monevent.server.services.ServiceConfiguration;
import monevent.server.services.exception.ServiceException;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by slopes on 19/02/17.
 */
@Path("/entity")
@Produces(MediaType.APPLICATION_JSON)
@Api(value = "/entity")
public class EntityService extends ServiceBase {
    public static final String NAME = "serviceConfigurationFactory.eventService";

    private final Manager manager;

    public EntityService(final Environment environment, final Manager manager) {
        super(EntityService.NAME, environment);
        this.manager = manager;
    }


    @POST
    @Timed()
    @Path("/process/{processorName}")
    @ApiOperation(value = "Process evnt using the given processor name. The configuration of the processor must exist before using this API.", response = ServiceConfiguration.class)
    public Response processEvent(@ApiParam() @PathParam("processorName") final String processorName, Entity entity) throws ServiceException {
        if (Strings.isNullOrEmpty(processorName)) {
            throw new ServiceException("Processor name cannot be null or empty", Response.Status.BAD_REQUEST);
        }

        try {
            IProcessor processor = manager.get(processorName);
            if (processor != null) {
                IEntity result = processor.process(entity);
                return Response.ok(result).status(Response.Status.ACCEPTED).build();
            }
            throw new ServiceException(String.format("The processor %s does not exists", processorName), Response.Status.NOT_FOUND);

        } catch (Exception error) {
            throw new ServiceException("An error occurred while processing the entity", error, Response.Status.INTERNAL_SERVER_ERROR);
        }

    }


    @Override
    public HealthCheck.Result check() {
        //TODO : implements health check
        return HealthCheck.Result.healthy();
    }
}
