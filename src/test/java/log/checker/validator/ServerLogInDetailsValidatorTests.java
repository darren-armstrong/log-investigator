package log.checker.validator;


import log.checker.model.ServerLogInDetails;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.ValidationUtils;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ServerLogInDetailsValidatorTests {

    @Before
    public void setUp() throws Exception {
        Logger.getRootLogger().setLevel(Level.OFF);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNullServerLogInDetails(){
       ServerLogInDetailsValidator serverLogInDetailsValidator = new ServerLogInDetailsValidator();
        BeanPropertyBindingResult result = new BeanPropertyBindingResult(null, "serverLogInDetails");
        ValidationUtils.invokeValidator(serverLogInDetailsValidator, null, result);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNullServerLogInDetailsTwo(){
        ServerLogInDetails serverLogInDetails = null;

        ServerLogInDetailsValidator serverLogInDetailsValidator = new ServerLogInDetailsValidator();
        BeanPropertyBindingResult result = new BeanPropertyBindingResult(serverLogInDetails, "serverLogInDetails");
        ValidationUtils.invokeValidator(serverLogInDetailsValidator, serverLogInDetails, result);
    }

    @Test
    public void testValidServerLoginDetails(){
        ServerLogInDetails serverLogInDetails = new ServerLogInDetails("test", "test", "test");

        ServerLogInDetailsValidator serverLogInDetailsValidator = new ServerLogInDetailsValidator();
        BeanPropertyBindingResult result = new BeanPropertyBindingResult(serverLogInDetails, "serverLogInDetails");
        ValidationUtils.invokeValidator(serverLogInDetailsValidator, serverLogInDetails, result);

        assertFalse(result.hasErrors());

    }

    @Test
    public void testValidServerLoginDetailsWithPortChange(){
        ServerLogInDetails serverLogInDetails = new ServerLogInDetails("test", "test", "test", 8080);

        ServerLogInDetailsValidator serverLogInDetailsValidator = new ServerLogInDetailsValidator();
        BeanPropertyBindingResult result = new BeanPropertyBindingResult(serverLogInDetails, "serverLogInDetails");
        ValidationUtils.invokeValidator(serverLogInDetailsValidator, serverLogInDetails, result);

        assertFalse(result.hasErrors());

    }

    @Test
    public void testNullServerLoginDetails(){
        ServerLogInDetails serverLogInDetails = new ServerLogInDetails(null, null, null);

        ServerLogInDetailsValidator serverLogInDetailsValidator = new ServerLogInDetailsValidator();
        BeanPropertyBindingResult result = new BeanPropertyBindingResult(serverLogInDetails, "serverLogInDetails");
        ValidationUtils.invokeValidator(serverLogInDetailsValidator, serverLogInDetails, result);

        assertTrue(result.hasErrors());

    }

    @Test
    public void testServerLoginDetailsWithNegativePortNumber(){
        ServerLogInDetails serverLogInDetails = new ServerLogInDetails("This is", "a bad", "port number", -1);

        ServerLogInDetailsValidator serverLogInDetailsValidator = new ServerLogInDetailsValidator();
        BeanPropertyBindingResult result = new BeanPropertyBindingResult(serverLogInDetails, "serverLogInDetails");
        ValidationUtils.invokeValidator(serverLogInDetailsValidator, serverLogInDetails, result);

        assertTrue(result.hasErrors());
    }


    @Test
    public void testServerLoginDetailsWithAPortNumberTooHigh(){
        ServerLogInDetails serverLogInDetails = new ServerLogInDetails("This is", "a bad", "port number", 65536);

        ServerLogInDetailsValidator serverLogInDetailsValidator = new ServerLogInDetailsValidator();
        BeanPropertyBindingResult result = new BeanPropertyBindingResult(serverLogInDetails, "serverLogInDetails");
        ValidationUtils.invokeValidator(serverLogInDetailsValidator, serverLogInDetails, result);

        assertTrue(result.hasErrors());
    }

    @Test
    public void testServerLoginDetailsWitEmptyFields(){
        ServerLogInDetails serverLogInDetails = new ServerLogInDetails("", "", "", 8080);

        ServerLogInDetailsValidator serverLogInDetailsValidator = new ServerLogInDetailsValidator();
        BeanPropertyBindingResult result = new BeanPropertyBindingResult(serverLogInDetails, "serverLogInDetails");
        ValidationUtils.invokeValidator(serverLogInDetailsValidator, serverLogInDetails, result);

        assertTrue(result.hasErrors());
    }


    @Test
    public void testServerLoginDetailsWitWhitespaceOnlyFields(){
        ServerLogInDetails serverLogInDetails = new ServerLogInDetails("     ", "   ", "    ", 8080);

        ServerLogInDetailsValidator serverLogInDetailsValidator = new ServerLogInDetailsValidator();
        BeanPropertyBindingResult result = new BeanPropertyBindingResult(serverLogInDetails, "serverLogInDetails");
        ValidationUtils.invokeValidator(serverLogInDetailsValidator, serverLogInDetails, result);

        assertTrue(result.hasErrors());
    }
}
