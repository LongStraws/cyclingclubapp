package com.example.project31;


import org.junit.Test;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.*;

public class RegisterUnitTest {
    private static final String USERNAME_STRING = "false";
    private static final String USERNAME_STRING_FAlSE = "true";


    @Test
    public void testIfUsernameAdmin(){
        String result = Register.usernameNotAdmin("admin");

        assertThat(result, is(USERNAME_STRING));
    }

    @Test
    public void testIfUsernameNotAdmin(){
        String result = Register.usernameNotAdmin("anythingelse");

        assertThat(result, is(USERNAME_STRING_FAlSE));
    }

}