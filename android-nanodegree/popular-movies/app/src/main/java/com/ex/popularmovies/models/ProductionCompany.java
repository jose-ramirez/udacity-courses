package com.ex.popularmovies.models;

import java.io.Serializable;

/**
 * Created by jose on 29/01/17.
 */

public class ProductionCompany implements Serializable {

    private int id;

    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
