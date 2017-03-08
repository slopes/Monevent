package monevent.server.services.entity;

import io.swagger.annotations.ApiModel;
import monevent.common.managers.Manager;
import monevent.common.model.configuration.ConfigurationException;
import monevent.server.services.ServiceConfiguration;

/**
 * Created by slopes on 22/01/17.
 */
@ApiModel
public class EntityServiceConfiguration extends ServiceConfiguration {


    public EntityServiceConfiguration() {
        super(EntityService.NAME);
    }

    @Override
    public EntityService build(Manager manager) throws ConfigurationException {
        return new EntityService(getEnvironment(), manager);
    }


}
