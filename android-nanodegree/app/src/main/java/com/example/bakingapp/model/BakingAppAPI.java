package com.example.bakingapp.model;

import com.example.bakingapp.model.Recipe;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;

/**
 * Created by jose on 23/05/17.
 */

public interface BakingAppAPI {
    String BASE_PATH = "/topher/2017/May/59121517_baking/baking.json";

    @GET(BASE_PATH)
    Observable<List<Recipe>> getRecipes();
}
