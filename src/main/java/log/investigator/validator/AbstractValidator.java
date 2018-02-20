package log.investigator.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

public abstract class AbstractValidator implements Validator{

    Errors errors;
    Object targetObject;

    @Override
    public boolean supports(Class<?> aClass) {
        return false;
    }

    @Override
    public void validate(Object target, Errors errors) {
        this.errors = errors;
        this.targetObject = target;

        genericValidation(targetObject);

        doSpecificValidation();
    }

    private void genericValidation(Object targetObject){
        if(targetObject == null) {
            throw new IllegalArgumentException("The Supplied Object is required and must not be null" );
        }
    }

    protected abstract void doSpecificValidation();

    void checkForEmptyFieldOrWhiteSpace(String fieldName){
        String errorMessage = "The supplied field [" + fieldName + "] is required and must not be null or empty";
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, fieldName, errorMessage);
    }

    void checkForEmptyField(String fieldName){
        String errorMessage = "The supplied field [" + fieldName + "] must not be null";
        ValidationUtils.rejectIfEmpty(errors, fieldName, errorMessage);
    }

}
