package com.ex.popularmovies;

import com.ex.popularmovies.common.MovieGetter;

import org.junit.Before;
import org.junit.Test;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class MovieAPITest {

    private MovieGetter mg;

    @Before
    public void setup(){
        this.mg = new MovieGetter(null);
    }

    @Test
    public void get_Movies(){
        //Call<List<Movie>> res = this.mg.getMovie(550);
        //assertTrue(films.size() == 1);
    }
}