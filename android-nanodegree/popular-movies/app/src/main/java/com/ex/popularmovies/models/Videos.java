package com.ex.popularmovies.models;

import java.util.List;

/**
 * Created by jose on 15/03/17.
 */

public class Videos {

    private int id;

    private List<Video> results;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Video> getResults() {
        return results;
    }

    public void setResults(List<Video> results) {
        this.results = results;
    }
}
