package log.investigator.service;

import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.SftpException;
import log.investigator.exception.ServerServiceException;

import java.util.HashMap;
import java.util.List;

public interface ServerService {

    boolean loginAuthentication(String username, String password, String hostname, int port) throws ServerServiceException, JSchException;

    List<String> getAvailableFilesInDirectory(String username, String password, String hostname,
                                              int port, String directory, String file) throws ServerServiceException, JSchException, SftpException;

    HashMap<String, HashMap<String, String>> getAllFromLogFile(String username, String password, String hostname,
                                                               int port, String directory, String file) throws ServerServiceException, JSchException, SftpException;
}
