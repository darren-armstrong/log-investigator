package log.checker.validator;

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

import static junit.framework.TestCase.assertFalse;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.spy;


public class MultipartFileValidatorTests {

    @Before
    public void setUp() {
        Logger.getRootLogger().setLevel(Level.OFF);
    }

    @Test(expected = IllegalArgumentException.class)
    public void passThroughNullMultipart() {
        MultipartFileValidator multipartFileValidator = new MultipartFileValidator();
        BeanPropertyBindingResult result = new BeanPropertyBindingResult(null, "MultipartFile");
        ValidationUtils.invokeValidator(multipartFileValidator, null, result);

    }

    @Test(expected = IllegalArgumentException.class)
    public void attemptToValidateAFileInsteadOfMultipartFile(){
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        String path = classLoader.getResource("log_test_file_two").getFile();
        File file = new File(path);

        MultipartFileValidator multipartFileValidator = new MultipartFileValidator();
        BeanPropertyBindingResult result = new BeanPropertyBindingResult(file, "MultipartFile");
        ValidationUtils.invokeValidator(multipartFileValidator, file, result);

        
    }

    @Test(expected = IllegalArgumentException.class)
    public void validateANullMultipartFile(){
        MultipartFile multipartFile = null;

        MultipartFileValidator multipartFileValidator = new MultipartFileValidator();
        BeanPropertyBindingResult result = new BeanPropertyBindingResult(multipartFile, "MultipartFile");
        ValidationUtils.invokeValidator(multipartFileValidator, multipartFile, result);
    }

    @Test
    public void validateValidManuscriptFile() throws IOException {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        String path = classLoader.getResource("log_test_file_two").getFile();
        File file = new File(path);
        String name = "log_test_file.log";
        String originalFileName = "log_test_file.log";
        String contentType = "text/plain";
        byte[] content = Files.readAllBytes(file.toPath());

        MultipartFile multipartFile = new MockMultipartFile(name,
                originalFileName, contentType, content);

        MultipartFileValidator multipartFileValidator = new MultipartFileValidator();
        MultipartFileValidator spyMultipartFileValidator = spy(multipartFileValidator);
        doReturn(true).when(spyMultipartFileValidator).supports(anyObject());
        BeanPropertyBindingResult result = new BeanPropertyBindingResult(multipartFile, "MultpartFile");
        ValidationUtils.invokeValidator(spyMultipartFileValidator, multipartFile, result);

        assertFalse(result.hasErrors());

    }

}
