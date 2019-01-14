package com.example.bakingapp.di;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;

import com.example.bakingapp.MVP;
import com.example.bakingapp.R;
import com.example.bakingapp.model.Recipe;
import com.example.bakingapp.presenter.RecipesPresenter;
import com.example.bakingapp.view.activity.recipes.RecipesAdapter;

import java.util.ArrayList;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by jose on 25/05/17.
 */

@Module
public class RecipesActivityModule {

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
    public GridLayoutManager provideGridLayoutManager(Context context){
        return new GridLayoutManager(context, this.context.getResources().getInteger(R.integer.column_span));
    }

    @Provides
    @Singleton
    public MVP.Presenter providePresenter(Context context){
        MVP.Presenter presenter = new RecipesPresenter();
        return presenter;
    }

    @Provides
    @Singleton
    public RecipesAdapter provideRecipeAdapter(){
        return new RecipesAdapter(new ArrayList<Recipe>());
    }
}
