package monevent.server.services;

import com.codahale.metrics.health.HealthCheck;
import monevent.common.managers.IManageable;

/**
 * Created by slopes on 22/01/17.
 */
public interface IService extends IManageable {
    HealthCheck getHealthCheck();
}
