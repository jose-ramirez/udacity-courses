package com.example.bakingapp;

import android.content.Context;
import android.view.View;

import com.example.bakingapp.model.BakingAppAPI;
import com.example.bakingapp.model.Recipe;

import java.util.List;

/**
 * Created by jose on 16/06/17.
 */

public interface MVP {

    public interface Model {
        BakingAppAPI getApi();
    }

    public interface Presenter {
        Context getContext();
        List<Recipe> getRecipeList();
        void getRecipes();
        void updateFavorite(android.view.View view);
        void setView(MVP.View view);
    }

    public interface View {
        void showProgressBar(boolean visibility);
        void updateRecipesList();
        void updateFavorite(android.view.View view, boolean isFavorite);
        void showRecipes(boolean visibility);
        void showErrorMessageView(String message);
    }

}
