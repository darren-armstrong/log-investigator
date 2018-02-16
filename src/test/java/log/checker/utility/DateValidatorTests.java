package log.checker.utility;

import log.checker.utility.DateValidator;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class DateValidatorTests {
    private DateValidator dateValidator;

    @Before
    public void initData(){
        dateValidator = new DateValidator();
    }

    public String[] ValidDateProvider() {
        return new String[]{"1/1/2010", "01/01/2020", "31/1/2010", "31/01/2020", "29/2/2008", "29/02/2008", "28/2/2009", "28/02/2009",
                "31/3/2010", "31/03/2010", "30/4/2010", "30/04/2010", "31/5/2010", "31/05/2010", "30/6/2010", "30/06/2010",
                "31/7/2010", "31/07/2010", "31/8/2010", "31/08/2010", "30/9/2010", "30/09/2010", "31/10/2010", "31/10/2010",
                "30/11/2010", "30/11/2010", "31/12/2010", "31/12/2010", "1:1:2010", "01:01:2020", "31:1:2010", "31:01:2020",
                "29:2:2008", "29:02:2008", "28:2:2009", "28:02:2009", "31:3:2010", "31:03:2010", "30:4:2010", "30:04:2010",
                "31:5:2010", "31:05:2010", "30:6:2010", "30:06:2010", "31:7:2010", "31:07:2010", "31:8:2010", "31:08:2010",
                "30:9:2010", "30:09:2010", "31:10:2010", "31:10:2010", "30:11:2010", "30:11:2010", "31:12:2010", "31:12:2010",
                "1-1-2010", "01-01-2020", "31-1-2010", "31-01-2020", "29-2-2008", "29-02-2008", "28-2-2009", "28-02-2009",
                "31-3-2010", "31-03-2010", "30-4-2010", "30-04-2010", "31-5-2010", "31-05-2010", "30-6-2010", "30-06-2010",
                "31-7-2010", "31-07-2010", "31-8-2010", "31-08-2010", "30-9-2010", "30-09-2010", "31-10-2010", "31-10-2010",
                "30-11-2010", "30-11-2010", "31-12-2010", "31-12-2010", "2010-12-31"};
    }

    public String[] InvalidDateProvider() {
        return new String[]{
                "32/1/2010", "32/01/2020", "1/13/2010", "01/01/1820",
                "29/2/2007", "29/02/2007", "30/2/2008", "31/02/2008",
                "29/a/2008", "a/02/2008", "333/2/2008", "29/02/200a",
                "31/4/2010", "31/04/2010", "31/6/2010", "31/06/2010",
                "31/9/2010", "31/09/2010", "31/11/2010", "test", "tste",
                "0", "ninin", "29dij", "2hihihihih7", "language",
                "019090190909", "29/a/2ioi", "a/02/2iuiui", "333ni",
                "1010101", "121212", "f8f8f8f", "t8t8t8", "t1",
                "weeeeee", "01928374", "12-12-12aa1", "", "    "};
    }

    @Test
    public void ValidDateTest() {
        String[] validDates= ValidDateProvider();
        for (String date :validDates) {
            boolean valid = dateValidator.validate(date);


            assertEquals(true, valid);
        }

    }

    @Test
    public void InValidDateTest() {
        String[] invalidDates = InvalidDateProvider();
        for (String date :invalidDates) {
            boolean valid = dateValidator.validate(date);

            assertEquals(false, valid);
        }
    }

}
