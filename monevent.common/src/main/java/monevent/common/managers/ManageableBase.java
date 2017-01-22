package monevent.common.managers;

import monevent.common.communication.EntityBusManager;
import monevent.common.communication.IEntityBus;
import monevent.common.model.IEntity;
import monevent.common.model.fault.Fault;
import monevent.common.process.ProcessorException;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Created by Stephane on 21/11/2015.
 */

public abstract class ManageableBase implements IManageable {

    private static final String UNKNOWN_HOST = "undefined";
    private static String hostname;
    private static String component;

    static {
        try {
            hostname = InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException error) {
            hostname = ManageableBase.UNKNOWN_HOST;
        }
    }

    public synchronized static void setComponent(String component) {
        ManageableBase.component = component;
    }

    public static String getComponent() {
        return ManageableBase.component;
    }

    private static IEntityBus entityBus;

    public synchronized static void setEventIPublisher(IEntityBus IEntityBus) {
        ManageableBase.entityBus = IEntityBus;
    }

    public static void log(IEntity entity) {
        if (entityBus != null && entity != null) {
            ManageableBase.entityBus.publish(entity);
        }
    }

    private final Logger logger;
    private final String name;

    protected ManageableBase(String name) {
        if (!StringUtils.isNotBlank(name)) {
            throw new IllegalArgumentException("Blank or empty name are not valid");
        }
        this.logger = LoggerFactory.getLogger(name);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static void debug(final Logger logger, String trace, Object... args) {
        if (logger == null) return;
        if (logger.isDebugEnabled()) {
            logger.debug(trace(trace, args));
        }
    }

    protected void debug(String trace, Object... args) {
        ManageableBase.debug(logger, trace, args);
    }

    public static void info(final Logger logger, String trace, Object... args) {
        if (logger == null) return;
        if (logger.isInfoEnabled()) {
            logger.info(trace(trace, args));
        }
    }

    protected void info(String trace, Object... args) {
        ManageableBase.info(logger, trace, args);
    }

    public static void warn(final Logger logger, String trace, Object... args) {
        if (logger == null) return;
        if (logger.isWarnEnabled()) {
            logger.warn(trace(trace, args));
        }
    }

    protected void warn(String trace, Object... args) {
        ManageableBase.warn(logger, trace, args);
    }

    public static void error(final Logger logger, String trace, Object... args) {
        if (logger == null) return;
        if (logger.isErrorEnabled()) {
            logger.error(trace(trace, args));
        }
    }

    protected void error(String trace, Object... args) {
        ManageableBase.error(logger, trace, args);
    }

    public static void error(final Logger logger, String trace, Exception exception, Object... args) {
        if (logger == null) return;
        if (logger.isInfoEnabled()) {
            if (exception != null) {
                logger.error(trace("%s : " + trace, exception.getMessage(), args), exception);
            } else {
                logger.error(trace(trace, args));
            }
        }

        if (entityBus != null) {
            Fault fault = new Fault("exception");
            fault.setComponent(ManageableBase.getComponent());
            fault.setHost(ManageableBase.hostname);
            log(fault);
        }
    }

    protected void error(String trace, Exception exception, Object... args) {
        ManageableBase.error(logger, trace, exception, args);
    }

    public static String trace(String format, Object... args) {
        return String.format(format, args);
    }

    public void start() throws ProcessorException {
        debug("Starting %s ...", getName());
        try {
            doStart();
        } catch (Exception error) {
            error("Cannot start %s", error, getName());
            throw new ProcessorException(trace("Cannot start %s", getName()), error);
        } finally {
            debug("%s ... started.", getName());
        }
    }

    protected abstract void doStart();

    public void stop() throws ProcessorException {
        debug("Stopping %s ...", getName());
        try {
            doStop();
        } catch (Exception error) {
            error("Cannot stop %s", error, getName());
            throw new ProcessorException(trace("Cannot stop %s", getName()), error);
        } finally {
            debug("%s ... stopped.", getName());
        }
    }

    protected abstract void doStop();

    protected boolean publish(EntityBusManager entityBusManager, String bus, IEntity entity) {
        if (entity == null) {
            warn("Cannot publish null entity");
            return false;
        }

        if (bus == null) {
            warn("Cannot publish entity on a null bus");
            return false;
        }

        if (entityBusManager == null) {
            warn("Entity manager is not initialized.");
            return false;
        }

        IEntityBus entityBus = entityBusManager.load(bus);
        if (entityBus != null) {
            entityBus.publish(entity);
            return true;
        } else {
            warn("The entity bus %s does not exists.", bus);
        }


        return false;
    }
}