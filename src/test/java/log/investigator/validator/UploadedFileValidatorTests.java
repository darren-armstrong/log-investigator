package log.investigator.validator;

import log.investigator.model.UploadedFile;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.ValidationUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class UploadedFileValidatorTests {

    private MultipartFile result;

    @Before
    public void setUp() throws IOException, NullPointerException {
        Logger.getRootLogger().setLevel(Level.OFF);

        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
                String path = classLoader.getResource("log_test_file.log").getFile();
        File file = new File(path);
        String name = "log_test_file.log";
        String originalFileName = "log_test_file.log";
        String contentType = "text/plain";
        byte[] content = Files.readAllBytes(file.toPath());

        result = new MockMultipartFile(name,
                originalFileName, contentType, content);
    }

    @Test
    public void validFileUploaded(){
        UploadedFile uploadedFile = new UploadedFile(result);

        UploadedFileValidator uploadedFileValidator = new UploadedFileValidator();
        BeanPropertyBindingResult result = new BeanPropertyBindingResult(uploadedFile, "UploadedFile");
        ValidationUtils.invokeValidator(uploadedFileValidator, uploadedFile, result);

        assertFalse(result.hasErrors());
    }

    @Test
    public void nullFields(){
        UploadedFile uploadedFile = new UploadedFile(result);
        uploadedFile.setContentType(null);
        uploadedFile.setFileType(null);
        uploadedFile.setFile(null);
        uploadedFile.setOriginalFileName(null);

        UploadedFileValidator uploadedFileValidator = new UploadedFileValidator();
        BeanPropertyBindingResult result = new BeanPropertyBindingResult(uploadedFile, "UploadedFile");
        ValidationUtils.invokeValidator(uploadedFileValidator, uploadedFile, result);

        assertTrue(result.hasErrors());
    }

    @Test
    public void emptyStringFields(){
        UploadedFile uploadedFile = new UploadedFile(result);
        uploadedFile.setContentType("");
        uploadedFile.setFileType("");
        uploadedFile.setOriginalFileName("");

        UploadedFileValidator uploadedFileValidator = new UploadedFileValidator();
        BeanPropertyBindingResult result = new BeanPropertyBindingResult(uploadedFile, "UploadedFile");
        ValidationUtils.invokeValidator(uploadedFileValidator, uploadedFile, result);

        assertTrue(result.hasErrors());
    }

    @Test
    public void whitespaceOnlyStringFields(){
        UploadedFile uploadedFile = new UploadedFile(result);
        uploadedFile.setContentType("     ");
        uploadedFile.setFileType("    ");
        uploadedFile.setOriginalFileName("     ");

        UploadedFileValidator uploadedFileValidator = new UploadedFileValidator();
        BeanPropertyBindingResult result = new BeanPropertyBindingResult(uploadedFile, "UploadedFile");
        ValidationUtils.invokeValidator(uploadedFileValidator, uploadedFile, result);

        assertTrue(result.hasErrors());
    }

}
