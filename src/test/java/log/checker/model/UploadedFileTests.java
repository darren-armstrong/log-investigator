package log.checker.model;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.Assert.assertEquals;

public class UploadedFileTests {
    private ClassLoader classLoader;

    @Before
    public void setUp() throws Exception {
        Logger.getRootLogger().setLevel(Level.OFF);

        classLoader = Thread.currentThread().getContextClassLoader();
    }

    @Test
    public void logFilePassedThrough() throws IOException {
        String path = classLoader.getResource("log_test_file.log").getFile();
        File file = new File(path);
        String name = "log_test_file.log";
        String originalFileName = "log_test_file.log";
        String contentType = "text/plain";
        byte[] content = Files.readAllBytes(file.toPath());

        MultipartFile result = new MockMultipartFile(name,
                originalFileName, contentType, content);

        UploadedFile uploadedFile = new UploadedFile(result);


        assertEquals(result, uploadedFile.getFile());
        assertEquals("log", uploadedFile.getFileType());
        assertEquals("log_test_file.log", uploadedFile.getOriginalFileName());
        assertEquals("text/plain", uploadedFile.getContentType());
    }

    @Test
    public void fileWithNoFileTypePassedThrough() throws IOException {
        String path = classLoader.getResource("log_test_file_two").getFile();
        File file = new File(path);
        String name = "log_test_file";
        String originalFileName = "log_test_file";
        String contentType = "text/plain";
        byte[] content = Files.readAllBytes(file.toPath());

        MultipartFile result = new MockMultipartFile(name,
                originalFileName, contentType, content);

        UploadedFile uploadedFile = new UploadedFile(result);


        assertEquals(result, uploadedFile.getFile());
        assertEquals("", uploadedFile.getFileType());
        assertEquals("log_test_file", uploadedFile.getOriginalFileName());
        assertEquals("text/plain", uploadedFile.getContentType());
    }
}
