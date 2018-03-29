package log.investigator.task;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;

import static org.junit.Assert.assertEquals;

public class DataBindingJsonTests {

    private DataBindingJson dataBindingJson = new DataBindingJson();

    @Before
    public void setUp() throws Exception {
        Logger.getRootLogger().setLevel(Level.OFF);
    }

    @Test
    public void TestStringConvertedToJson() throws JsonProcessingException {
        String expectedJson = "{\"message\":\"what will happen when I put this object through\"}";
        String passedThroughObject = "what will happen when I put this object through";
        String returnedJson = dataBindingJson.convertObjectToJson(passedThroughObject);

        assertEquals(expectedJson, returnedJson);
    }

    @Test
    public void straightForwardHashMapToJson() throws JsonProcessingException {
        String expectedJson = "{\"1\":\"test value\",\"2\":\"\\\"{\\\"Test\\\" : \\\"test\\\"}\\\"\"}";

        HashMap<Integer, String> passedThroughObject = new HashMap<>();
        passedThroughObject.put(1, "test value");
        passedThroughObject.put(2, "\"{\"Test\" : \"test\"}\"");

        String returnedJson = dataBindingJson.convertObjectToJson(passedThroughObject);

        assertEquals(expectedJson, returnedJson);
    }

    @Test
    public void nestedHashMapToJson() throws JsonProcessingException {
        String expectedJson = "{\"1\":{\"test2\":\"test\",\"Test\":\"test\"},\"2\":{\"test2\":\"test\",\"Test\":\"test\"}}";

        HashMap<Integer, HashMap<String, String>> passedThroughObject = new HashMap<>();
        HashMap<String, String> nestedHashMap = new HashMap<>();
        nestedHashMap.put("Test", "test");
        nestedHashMap.put("test2", "test");
        passedThroughObject.put(1, nestedHashMap);
        passedThroughObject.put(2, nestedHashMap);

        String returnedJson = dataBindingJson.convertObjectToJson(passedThroughObject);

        assertEquals(expectedJson, returnedJson);
    }

    @Test
    public void convertJsonToObject() throws IOException {
        HashMap<Integer, HashMap<String, String>> returnedHashMap;
        HashMap<Integer, HashMap<String, String>> expectedObject = new HashMap<>();
        HashMap<String, String> nestedHashMap = new HashMap<>();
        nestedHashMap.put("Test", "test");
        nestedHashMap.put("test2", "test");
        expectedObject.put(1, nestedHashMap);
        expectedObject.put(2, nestedHashMap);

        String json = "{\"1\":{\"test2\":\"test\",\"Test\":\"test\"},\"2\":{\"test2\":\"test\",\"Test\":\"test\"}}";

        returnedHashMap = (HashMap<Integer, HashMap<String, String>>) dataBindingJson.convertJsonToObject(json);

        assertEquals(expectedObject.size(), returnedHashMap.size());
    }
}
