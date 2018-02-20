package log.investigator.utility;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DateValidator {
    private Matcher matcher;
    private int datePatternCount, year;
    private String day, month;
    private boolean dateIsValid = false;

    private static final String dayRegex = "(0?[1-9]|[12]\\d|3[01])";
    private static final String monthRegex = "(0?[1-9]|1[012])";
    private static final String yearRegex = "(\\d{4})";
    private static final String separatorRegex = "(/|-|:)";

    private static final String DATE_PATTERN_D_M_Y = dayRegex + separatorRegex + monthRegex + separatorRegex + yearRegex;
    private static final String DATE_PATTERN_M_D_Y = monthRegex + separatorRegex + dayRegex + separatorRegex + yearRegex;
    private static final String DATE_PATTERN_Y_M_D = yearRegex + separatorRegex + monthRegex + separatorRegex + dayRegex;
    private static final String DATE_PATTERN_Y_D_M = yearRegex + separatorRegex + dayRegex + separatorRegex + monthRegex;

    private static final String[] DATE_PATTERNS = {DATE_PATTERN_D_M_Y, DATE_PATTERN_M_D_Y, DATE_PATTERN_Y_M_D, DATE_PATTERN_Y_D_M};

    public DateValidator(){ }

    /**
     * Validate date format with regular expression
     * @return true valid date format, false invalid date format
     */
    public boolean validate(final String date) {
        if(date != null && !date.isEmpty()){
            if (Character.isDigit(date.charAt(0))) {
                runDatePatternsForMatches(date);
            }
        }

        return dateIsValid;
    }

    private void runDatePatternsForMatches(final String date){
        datePatternCount = 1;

        for (String DATE_PATTERN : DATE_PATTERNS) {
            setDatePatternToMatch(DATE_PATTERN, date);

            if(matcher.matches()) {
                matcher.reset();
                if(matcher.find()) {
                    checkDateFromMatch();
                }
            }

            if(dateIsValid || isLastDatePattern()){
                break;
            }
        }

    }

    private void setDatePatternToMatch(String datePattern, String date){
        Pattern pattern = Pattern.compile(datePattern);
        matcher = pattern.matcher(date);
    }

    private boolean isLastDatePattern(){
        return datePatternCount++ == DATE_PATTERNS.length;
    }

    private void checkDateFromMatch() {
        setDayMonthYear();
        checkIfDayMonthYearIsValid();
    }

    private void setDayMonthYear(){
        day = matcher.group(1);
        month = matcher.group(3);
        year = Integer.parseInt(matcher.group(5));

        switch (datePatternCount) {
            case 2:
                day = matcher.group(3);
                month = matcher.group(1);
                break;
            case 3:
                day = matcher.group(5);
                month = matcher.group(3);
                year = Integer.parseInt(matcher.group(1));
                break;
            case 4:
                day = matcher.group(3);
                month = matcher.group(5);
                year = Integer.parseInt(matcher.group(1));
                break;
        }
    }

    private void checkIfDayMonthYearIsValid(){
        final String MAX_NUM_OF_DAYS = "31";
        if (day.equals(MAX_NUM_OF_DAYS) && !monthsLessThanThirtyOneDays()) {
            dateIsValid = true;
        }else if (!day.equals(MAX_NUM_OF_DAYS)){
            if(monthIsFebruary()){
                februaryCheck();
            }else {
                dateIsValid = true;
            }
        }
    }

    private boolean monthsLessThanThirtyOneDays(){

        return (month.equals("2") ||month.equals("4") || month.equals("6") || month.equals("9") ||
                month.equals("11") || month.equals("02") || month.equals("04") ||
                month.equals("06") || month.equals("09"));
    }

    private boolean monthIsFebruary(){
        return month.equals("2") || month.equals("02");
    }

    private void februaryCheck(){
        if (isLeapYear()) {
            dateIsValid = !day.equals("30");
        } else {
            dateIsValid = !day.equals("29") && !day.equals("30");
        }
    }

    private boolean isLeapYear(){
        return year % 4 == 0;
    }
}
