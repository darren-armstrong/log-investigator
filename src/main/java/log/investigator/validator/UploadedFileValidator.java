package log.investigator.validator;


import log.investigator.model.UploadedFile;

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
