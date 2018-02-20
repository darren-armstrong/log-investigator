package log.investigator.model;

import com.jcraft.jsch.*;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import java.util.Properties;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class SftpSessionTests {
    private SftpSession sftpSession;

    @Before
    public void setUp() throws Exception {
        Logger.getRootLogger().setLevel(Level.OFF);
        ServerLogInDetails serverLogInDetails = new ServerLogInDetails("username", "password", "hostname");
        JSch jsch = new JSch();

        Properties config = new Properties();
        config.put("StrictHostKeyChecking", "no");
        JSch.setConfig(config);

        Session session = jsch.getSession("remote-username", "localhost", 22999);
        session.setPassword("remote-password");

        ChannelSftp channelSftp = new ChannelSftp();

        sftpSession = new SftpSession(serverLogInDetails);

        sftpSession.setSession(session);
        sftpSession.setChannel(channelSftp);

    }

    @Test
    public void passInNullLogInDetailsValues(){
        SftpSession nullSftpSession = new SftpSession(null);

        assertEquals(null, nullSftpSession.getSession());
        assertEquals(null, nullSftpSession.getChannel());
    }

    @Test
    public void SessionAndChannelNotConnected() throws JSchException {

        assertFalse(sftpSession.getSession().isConnected());
        assertFalse(sftpSession.getChannel().isConnected());

    }

    @Test
    public void SessionAndChannelConnected() throws JSchException {
        Session mockSession = mock(sftpSession.getSession().getClass());
        ChannelSftp mockChannelSftp = mock(sftpSession.getChannel().getClass());

        when(mockSession.isConnected()).thenReturn(true);
        when(mockChannelSftp.isConnected()).thenReturn(true);

        assertTrue(mockSession.isConnected());
        assertTrue(mockChannelSftp.isConnected());

    }

    @Test
    public void getTheServerLoginDetails(){
        ServerLogInDetails serverLogInDetails = sftpSession.getServerLogInDetails();

        assertEquals("username", serverLogInDetails.getUsername());
        assertEquals("password", serverLogInDetails.getPassword());
        assertEquals("hostname", serverLogInDetails.getHostname());
        assertEquals(22, serverLogInDetails.getPort());
    }
}
