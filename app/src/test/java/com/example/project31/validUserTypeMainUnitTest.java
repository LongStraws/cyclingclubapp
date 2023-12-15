package com.example.project31;

import org.junit.Test;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.*;
public class validUserTypeMainUnitTest {
    private static final String USERNAME_STRING = "false";
    private static final String USERNAME_STRING_FAlSE = "true";

    @Test
    public void testIfUserTypeAdmin(){
        String result = MainActivity.validateUserType("ADMIN");

        assertThat(result, is(USERNAME_STRING_FAlSE));
    }

    @Test
    public void testIfUserTypeNotAdmin(){
        String result = MainActivity.validateUserType("CLUB");
        String result2 = MainActivity.validateUserType("PARTICIPANT");
        assertThat(result, is(USERNAME_STRING));
        assertThat(result2, is(USERNAME_STRING));
    }
}
