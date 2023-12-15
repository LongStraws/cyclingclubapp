package com.example.cyclingclubapp;


import org.junit.Test;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.*;

public class LoginUnitTest {
    private static final String USERNAME_EXISTS = "true";


    @Test
    public void testValidUsername(){
        String result = Login.validateUsername("enthusiastically");

        assertThat(result, is(USERNAME_EXISTS));
    }


}
