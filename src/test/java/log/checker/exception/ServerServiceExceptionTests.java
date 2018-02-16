package log.checker.exception;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

public class ServerServiceExceptionTests {

    @Before
    public void setUp() throws Exception {
        Logger.getRootLogger().setLevel(Level.OFF);
    }

    @Test(expected = ServerServiceException.class)
    public void testThrowServerServiceException() throws ServerServiceException {
        throw new ServerServiceException();
    }

    @Test(expected = ServerServiceException.class)
    public void testThrowServerServiceExceptionWithMessage() throws ServerServiceException {
        throw new ServerServiceException("This is a test");
    }

    @Test(expected = ServerServiceException.class)
    public void testThrowServerServiceExceptionWithMessageAndCause() throws ServerServiceException {
        Exception cause = new NullPointerException();
        throw new ServerServiceException("This is a test", cause);
    }

    @Test(expected = ServerServiceException.class)
    public void testThrowServerServiceExceptionCause() throws ServerServiceException {
        Exception cause = new NullPointerException();
        throw new ServerServiceException(cause);
    }

    @Test(expected = ServerServiceException.class)
    public void testThrowServerServiceExceptionAllParams() throws ServerServiceException {
        String message = "This is a test message";
        boolean enableSuppression = false;
        boolean writableStackTrace = true;
        Exception cause = new NullPointerException();
        throw new ServerServiceException(message,cause, enableSuppression, writableStackTrace);
    }
}
