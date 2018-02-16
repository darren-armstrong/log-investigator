package log.checker.model;


import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.Session;

public class SftpSession {

    private ServerLogInDetails serverLogInDetails;
    private Session session;
    private ChannelSftp channel;

    public SftpSession(ServerLogInDetails serverLogInDetails){
        this.serverLogInDetails = serverLogInDetails;
    }

    public ServerLogInDetails getServerLogInDetails() {
        return serverLogInDetails;
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public ChannelSftp getChannel() {
        return channel;
    }

    public void setChannel(ChannelSftp channel) {
        this.channel = channel;
    }

}
