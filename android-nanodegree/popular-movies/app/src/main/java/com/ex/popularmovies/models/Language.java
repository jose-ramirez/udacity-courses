package com.ex.popularmovies.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by jose on 29/01/17.
 */

public class Language implements Serializable {

    @SerializedName("iso_639_1")
    private String isoCode;

    private String name;

    public String getIsoCode() {
        return isoCode;
    }

    public void setId(String isoCode) {
        this.isoCode = isoCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
