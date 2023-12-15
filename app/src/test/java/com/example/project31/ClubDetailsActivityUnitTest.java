package com.example.project31;

import org.junit.Test;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.*;

public class ClubDetailsActivityUnitTest {
    Boolean result = ClubDetailsActivity.isRightGameUnitTest("test", "test", "test", "test", "test", "test", "test", "test");

    @Test
    public void testInvalidGame(){
        assertThat(result, is(false));
    }
}
