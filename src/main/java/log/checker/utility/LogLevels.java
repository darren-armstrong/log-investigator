package log.checker.utility;

public class LogLevels {
    private String [] logLevels = {"ERROR", "INFO", "FATAL", "WARN",
            "DEBUG", "TRACE", "UNKNOWN", "ALERT", "CRITICAL",
            "SEVERE", "WARNING", "CONFIG", "FINE", "FINER",
            "FINEST", "EMERGENCY", "NOTICE"};

    public String[] getLogLevels() {
        return logLevels;
    }
}
