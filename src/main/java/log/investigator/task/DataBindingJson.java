package log.investigator.task;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.HashMap;

public class DataBindingJson {
    private Object convertToJson;

    public String convertObjectToJson(Object convertToJson) throws JsonProcessingException{
        this.convertToJson = convertToJson;
        ObjectMapper objectMapper = new ObjectMapper();

        if(checkForStringClass()){
            convertToJson = convertStringToHashmap((String) convertToJson);
        }

        return objectMapper.writeValueAsString(convertToJson);
    }

    public Object convertJsonToObject(String jsonString) throws IOException {
        ObjectMapper mapper = new ObjectMapper();

        return mapper.readValue(jsonString, Object.class);
    }

    private boolean checkForStringClass(){
        return convertToJson.getClass().equals(String.class);
    }

    private HashMap<String, String> convertStringToHashmap(String message){
        HashMap<String, String> connectMessageToKey = new HashMap<>();
        connectMessageToKey.put("message", (String) convertToJson);

        return connectMessageToKey;
    }

}
