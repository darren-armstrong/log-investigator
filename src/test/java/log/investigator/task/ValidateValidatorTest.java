package log.investigator.task;


import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.junit.Before;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ValidateValidatorTest {

    @Before
    public void setUp() throws Exception {
        Logger.getRootLogger().setLevel(Level.OFF);
    }

}
