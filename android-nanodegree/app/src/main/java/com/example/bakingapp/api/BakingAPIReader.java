package com.example.bakingapp.api;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by jose on 23/05/17.
 */

public class BakingAPIReader {

    public BakingAPI api;

    private String BASE_URL = "https://d17h27t6h515a5.cloudfront.net/";
    //private String BASE_URL = "http://192.168.0.19:3000/";

    public BakingAPIReader(OkHttpClient client){
        Retrofit adapter = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createAsync())
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
        this.api = adapter.create(BakingAPI.class);
    }
}
