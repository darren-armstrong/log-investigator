package log.investigator.model;


import java.util.ArrayList;
import java.util.List;

public class SessionRequest {
    private String directory = "";
    private String file = "*.log";
    private List<String> searchRequest = new ArrayList<>();

    public SessionRequest(String directory){
        setDirectory(directory);
    }

    public SessionRequest(String directory, String file){
        setDirectory(directory);
        setFile(file);
    }

    public SessionRequest(String directory, String file, List<String> searchRequest){
        setDirectory(directory);
        setFile(file);
        setSearchRequest(searchRequest);
    }

    public String getDirectory() {
        return directory;
    }

    public void setDirectory(String directory) {
        this.directory = directory;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public List<String> getSearchRequest() {
        return searchRequest;
    }

    public void setSearchRequest(List<String> searchRequest) {
        this.searchRequest = searchRequest;
    }
}
