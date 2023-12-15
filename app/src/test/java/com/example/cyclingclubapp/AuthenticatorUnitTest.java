package com.example.cyclingclubapp;

import org.junit.Test;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.*;

public class AuthenticatorUnitTest {
    Authenticator loginAuthenticator = new Authenticator();
    Boolean result = loginAuthenticator.validatePasswordUnitTest("password");
    Boolean result2 = loginAuthenticator.validatePasswordUnitTest("Test1@");

    @Test
    public void testInvalidPassword(){
        assertThat(result, is(false));
    }

    @Test
    public void testValidPassword(){
        assertThat(result2, is(true));
    }
}
