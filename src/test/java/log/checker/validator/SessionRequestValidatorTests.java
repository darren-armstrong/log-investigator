package log.checker.validator;


import log.checker.model.SessionRequest;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.ValidationUtils;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class SessionRequestValidatorTests {

    @Before
    public void setUp() throws Exception {
        Logger.getRootLogger().setLevel(Level.OFF);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNullServerRequest(){
        SessionRequestValidator sessionRequestValidator = new SessionRequestValidator();
        BeanPropertyBindingResult result = new BeanPropertyBindingResult(null, "logRequest");
        ValidationUtils.invokeValidator(sessionRequestValidator, null, result);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNullServerRequestTwo(){
        SessionRequest sessionRequest = null;

        SessionRequestValidator sessionRequestValidator = new SessionRequestValidator();
        BeanPropertyBindingResult result = new BeanPropertyBindingResult(sessionRequest, "sessionRequest");
        ValidationUtils.invokeValidator(sessionRequestValidator, sessionRequest, result);
    }

    @Test
    public void testValidDirectoryServerRequest(){
        SessionRequest sessionRequest = new SessionRequest("New/directory");

        SessionRequestValidator sessionRequestValidator = new SessionRequestValidator();
        BeanPropertyBindingResult result = new BeanPropertyBindingResult(sessionRequest, "sessionRequest");
        ValidationUtils.invokeValidator(sessionRequestValidator, sessionRequest, result);


        assertFalse(result.hasErrors());
    }

    @Test
    public void testNewDirectoryAndFileServerRequest(){
        SessionRequest sessionRequest = new SessionRequest("New/directory", "new.file");

        SessionRequestValidator sessionRequestValidator = new SessionRequestValidator();
        BeanPropertyBindingResult result = new BeanPropertyBindingResult(sessionRequest, "sessionRequest");
        ValidationUtils.invokeValidator(sessionRequestValidator, sessionRequest, result);


        assertFalse(result.hasErrors());
    }

    @Test
    public void testNewDirectoryFileAndSearchRequestServerRequest(){
        List<String> searchRequest = new ArrayList<>();
        searchRequest.add("Search One");
        searchRequest.add("Search Two");
        SessionRequest sessionRequest = new SessionRequest("New/directory", "new.file", searchRequest);

        SessionRequestValidator sessionRequestValidator = new SessionRequestValidator();
        BeanPropertyBindingResult result = new BeanPropertyBindingResult(sessionRequest, "sessionRequest");
        ValidationUtils.invokeValidator(sessionRequestValidator, sessionRequest, result);


        assertFalse(result.hasErrors());
    }

    @Test
    public void searchRequestListHasNullInIt(){
        List<String> searchRequest = new ArrayList<>();
        searchRequest.add("Search One");
        searchRequest.add(null);
        SessionRequest sessionRequest = new SessionRequest("New/directory", "new.file", searchRequest);

        SessionRequestValidator sessionRequestValidator = new SessionRequestValidator();
        BeanPropertyBindingResult result = new BeanPropertyBindingResult(sessionRequest, "sessionRequest");
        ValidationUtils.invokeValidator(sessionRequestValidator, sessionRequest, result);


        assertTrue(result.hasErrors());
    }

    @Test
    public void searchRequestListHasNullsInMiddleOfIt(){
        List<String> searchRequest = new ArrayList<>();
        searchRequest.add("Search One");
        searchRequest.add(null);
        searchRequest.add("Search two");
        searchRequest.add(null);
        searchRequest.add("Search three");
        SessionRequest sessionRequest = new SessionRequest("New/directory", "new.file", searchRequest);

        SessionRequestValidator sessionRequestValidator = new SessionRequestValidator();
        BeanPropertyBindingResult result = new BeanPropertyBindingResult(sessionRequest, "sessionRequest");
        ValidationUtils.invokeValidator(sessionRequestValidator, sessionRequest, result);


        assertTrue(result.hasErrors());
    }

    @Test
    public void testServerRequestWithBadParams(){
        SessionRequest sessionRequest = new SessionRequest(null, null, null);

        SessionRequestValidator sessionRequestValidator = new SessionRequestValidator();
        BeanPropertyBindingResult result = new BeanPropertyBindingResult(sessionRequest, "sessionRequest");
        ValidationUtils.invokeValidator(sessionRequestValidator, sessionRequest, result);


        assertTrue(result.hasErrors());
    }

    @Test
    public void testServerRequestWithBadSearchRequest(){
        SessionRequest sessionRequest = new SessionRequest("Test/Dir", ".logs", null);

        SessionRequestValidator sessionRequestValidator = new SessionRequestValidator();
        BeanPropertyBindingResult result = new BeanPropertyBindingResult(sessionRequest, "sessionRequest");
        ValidationUtils.invokeValidator(sessionRequestValidator, sessionRequest, result);


        assertTrue(result.hasErrors());
    }

    @Test
    public void testServerRequestWithEmptyStringParams(){
        SessionRequest sessionRequest = new SessionRequest("", "");

        SessionRequestValidator sessionRequestValidator = new SessionRequestValidator();
        BeanPropertyBindingResult result = new BeanPropertyBindingResult(sessionRequest, "sessionRequest");
        ValidationUtils.invokeValidator(sessionRequestValidator, sessionRequest, result);


        assertTrue(result.hasErrors());
    }

    @Test
    public void testServerRequestWithWhitespaceStringParams(){
        SessionRequest sessionRequest = new SessionRequest("    ", "     ");

        SessionRequestValidator sessionRequestValidator = new SessionRequestValidator();
        BeanPropertyBindingResult result = new BeanPropertyBindingResult(sessionRequest, "sessionRequest");
        ValidationUtils.invokeValidator(sessionRequestValidator, sessionRequest, result);


        assertTrue(result.hasErrors());
    }
}
