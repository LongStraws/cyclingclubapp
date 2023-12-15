package com.example.project31;

import org.junit.Test;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.*;

public class GameDetailsUnitTest {
    GameDetails gameDetails = new GameDetails();
    String result = gameDetails.getGameId();

    @Test
    public void testNullGameDetail(){
        assertThat(result, is(nullValue()));
    }
}
