package com.example.bakingapp.di;

/*
  Created by jose on 25/05/17.
 */

import android.content.Context;

import com.example.bakingapp.view.activity.recipes.RecipesActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules={RecipesActivityModule.class})
public interface RecipesActivityComponent {

    Context context();

    void inject(RecipesActivity activity);
}
