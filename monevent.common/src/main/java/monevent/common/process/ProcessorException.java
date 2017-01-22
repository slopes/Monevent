package monevent.common.process;

/**
 * Created by Stephane on 20/11/2015.
 */
public class ProcessorException extends RuntimeException {

    private static final long serialVersionUID = 2481765691453713637L;

    public ProcessorException() {
        // TODO Auto-generated constructor stub
    }

    public ProcessorException(String message) {
        super(message);
        // TODO Auto-generated constructor stub
    }

    /**
     * @param cause
     */
    public ProcessorException(Throwable cause) {
        super(cause);
        // TODO Auto-generated constructor stub
    }

    /**
     * @param message
     * @param cause
     */
    public ProcessorException(String message, Throwable cause) {
        super(message, cause);
        // TODO Auto-generated constructor stub
    }

    /**
     * @param message
     * @param cause
     * @param enableSuppression
     * @param writableStackTrace
     */
    public ProcessorException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause,enableSuppression,writableStackTrace);
        // TODO Auto-generated constructor stub
    }

}
