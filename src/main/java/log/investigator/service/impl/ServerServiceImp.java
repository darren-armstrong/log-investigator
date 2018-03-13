package log.investigator.service.impl;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.SftpException;
import log.investigator.exception.ServerServiceException;
import log.investigator.model.ServerLogInDetails;
import log.investigator.model.SessionRequest;
import log.investigator.model.SftpSession;
import log.investigator.service.ServerService;
import log.investigator.task.LogContentTransformer;
import log.investigator.task.SftpClient;
import log.investigator.task.ValidateValidator;
import log.investigator.validator.ServerLogInDetailsValidator;
import log.investigator.validator.SessionRequestValidator;
import log.investigator.validator.SftpSessionValidator;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;


@Service
public class ServerServiceImp implements ServerService {
    private SftpSession sftpSession;
    private SessionRequest sessionRequest;
    private String username;
    private String password;
    private String hostname;
    private String directory;
    private String file;
    private int port;


    @Override
    public boolean loginAuthentication(String username, String password, String hostname, int port) throws ServerServiceException, JSchException {
        this.username = username;
        this.password = password;
        this.hostname = hostname;
        this.port = port;
        setUpSftpClient();
        endSftpClientConnection();
        return true;
    }

    @Override
    public List<String> getAvailableFilesInDirectory(String username, String password, String hostname, int port, String directory, String file)
            throws ServerServiceException, JSchException, SftpException {
        List<String> listOfLogs = new ArrayList<>();
        this.username = username;
        this.password = password;
        this.hostname = hostname;
        this.directory = directory;
        this.file = file;
        this.port = port;

        setUpSessionRequest();
        setUpSftpClient();

        ChannelSftp channelSftp = sftpSession.getChannel();
        goToDirectory(channelSftp);

        Vector<ChannelSftp.LsEntry> list = channelSftp.ls(file);
        for(ChannelSftp.LsEntry entry : list) {
            listOfLogs.add(entry.getFilename());
        }

        endSftpClientConnection();

        return listOfLogs;
    }

    @Override
    public HashMap<String, HashMap<String, String>> getAllFromLogFile(String username, String password, String hostname,
                                                                      int port, String directory, String file)
            throws ServerServiceException, JSchException, SftpException, IOException {
        HashMap<String, HashMap<String, String>> dataFromLogFile;
        this.username = username;
        this.password = password;
        this.hostname = hostname;
        this.port = port;
        this.directory = directory;
        this.file = file;

        setUpSessionRequest();
        setUpSftpClient();

        ChannelSftp channelSftp = sftpSession.getChannel();
        goToDirectory(channelSftp);
        LogContentTransformer logContentTransformer = new LogContentTransformer(channelSftp.get(file));
        dataFromLogFile = logContentTransformer.generateLogOutput();


        endSftpClientConnection();
        return dataFromLogFile;
    }

    private void setUpSftpClient() throws ServerServiceException, JSchException {
        SftpClient sftpClient = new SftpClient();
        sftpSession = new SftpSession(
                setUpServerLogInDetails()
        );
        sftpClient.createServerSession(sftpSession);
        validateSftpClient(sftpSession);
    }

    private ServerLogInDetails setUpServerLogInDetails() throws ServerServiceException {
        ServerLogInDetails serverLogInDetails = new ServerLogInDetails(username, password, hostname, port);
        validateServerLogInDetails(serverLogInDetails);

        return serverLogInDetails;
    }

    protected void endSftpClientConnection(){
        SftpClient sftpClient = new SftpClient();
        sftpClient.endServerSession(sftpSession);
    }

    protected void setUpSessionRequest() throws ServerServiceException {
        sessionRequest = new SessionRequest(directory, file);
        validateSessionRequest(sessionRequest);
    }

    private void goToDirectory(ChannelSftp channelSftp) throws SftpException {
        channelSftp.cd(directory);
    }

    private void validateServerLogInDetails(ServerLogInDetails serverLogInDetails) throws ServerServiceException {
        ServerLogInDetailsValidator serverLogInDetailsValidator = new ServerLogInDetailsValidator();
        new ValidateValidator(serverLogInDetailsValidator, serverLogInDetails, "serverLogInDetails");
    }

    private void validateSftpClient(SftpSession sftpSession) throws ServerServiceException {
        SftpSessionValidator sftpSessionValidator = new SftpSessionValidator();
        new ValidateValidator(sftpSessionValidator, sftpSession, "sftpSession");
    }

    private void validateSessionRequest(SessionRequest sessionRequest) throws ServerServiceException {
        SessionRequestValidator sessionRequestValidator = new SessionRequestValidator();
        new ValidateValidator(sessionRequestValidator, sessionRequest, "sessionRequest");
    }
}
