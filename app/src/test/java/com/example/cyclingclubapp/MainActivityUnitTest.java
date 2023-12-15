package com.example.cyclingclubapp;

import org.junit.Test;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.*;
public class MainActivityUnitTest {
    private static final String USERNAME_STRING = "true";


    @Test
    public void testDeletionEvent(){
        String result = MainActivity.deleteEventTest("NxVsLkJKDjal3KLA3");

        assertThat(result, is(USERNAME_STRING));
    }
}
