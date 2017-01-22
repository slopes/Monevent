package monevent.server.services.exception;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

/**
 * Created by slopes on 22/01/17.
 */
public class ServiceException extends WebApplicationException {

    public ServiceException(String message, Response.Status status) throws IllegalArgumentException {
        super(message, status);
    }

    public ServiceException(String message, Throwable cause, Response.Status status) throws IllegalArgumentException {
        super(message, cause, status);
    }
}
