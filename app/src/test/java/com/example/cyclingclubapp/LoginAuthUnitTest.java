package com.example.cyclingclubapp;

import org.junit.Test;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.*;

public class LoginAuthUnitTest {
    LoginAuthenticator loginAuthenticator = new LoginAuthenticator();
    Boolean result = loginAuthenticator.allValid();

    @Test
    public void testIfAllValid(){
        assertThat(result, is(false));
    }
}
