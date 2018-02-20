package log.investigator.task;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import log.investigator.model.SftpSession;

import java.util.Properties;

public class SftpClient {

    public void createServerSession(SftpSession sftpSession) throws JSchException {
        String username = sftpSession.getServerLogInDetails().getUsername();
        String password = sftpSession.getServerLogInDetails().getPassword();
        String hostname = sftpSession.getServerLogInDetails().getHostname();
        int port = sftpSession.getServerLogInDetails().getPort();

        JSch jsch=new JSch();

        sftpSession.setSession(
                jsch.getSession(username,
                        hostname,
                        port
                )
        );

        sftpSession.getSession().setPassword(password);
        Properties config = new Properties();
        config.put("StrictHostKeyChecking", "no");
        sftpSession.getSession().setConfig(config);
        sftpSession.getSession().connect();

        sftpSession.setChannel((ChannelSftp) sftpSession.getSession().openChannel("sftp"));
        sftpSession.getChannel().connect();
    }

    public void endServerSession(log.investigator.model.SftpSession sftpSession) {
        sftpSession.getChannel().exit();
        sftpSession.getSession().disconnect();
    }
}
