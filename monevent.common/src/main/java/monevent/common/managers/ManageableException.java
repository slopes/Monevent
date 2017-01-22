package monevent.common.managers;

/**
 * Created by slopes on 22/01/17.
 */
public class ManageableException extends RuntimeException {
    public ManageableException() {
        super();
    }

    public ManageableException(String message) {
        super(message);
    }

    public ManageableException(String message, Throwable cause) {
        super(message, cause);
    }

    public ManageableException(Throwable cause) {
        super(cause);
    }

    protected ManageableException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
