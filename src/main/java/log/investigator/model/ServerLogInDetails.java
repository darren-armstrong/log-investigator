package log.investigator.model;


public class ServerLogInDetails {
    private String username = "";
    private String password = "";
    private String hostname = "";
    private int port = 22;

    public ServerLogInDetails(String username, String password, String hostname, int port){
        setUsername(username);
        setPassword(password);
        setHostname(hostname);
        setPort(port);
    }

    public ServerLogInDetails(String username, String password, String hostname){
        setUsername(username);
        setPassword(password);
        setHostname(hostname);
    }

    public String getUsername() { return username; }

    public void setUsername(String username) { this.username = username; }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
}
