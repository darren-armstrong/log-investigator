package log.checker.validator;


import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.Session;
import log.checker.model.SftpSession;

public class SftpSessionValidator extends AbstractValidator {

    private SftpSession sftpSession;

    @Override
    public boolean supports(Class<?> aClass){ return SftpSession.class.equals(aClass); }

    @Override
    protected void doSpecificValidation() {
        sftpSession = (SftpSession) targetObject;
        validateSession();
        validateChannel();
    }

    private void validateSession(){
        Session session = sftpSession.getSession();

        if(!session.isConnected()){
            errors.reject("Failed to connect to Server");
        }
    }

    private void validateChannel(){
        ChannelSftp channelSftp = sftpSession.getChannel();

        if(!channelSftp.isConnected()){
            errors.reject("Failed to open a channel connection with the supplied server");
        }
    }

}
