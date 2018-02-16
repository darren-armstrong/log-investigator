package log.checker.validator;


import log.checker.model.UploadedFile;

public class UploadedFileValidator extends AbstractValidator{

    @Override
    public boolean supports(Class<?> aClass){
        return UploadedFile.class.equals(aClass);
    }

    @Override
    protected void doSpecificValidation() {
        checkForEmptyFieldOrWhiteSpace("contentType");
        checkForEmptyFieldOrWhiteSpace("fileType");
        checkForEmptyFieldOrWhiteSpace("originalFileName");
        checkForEmptyField("file");


    }

}
