package com.example.bakingapp.di;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;

import com.example.bakingapp.MVP;
import com.example.bakingapp.presenter.RecipesPresenter;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by jose on 25/05/17.
 */

@Module
public class RecipesActivityModule {

    private static final int DEFAULT_GRID_COLUMNS = 3;

    private final Context context;

    public RecipesActivityModule(Context context){
        this.context = context;
    }

    @Provides
    public Context context(){
        return this.context;
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

    @Provides
    @Singleton
    public MVP.Presenter providePresenter(Context context){
        MVP.Presenter presenter = new RecipesPresenter();
        return presenter;
    }
}
