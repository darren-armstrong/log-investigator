package log.investigator.task;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.HashMap;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

public class LogContentTransformerTests {
    private ClassLoader classLoader;

    @Before
    public void setUp() throws Exception {
        Logger.getRootLogger().setLevel(Level.OFF);

        classLoader = Thread.currentThread().getContextClassLoader();
    }

    // Test transform log file
    @Test
    public void testTransformDataValidLogOne() throws IOException {
        HashMap<String, HashMap<String, String>> logData;
        String path = classLoader.getResource("log_test_file.log").getFile();
        File initialFile = new File(path);
        InputStream targetStream = new FileInputStream(initialFile);

        LogContentTransformer logContentTransformer = new LogContentTransformer(targetStream);
        LogContentTransformer logContentTransformerSpy = spy(logContentTransformer);

        doReturn(
                new SimpleDateFormat("dd/MM/yyyy HH:mm:ss"
                ).format(new java.util.Date())).when(logContentTransformerSpy).getDateFromLine(anyString());

        logData = logContentTransformerSpy.generateLogOutput();

        assertFalse(logData.isEmpty());
    }

    @Test
    public void testTransformDataValidLogTwo() throws IOException {
        HashMap<String, HashMap<String, String>> logData;
        String path = classLoader.getResource("log_test_file_two").getFile();
        File initialFile = new File(path);
        InputStream targetStream = new FileInputStream(initialFile);

        LogContentTransformer logContentTransformer = new LogContentTransformer(targetStream);
        LogContentTransformer logContentTransformerSpy = spy(logContentTransformer);

        doReturn(
                new SimpleDateFormat("dd/MM/yyyy HH:mm:ss"
                ).format(new java.util.Date())).when(logContentTransformerSpy).getDateFromLine(anyString());

        logData = logContentTransformerSpy.generateLogOutput();

        assertFalse(logData.isEmpty());
    }

    @Test
    public void testTransformDataValidLogThree() throws IOException {
        HashMap<String, HashMap<String, String>> logData;
        String path = classLoader.getResource("log_test_file_three.log").getFile();
        File initialFile = new File(path);
        InputStream targetStream = new FileInputStream(initialFile);

        LogContentTransformer logContentTransformer = new LogContentTransformer(targetStream);
        LogContentTransformer logContentTransformerSpy = spy(logContentTransformer);

        doReturn(new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new java.util.Date())).when(logContentTransformerSpy).getDateFromLine(anyString());

        logData = logContentTransformerSpy.generateLogOutput();

