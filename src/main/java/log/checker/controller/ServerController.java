package log.checker.controller;


import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.SftpException;
import io.swagger.annotations.ApiOperation;
import log.checker.exception.ServerServiceException;
import log.checker.service.ServerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/server")
public class ServerController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ServerController.class);

    @Resource private ServerService serverService;

    @ApiOperation(value="Server Connection Authentication", notes="Used to Authenticate connection to Highwire Servers",
            responseContainer = "String")
    @RequestMapping(value="/authentication", method = RequestMethod.GET,
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.TEXT_PLAIN_VALUE})
    @ResponseBody
    public String serverAuthentication(
            @RequestParam String username,
            @RequestParam String password,
            @RequestParam(defaultValue = "hw-bp-app-1.highwire.org", required = false) String hostname,
            @RequestParam(defaultValue = "22", required = false) int port
    ){
        String response = "{\"server authentication\" : \"";
        String errorMessage = "";
        boolean serverConnected = false;

        try {
            serverConnected = serverService.loginAuthentication(username, password, hostname, port);
        } catch (ServerServiceException | JSchException e) {
            errorMessage = e.getMessage();
        }

        response += serverConnected + "\"";
        if(!errorMessage.isEmpty()){
            response += ", \"error message\" : \"" + errorMessage + "\"";
            LOGGER.error(errorMessage);
        }
        response += "}";
        return response;
    }

    @ApiOperation(value="Available Files in Directory", notes="This displays all the available files in the directory you search with.",
            responseContainer = "String")
    @RequestMapping(value="/getAvailableLogFiles", method = RequestMethod.GET,
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.TEXT_PLAIN_VALUE})
    @ResponseBody
    public String getFilesInDirectory(
            @RequestParam String username,
            @RequestParam String password,
            @RequestParam(defaultValue = "hw-bp-app-1.highwire.org", required = false) String hostname,
            @RequestParam(defaultValue = "22", required = false) int port,
            @RequestParam(defaultValue = "/var/log/bp/", required = false) String directory,
            @RequestParam(defaultValue = "*.log", required = false) String file
    ){
        List<String> ListOfFiles = new ArrayList<>();
        StringBuilder response = new StringBuilder("{");
        String errorMessage = "";

        try {
            ListOfFiles = serverService.getAvailableFilesInDirectory(username, password, hostname, port, directory, file);
        } catch (ServerServiceException | JSchException | SftpException e) {
            errorMessage = e.getMessage();
        }

        for(int count = 0; (ListOfFiles.size() - 1) > count; count++){
            String filename = ListOfFiles.get(count);
            response.append("\"File").append(count + 1).append("\" : \"").append(filename).append("\"");
            if(count != (ListOfFiles.size() - 2)){
                response.append(", ");
            }
        }

        if(!errorMessage.isEmpty()){
            response.append("\"error message\" : \"").append(errorMessage).append("\"");
            LOGGER.error(errorMessage);
        }
        
        response.append("}");
        return response.toString();
    }


    @ApiOperation(value = "Upload Log file and get data", notes="This returns all the data from the log file that's uploaded.")
    @RequestMapping(value = "/uploadLogFile", method = RequestMethod.POST,
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.TEXT_PLAIN_VALUE})
    @ResponseBody
    public String UploadFileAndGetData(@RequestParam("file") MultipartFile file){


        return "{\"Result\":\"Yeah\"}";
    }

}
