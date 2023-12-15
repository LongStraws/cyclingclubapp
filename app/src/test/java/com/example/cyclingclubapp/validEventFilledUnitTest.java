package com.example.cyclingclubapp;

import org.junit.Test;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.*;
public class validEventFilledUnitTest{
    private static final String USERNAME_STRING = "true";
    private static final String USERNAME_STRING_FAlSE = "false";



    @Test
    public void testIfEventValid(){
        String result = MainActivity.isValidInputTest("CLUB", "PARTICIPANT", "EVENT", "DATE", "TIME", "LOCATION", "DESCRIPTION", "CONTACT", "CAPACITY", "COST", "true");

        assertThat(result, is(USERNAME_STRING_FAlSE));
    }
}
