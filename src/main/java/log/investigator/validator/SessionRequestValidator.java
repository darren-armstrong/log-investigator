package log.investigator.validator;

import log.investigator.model.SessionRequest;

import java.util.List;

public class SessionRequestValidator extends AbstractValidator{

    private SessionRequest sessionRequest;

    @Override
    public boolean supports(Class<?> aClass){
        return SessionRequest.class.equals(aClass);
    }

    @Override
    protected void doSpecificValidation() {
        sessionRequest = (SessionRequest) targetObject;
        validateDirectory();
        validateFile();
        validateSearchRequest();
    }

    private void validateDirectory(){
        checkForEmptyFieldOrWhiteSpace("directory");
    }

    private void validateFile(){
        checkForEmptyFieldOrWhiteSpace("file");
    }


    private void validateSearchRequest(){
        List<String> search = sessionRequest.getSearchRequest();
        checkForEmptyField("searchRequest");
        if (search != null && !search.isEmpty()) {
            checkForNullData(search);
        }
    }

    private void checkForNullData(List<String> search){

        for (String searchItem :search) {
            if(searchItem == null){
                errors.reject("One or more of the search request had null search items");
                break;
            }
        }

    }

}
