package log.checker.controller;


import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class ApiStandardController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ServerController.class);
    private final String VERSION = "v1.0";

    @ApiOperation(value="Health Check",notes="Health status on the application",responseContainer = "String")
    @RequestMapping(value = "/healthcheck", method = RequestMethod.GET,
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.TEXT_PLAIN_VALUE})
    @ResponseBody
    public String healthcheck(@RequestHeader(value = "accept", required = false) String acceptHeader) {
        if (acceptHeader.equalsIgnoreCase(MediaType.APPLICATION_JSON_VALUE)) {
            return "{\"health\": \"ok\"}";
        } else {
            return "ok";
        }
    }

    @ApiOperation(value="Version",notes="Version of the application",responseContainer = "String")
    @RequestMapping(value = "/version", method = RequestMethod.GET,
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.TEXT_PLAIN_VALUE})
    @ResponseBody
    public String version(@RequestHeader(value = "accept", required = false) String acceptHeader) {
        if (acceptHeader.equalsIgnoreCase(MediaType.APPLICATION_JSON_VALUE)) {
            return "{\"version\": \"" + VERSION + "\"}";
        } else {
            return VERSION;
        }
    }

    @ApiOperation(value="Hello Highwire",notes="Hello Highwire Response",responseContainer = "String")
    @RequestMapping(value = {"/", "/greetings", "/helloworld"}, method = RequestMethod.GET,
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.TEXT_PLAIN_VALUE})
    @ResponseBody
    public String greetings(@RequestHeader(value = "accept", required = false) String acceptHeader){
        if (acceptHeader.equalsIgnoreCase(MediaType.APPLICATION_JSON_VALUE)) {
            return "{\"response\": \"Hello Highwire\"}";
        } else {
            return "Hello Highwire";
        }
    }
}
