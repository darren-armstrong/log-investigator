package log.investigator.task;

import log.investigator.exception.ServerServiceException;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

public class ValidateValidator {

    public ValidateValidator(Validator validator, Object target, String objectName) throws ServerServiceException {
        BeanPropertyBindingResult result = new BeanPropertyBindingResult(target, objectName);
        ValidationUtils.invokeValidator(validator, target, result);

        if(result.hasErrors()){
            String errorMessage = generateErrorMessage(result);
            throw new ServerServiceException(errorMessage);
        }
    }

    private String generateErrorMessage(BeanPropertyBindingResult result){
        StringBuilder errorMessage = new StringBuilder("Validation Error");
        if(result.getErrorCount() > 1){
            errorMessage.append("s");
        }
        for (ObjectError error : result.getAllErrors()) {
            errorMessage.append(error.getCode());
            if(result.getErrorCount() > 1){
                errorMessage.append("\n");
            }
        }

        return errorMessage.toString();
    }
}
