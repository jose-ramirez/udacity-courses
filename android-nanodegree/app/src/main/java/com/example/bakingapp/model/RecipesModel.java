package com.example.bakingapp.model;

import com.example.bakingapp.MVP;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by jose on 12/07/17.
 */

public class RecipesModel implements MVP.Model {

    private BakingAppAPI api;

    private String BASE_URL = "https://d17h27t6h515a5.cloudfront.net/";

    public OkHttpClient client;

    public RecipesModel(){

        this.client = new OkHttpClient();

        Retrofit adapter = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createAsync())
            .addConverterFactory(GsonConverterFactory.create())
            .client(this.client)
            .build();

        this.api = adapter.create(BakingAppAPI.class);
    }

    @Override
    public BakingAppAPI getApi() {
        return this.api;
    }
}