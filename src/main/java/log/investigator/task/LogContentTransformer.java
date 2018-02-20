package log.investigator.task;


import log.investigator.utility.LogLevels;
import com.joestelmach.natty.*;
import log.investigator.utility.DateValidator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DateFormatSymbols;
import java.util.HashMap;
import java.util.List;

public class LogContentTransformer {
    private InputStream fileContent;
    private DateFormatSymbols dateFormatSymbols = DateFormatSymbols.getInstance();
    private HashMap<String, HashMap<String, String>> logData = new HashMap<>();
    private BufferedReader bufferedReader;
    private String storeLine = "";

    public LogContentTransformer(InputStream fileContent){
        this.fileContent = fileContent;
    }

    public HashMap<String, HashMap<String, String>> generateLogOutput() throws IOException {
        getLogDataFromFile();
        setOutputForLogData();

        return logData;
    }

    private void getLogDataFromFile(){
        this.bufferedReader = new BufferedReader(new InputStreamReader(fileContent));
    }

    private void setOutputForLogData() throws IOException {
        StringBuilder logLongerThanOneLine = new StringBuilder();
        int count = 0;
        String line;
        while ((line = bufferedReader.readLine()) != null){
            if(checkThatLineBeginsWithDate(line)) {
                if(logLongerThanOneLine.length() > 0){
                    logLongerThanOneLine.append(storeLine);
                    storeLine = logLongerThanOneLine.toString();
                    logLongerThanOneLine = new StringBuilder();
                }
                if(!storeLine.isEmpty()){
                    count++;
                    logData.put("line " + count, getDataFromLine(storeLine));
                }
            }else{
                logLongerThanOneLine.append(storeLine).append("\n");
            }
            storeLine = line;
        }

        if(logLongerThanOneLine.length() > 0){
            logLongerThanOneLine.append(storeLine);
            storeLine = logLongerThanOneLine.toString();
        }
        if(!storeLine.isEmpty()){
            count++;
            logData.put("line " + count, getDataFromLine(storeLine));
        }
    }

    private boolean checkThatLineBeginsWithDate(String line){
        String firstWord = line.split(" ", 2)[0];
        DateValidator dateValidator = new DateValidator();
        boolean lineBeginsWithDate = dateValidator.validate(line);

        if(!lineBeginsWithDate) {
            lineBeginsWithDate = wordIsDayAbbreviatedCheck(firstWord);
        }

        if(!lineBeginsWithDate) {
            lineBeginsWithDate = wordIsMonthAbbreviatedCheck(firstWord);
        }

        return lineBeginsWithDate;
    }

    private boolean wordIsDayAbbreviatedCheck(String word){
        String [] daysAbbreviation = dateFormatSymbols.getShortWeekdays();

        for (String day: daysAbbreviation) {
            if (!day.isEmpty() && word.startsWith(day)){
                return true;
            }
        }

        return false;
    }

    private boolean wordIsMonthAbbreviatedCheck(String word){
        String [] monthsAbbreviation = dateFormatSymbols.getShortMonths();

        for (String month: monthsAbbreviation) {
            if (!month.isEmpty() && word.startsWith(month)){
                return true;
            }
        }

        return false;
    }

    HashMap<String, String> getDataFromLine(String line){
        LogLevels logLevels = new LogLevels();
        String [] splitLine;
        HashMap<String, String> data = new HashMap<>();

        for (String level: logLevels.getLogLevels()) {
            if(line.contains(level)){

                data.put("type", level);

                splitLine = line.split(level, 2);
                data.put("date", getDateFromLine(splitLine[0].trim()));
                data.put("message", getMessageFromLine(splitLine[1].trim()));
                break;
            }
        }

        return data;
    }

    String getDateFromLine(String line){
        Parser parser = new Parser();
        List<DateGroup> dates = parser.parse(line);
        String date = "";
        for (DateGroup dateGroup: dates) {
            date = dateGroup.getDates().get(0).toString();
        }

        return date;
    }

    private String getMessageFromLine(String line){
        if(line.startsWith("]")){
            line = line.substring(1).trim();
        }

        return line;
    }

}
