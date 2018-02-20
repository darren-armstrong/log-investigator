package log.investigator.model;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;


public class SessionRequestTests {

    @Before
    public void setUp() throws Exception {
        Logger.getRootLogger().setLevel(Level.OFF);
    }

    @Test
    public void testNewDirectoryParam(){
        SessionRequest sessionRequest = new SessionRequest("/New/Directory");


        Assert.assertEquals("/New/Directory", sessionRequest.getDirectory());
        Assert.assertEquals("*.log", sessionRequest.getFile());
        Assert.assertTrue(sessionRequest.getSearchRequest().isEmpty());
    }

    @Test
    public void testNewDireAndFileRequest(){
        SessionRequest sessionRequest = new SessionRequest("/New/Directory", "FindThisLog.log");


        Assert.assertEquals("/New/Directory", sessionRequest.getDirectory());
        Assert.assertEquals("FindThisLog.log", sessionRequest.getFile());
        Assert.assertTrue(sessionRequest.getSearchRequest().isEmpty());
    }

    @Test
    public void testNewDireFileAndSearchRequest(){
        List<String> searchRequest = new ArrayList<>();
        List<String> searchRequestFromSessionRequest;
        searchRequest.add("Search One");
        searchRequest.add("Search Two");
        SessionRequest sessionRequest = new SessionRequest("/New/Directory", "FindThisLog.log", searchRequest);

        searchRequestFromSessionRequest = sessionRequest.getSearchRequest();


        Assert.assertEquals("/New/Directory", sessionRequest.getDirectory());
        Assert.assertEquals("FindThisLog.log", sessionRequest.getFile());
        Assert.assertTrue(searchRequestFromSessionRequest.contains("Search One"));
        Assert.assertTrue(searchRequestFromSessionRequest.contains("Search Two"));
    }

    @Test
    public void testPassInNullParams(){
        SessionRequest sessionRequestDir = new SessionRequest(null);
        SessionRequest sessionRequestDirFile = new SessionRequest(null, null);
        SessionRequest sessionRequestDirFileSearch = new SessionRequest(null, null, null);

        Assert.assertEquals(null, sessionRequestDir.getDirectory());
        Assert.assertEquals("*.log", sessionRequestDir.getFile());
        Assert.assertTrue(sessionRequestDir.getSearchRequest().isEmpty());

        Assert.assertEquals(null, sessionRequestDirFile.getDirectory());
        Assert.assertEquals(null, sessionRequestDirFile.getFile());
        Assert.assertTrue(sessionRequestDirFile.getSearchRequest().isEmpty());

        Assert.assertEquals(null, sessionRequestDirFileSearch.getDirectory());
        Assert.assertEquals(null, sessionRequestDirFileSearch.getFile());
        Assert.assertEquals(null, sessionRequestDirFileSearch.getSearchRequest());
    }
}
