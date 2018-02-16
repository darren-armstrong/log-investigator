package log.checker.validator;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import log.checker.model.ServerLogInDetails;
import log.checker.model.SftpSession;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.ValidationUtils;

import java.util.Properties;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class SftpSessionValidatorTests {
    private SftpSession sftpSession;
    private Session session;
    private ChannelSftp channelSftp;

    @Before
    public void setUp() throws Exception {
        Logger.getRootLogger().setLevel(Level.OFF);
        ServerLogInDetails serverLogInDetails = new ServerLogInDetails("test", "password", "hostname");
        JSch jsch = new JSch();

        Properties config = new Properties();
        config.put("StrictHostKeyChecking", "no");
        JSch.setConfig(config);

        session = mock(jsch.getSession("remote-username", "localhost", 22999).getClass());
        session.setPassword("remote-password");

        channelSftp = mock(ChannelSftp.class);

        when(session.isConnected()).thenReturn(true);
        when(channelSftp.isConnected()).thenReturn(true);

        sftpSession = new SftpSession(serverLogInDetails);

        sftpSession.setSession(session);
        sftpSession.setChannel(channelSftp);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNullSftpSession(){
        SftpSessionValidator sftpSessionValidator = new SftpSessionValidator();
        BeanPropertyBindingResult result = new BeanPropertyBindingResult(null, "sftpSession");
        ValidationUtils.invokeValidator(sftpSessionValidator, null, result);
    }


    @Test
    public void testGoodParam(){
        SftpSessionValidator sftpSessionValidator = new SftpSessionValidator();
        BeanPropertyBindingResult result = new BeanPropertyBindingResult(sftpSession, "sftpSession");
        ValidationUtils.invokeValidator(sftpSessionValidator, sftpSession, result);

        assertFalse(result.hasErrors());
    }

    @Test
    public void testChannelNotConnectedError(){
        when(channelSftp.isConnected()).thenReturn(false);

        sftpSession.setChannel(channelSftp);

        SftpSessionValidator sftpSessionValidator = new SftpSessionValidator();
        BeanPropertyBindingResult result = new BeanPropertyBindingResult(sftpSession, "sftpSession");
        ValidationUtils.invokeValidator(sftpSessionValidator, sftpSession, result);

        assertTrue(result.hasErrors());
    }

    @Test
    public void testSessionNotConnectedError(){
        when(session.isConnected()).thenReturn(false);

        sftpSession.setSession(session);

        SftpSessionValidator sftpSessionValidator = new SftpSessionValidator();
        BeanPropertyBindingResult result = new BeanPropertyBindingResult(sftpSession, "sftpSession");
        ValidationUtils.invokeValidator(sftpSessionValidator, sftpSession, result);

        assertTrue(result.hasErrors());
    }

}
