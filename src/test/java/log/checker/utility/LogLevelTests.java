package log.checker.utility;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertFalse;

public class LogLevelTests {

    private LogLevels logLevels;

    @Before
    public void setUp() throws Exception {
        Logger.getRootLogger().setLevel(Level.OFF);
        logLevels = new LogLevels();
    }

    @Test(expected = NullPointerException.class)
    public void nullLogLevelsObject(){
        LogLevels logLevels = null;
        logLevels.getLogLevels();
    }

    @Test
    public void getAllTheAvailableLogLevels(){
        String [] logLevels = this.logLevels.getLogLevels();

        assertFalse(logLevels.length == 0);
    }
}
