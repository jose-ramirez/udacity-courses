package com.example.bakingapp.api;

import com.example.bakingapp.data.Recipe;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;

/**
 * Created by jose on 23/05/17.
 */

public interface BakingAPI {
    String BASE_PATH = "/topher/2017/May/59121517_baking/baking.json";
    //String BASE_PATH = "/empty";

    @GET(BASE_PATH)
    Observable<List<Recipe>> getRecipes();
}
