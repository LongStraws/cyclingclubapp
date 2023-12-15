package com.example.project31;

import org.junit.Test;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.*;

public class AddClubUnitTest {

    Boolean result = AddClubActivity.refreshGameDetailsUnitTest("test", "test", "test", "test", "test", "test", "test", "test");

    @Test
    public void testInvalidClub(){
        assertThat(result, is(false));
    }
}
