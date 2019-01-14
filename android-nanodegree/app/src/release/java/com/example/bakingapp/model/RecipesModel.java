package com.example.bakingapp.model;

import com.example.bakingapp.BuildConfig;
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

    public OkHttpClient client;

    public RecipesModel(){

        this.client = new OkHttpClient();

        Retrofit adapter = new Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
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