        assertFalse(logData.isEmpty());
    }

    @Test
    public void testTransformDataValidLogFour() throws IOException {
        HashMap<String, HashMap<String, String>> logData;
        String path = classLoader.getResource("log_test_four.log").getFile();
        File initialFile = new File(path);
        InputStream targetStream = new FileInputStream(initialFile);

        LogContentTransformer logContentTransformer = new LogContentTransformer(targetStream);
        LogContentTransformer logContentTransformerSpy = spy(logContentTransformer);

        doReturn(new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new java.util.Date())).when(logContentTransformerSpy).getDateFromLine(anyString());

        logData = logContentTransformerSpy.generateLogOutput();

        assertFalse(logData.isEmpty());
    }

    @Test
    public void testTransformDataValidLoWithWeirdDates() throws IOException {
        HashMap<String, HashMap<String, String>> logData;
        String path = classLoader.getResource("logsWithDifferentDateTypes.log").getFile();
        File initialFile = new File(path);
        InputStream targetStream = new FileInputStream(initialFile);

        LogContentTransformer logContentTransformer = new LogContentTransformer(targetStream);
        LogContentTransformer logContentTransformerSpy = spy(logContentTransformer);

        logData = logContentTransformerSpy.generateLogOutput();

        assertFalse(logData.isEmpty());
    }

    // Test Get Data From Line
    @Test
    public void testOneGetDataFromLine(){
        HashMap<String, String> dataFromLine;
        String lineFromLog = "Jan 24 04:29:47 hw-bp-app-1.highwire.org [amjsports][Wed Jan 24 04:29:47 PST 2018][DEBUG]" +
                "[org.highwire.mstr.ejbsupport.query.CritItemFieldData][167] SELECTION_FIELD_VERSION= null";
        String expectedDate = "Wed Jan 24 12:29:47 GMT 2018";
        String expectedLogType = "DEBUG";
        String expectedLogMessage = "[org.highwire.mstr.ejbsupport.query.CritItemFieldData][167] SELECTION_FIELD_VERSION= null";


        LogContentTransformer logContentTransformer = new LogContentTransformer(null);

        dataFromLine = logContentTransformer.getDataFromLine(lineFromLog);

        assertEquals(expectedDate, dataFromLine.get("date"));
        assertEquals(expectedLogType, dataFromLine.get("type"));
        assertEquals(expectedLogMessage, dataFromLine.get("message"));

    }

    @Test
    public void testTwoGetDataFromLine(){
        HashMap<String, String> dataFromLine;
        String lineFromLog = "Jan 24 04:29:26 hw-bp-app-1.highwire.org [amjsports][Wed Jan 24 04:29:26 PST 2018][INFO]" +
                "[org.highwire.dtl.DTLRunner][43] Concluding DTL processing [queues (org.highwire.mstr.client.tracking.ListQueuesServlet): " +
                "GET http://amjsports-bp-dev.highwiretest.com/benchpress/tracking/queues?request_journal_code=amjsports&getRole=true; " +
                "Referer: https://amjsports-bp-dev.highwiretest.com/submission/queues?MSTRServlet.emailAddr=darmstrong@highwire.org]" +
                " of /highwire-dev/journals/mstr/templates/tracking/list_queues.dtl";
        String expectedDate = "Wed Jan 24 12:29:26 GMT 2018";
        String expectedLogType = "INFO";
        String expectedLogMessage = "[org.highwire.dtl.DTLRunner][43] Concluding DTL processing [queues " +
                "(org.highwire.mstr.client.tracking.ListQueuesServlet): GET http://amjsports-bp-dev.highwiretest.com/benchpress/tracking/queues?request_journal_code=amjsports&getRole=true; " +
                "Referer: https://amjsports-bp-dev.highwiretest.com/submission/queues?MSTRServlet.emailAddr=darmstrong@highwire.org]" +
                " of /highwire-dev/journals/mstr/templates/tracking/list_queues.dtl";

        LogContentTransformer logContentTransformer = new LogContentTransformer(null);

        dataFromLine = logContentTransformer.getDataFromLine(lineFromLog);

        assertEquals(expectedDate, dataFromLine.get("date"));
        assertEquals(expectedLogType, dataFromLine.get("type"));
        assertEquals(expectedLogMessage, dataFromLine.get("message"));
    }

    @Test
    public void testThreeGetDataFromLine(){
        HashMap<String, String> dataFromLine;
        String lineFromLog = "Mon Jan 15 09:50:11 PST 2018][ERROR][org.highwire.mstr.client.misc.PortalJobQueueClient] " +
                "MSTRJournalManager.getJournalGroup(journal) is NULL";
        String expectedDate = "Mon Jan 15 17:50:11 GMT 2018";
        String expectedLogType = "ERROR";
        String expectedLogMessage = "[org.highwire.mstr.client.misc.PortalJobQueueClient] " +
                "MSTRJournalManager.getJournalGroup(journal) is NULL";

        LogContentTransformer logContentTransformer = new LogContentTransformer(null);

        dataFromLine = logContentTransformer.getDataFromLine(lineFromLog);

        assertEquals(expectedDate, dataFromLine.get("date"));
        assertEquals(expectedLogType, dataFromLine.get("type"));
        assertEquals(expectedLogMessage, dataFromLine.get("message"));

    }

    @Test
    public void testFourGetDataFromLine(){
        HashMap<String, String> dataFromLine;
        String lineFromLog = "Tue Jan 23 23:55:08 PST 2018][ALERT][org.highwire.mstr.production.iThenticateProcessor]" +
                "[main] Using server https://api.ithenticate.com/rpc";
        String expectedDate = "Wed Jan 24 07:55:08 GMT 2018";
        String expectedLogType = "ALERT";
        String expectedLogMessage = "[org.highwire.mstr.production.iThenticateProcessor]" +
                "[main] Using server https://api.ithenticate.com/rpc";

        LogContentTransformer logContentTransformer = new LogContentTransformer(null);

        dataFromLine = logContentTransformer.getDataFromLine(lineFromLog);

        assertEquals(expectedDate, dataFromLine.get("date"));
        assertEquals(expectedLogType, dataFromLine.get("type"));
        assertEquals(expectedLogMessage, dataFromLine.get("message"));

    }
}
