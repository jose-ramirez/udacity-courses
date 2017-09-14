package com.example.bakingapp.model;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;

/**
 * Created by jose on 23/05/17.
 */

public interface BakingAppAPI {

    // The path for the recipes' info
    String BASE_PATH = "/topher/2017/May/59121517_baking/baking.json";

    /**
     * Get all the recipes' data :)
     *
     * @return An Observable to deal with the recipes' data reactively.
     */
    @GET(BASE_PATH)
    Observable<List<Recipe>> getRecipes();
}
