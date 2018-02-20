package log.investigator.exception;

public class ServerServiceException extends Exception {

    public ServerServiceException(){
        super();
    }

    public ServerServiceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public ServerServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public ServerServiceException(String message) {
        super(message);
    }

    public ServerServiceException(Throwable cause) {
        super(cause);
    }
}
