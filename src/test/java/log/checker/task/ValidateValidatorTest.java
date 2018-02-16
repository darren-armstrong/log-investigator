package log.checker.task;


import log.checker.exception.ServerServiceException;
import log.checker.model.ServerLogInDetails;
import log.checker.validator.ServerLogInDetailsValidator;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ValidateValidatorTest {

    @Before
    public void setUp() throws Exception {
        Logger.getRootLogger().setLevel(Level.OFF);
    }

}
