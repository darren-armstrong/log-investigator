package log.investigator.model;


import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ServerLogInDetailsTest {

    @Before
    public void setUp() throws Exception {
        Logger.getRootLogger().setLevel(Level.OFF);
    }

    @Test
    public void PassThroughNulls(){
        ServerLogInDetails serverLogInDetails = new ServerLogInDetails(null, null, null);

        assertEquals(null, serverLogInDetails.getUsername());
        assertEquals(null, serverLogInDetails.getPassword());
        assertEquals(null, serverLogInDetails.getHostname());
        assertEquals(22, serverLogInDetails.getPort());
    }

    @Test
    public void PassThroughEmptyParams(){
        ServerLogInDetails serverLogInDetails = new ServerLogInDetails("", "", "");

        assertEquals("", serverLogInDetails.getUsername());
        assertEquals("", serverLogInDetails.getPassword());
        assertEquals("", serverLogInDetails.getHostname());
        assertEquals(22, serverLogInDetails.getPort());
    }

    @Test
    public void PassThroughEmptyParamsAndSetPortToMinusOne(){
        ServerLogInDetails serverLogInDetails = new ServerLogInDetails("", "", "", -1);

        assertEquals("", serverLogInDetails.getUsername());
        assertEquals("", serverLogInDetails.getPassword());
        assertEquals("", serverLogInDetails.getHostname());
        assertEquals(-1, serverLogInDetails.getPort());
    }

    @Test
    public void PassThroughDataInParams(){
        ServerLogInDetails serverLogInDetails = new ServerLogInDetails("username", "password", "hostname");

        assertEquals("username", serverLogInDetails.getUsername());
        assertEquals("password", serverLogInDetails.getPassword());
        assertEquals("hostname", serverLogInDetails.getHostname());
        assertEquals(22, serverLogInDetails.getPort());
    }

    @Test
    public void ChangePortNumber(){
        ServerLogInDetails serverLogInDetails = new ServerLogInDetails("username", "password", "hostname", 8080);

        assertEquals("username", serverLogInDetails.getUsername());
        assertEquals("password", serverLogInDetails.getPassword());
        assertEquals("hostname", serverLogInDetails.getHostname());
        assertEquals(8080, serverLogInDetails.getPort());
    }

}
