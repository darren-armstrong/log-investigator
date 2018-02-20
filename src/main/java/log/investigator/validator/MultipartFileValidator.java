package log.investigator.validator;

import org.springframework.web.multipart.MultipartFile;

public class MultipartFileValidator extends AbstractValidator{

    @Override
    public boolean supports(Class<?> aClass){
        return MultipartFile.class.equals(aClass);
    }

    @Override
    protected void doSpecificValidation() {}
}
