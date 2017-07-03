package com.example.bakingapp.di;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;

import com.example.bakingapp.RecipeAdapter;
import com.example.bakingapp.api.BakingAPI;
import com.example.bakingapp.api.BakingAPIReader;
import com.example.bakingapp.data.Recipe;

import java.util.ArrayList;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;

/**
 * Created by jose on 25/05/17.
 */

@Module
public class MainActivityModule {

    private static final int DEFAULT_GRID_COLUMNS = 3;

    private final Context context;

    public MainActivityModule(Context context){
        this.context = context;
    }

    @Provides
    public Context context(){
        return this.context;
    }

    @Provides
    @Singleton
    public OkHttpClient provideClient(){
        return new OkHttpClient();
    }

    @Provides
    @Singleton
    public BakingAPI provideAPI(OkHttpClient client){
        return new BakingAPIReader(client).api;
    }

    @Provides
    @Singleton
    public RecipeAdapter provideRecipeAdapter(){
        return new RecipeAdapter(new ArrayList<Recipe>());
    }

    @Provides
    @Singleton
    public LinearLayoutManager provideLayoutManager(Context context){
        return new LinearLayoutManager(context);
    }

    @Provides
    @Singleton
    public GridLayoutManager provideGridLayoutManager(Context context){
        return new GridLayoutManager(context, DEFAULT_GRID_COLUMNS);
    }
}
