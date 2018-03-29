package log.investigator.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.SftpException;
import log.investigator.exception.ServerServiceException;
import log.investigator.service.impl.ServerServiceImp;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ServerServiceTests {
    private ServerService mockServerService;


    @Before
    public void setUp() throws Exception {
        Logger.getRootLogger().setLevel(Level.OFF);
        mockServerService = mock(ServerServiceImp.class);
    }

    @Test(expected = ServerServiceException.class)
    public void testBadLoginAuthentication() throws ServerServiceException, JSchException {
        ServerService serverService = new ServerServiceImp();
        serverService.loginAuthentication(null,null, null, -1);

    }

    @Test(expected = ServerServiceException.class)
    public void testInvalidGetAvailableLogsInDir() throws ServerServiceException, JSchException, SftpException, JsonProcessingException {
        ServerService serverService = new ServerServiceImp();
        serverService.getAvailableFilesInDirectory("null","null",
                "test-app.test.org", -1, "",
                "*.log");
    }

    @Test(expected = ServerServiceException.class)
    public void getAllFromLogFile() throws ServerServiceException, JSchException, SftpException, IOException {
        ServerService serverService = new ServerServiceImp();
        serverService.getAllFromLogFile(null,null, null, -1, null, null );
    }

    @Test
    public void testValidLoginAuthentication() throws ServerServiceException, JSchException {
        boolean authenticated;
        when(mockServerService.loginAuthentication("test", "test", "test-app.test.org", 22)).thenReturn(true);

        authenticated = mockServerService.loginAuthentication("test","test", "test-app.test.org", 22);


        assertTrue(authenticated);
    }

    @Test
    public void testValidGetAvailableLogsInDir() throws ServerServiceException, JSchException, SftpException, JsonProcessingException {
        String mockAvailableLogs = "{\"1\", \"test.log\", \"2\":\"test_2.log\", \"3\":\"test_3.log\"}";

        when(mockServerService.getAvailableFilesInDirectory("test", "password",
                "test-app.test.org", 22, "/var/log/bp/",
                "*.log")).thenReturn(mockAvailableLogs);

        String availableLogs = mockServerService.getAvailableFilesInDirectory("test", "password",
                "test-app.test.org", 22, "/var/log/bp/",
                "*.log");


        assertEquals(mockAvailableLogs, availableLogs);
    }

}
