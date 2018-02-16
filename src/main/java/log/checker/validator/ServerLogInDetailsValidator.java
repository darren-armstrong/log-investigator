package log.checker.validator;

import log.checker.model.ServerLogInDetails;

public class ServerLogInDetailsValidator extends AbstractValidator {

    @Override
    public boolean supports(Class<?> aClass){
        return ServerLogInDetails.class.equals(aClass);
    }

    @Override
    protected void doSpecificValidation() {
        validateUsername();
        validatePassword();
        validateHostname();
        validatePort();
    }

    private void validateUsername() {
        checkForEmptyFieldOrWhiteSpace("username");
    }

    private void validatePassword() {
        checkForEmptyFieldOrWhiteSpace("password");
    }

    private void validateHostname() {
        checkForEmptyFieldOrWhiteSpace("hostname");
    }

    private void validatePort() {
        ServerLogInDetails serverLogInDetails = (ServerLogInDetails) targetObject;
        int port =  serverLogInDetails.getPort();
        if(port < 0 || port > 65535){
            errors.reject("The Supplied Port Number is required and can only be a range from 0 - 65535");
        }
    }
}
