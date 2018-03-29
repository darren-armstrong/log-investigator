package log.investigator.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.SftpException;
import log.investigator.exception.ServerServiceException;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

public interface ServerService {

    boolean loginAuthentication(String username, String password, String hostname, int port)
            throws ServerServiceException, JSchException;

    String getAvailableFilesInDirectory(String username, String password, String hostname,
                                        int port, String directory, String file)
            throws ServerServiceException, JSchException, SftpException, JsonProcessingException;

    String getAllFromLogFile(String username, String password, String hostname,
                             int port, String directory, String file)
            throws ServerServiceException, JSchException, SftpException, IOException;
}
