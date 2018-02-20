package log.investigator.validator;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.ValidationUtils;

public class AbstractValidatorTests {

    @Before
    public void setUp() throws Exception {
        Logger.getRootLogger().setLevel(Level.OFF);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNullObject(){
        AbstractValidator abstractValidator = new AbstractValidator() {
            @Override
            protected void doSpecificValidation() { }
        };
        BeanPropertyBindingResult result = new BeanPropertyBindingResult(null, "null");
        ValidationUtils.invokeValidator(abstractValidator, null, result);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNotSupportedObject(){
        Object person = new Object();

        AbstractValidator abstractValidator = new AbstractValidator() {
            @Override
            protected void doSpecificValidation() { }
        };
        BeanPropertyBindingResult result = new BeanPropertyBindingResult(person, "AbstractValidator");
        ValidationUtils.invokeValidator(abstractValidator, person, result);
    }
}
