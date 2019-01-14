package com.example.bakingapp;

import android.content.Context;

import com.example.bakingapp.model.BakingAppAPI;
import com.example.bakingapp.model.Recipe;

import java.util.List;

/**
 * Created by jose on 16/06/17.
 */

public interface MVP {

    public interface Model {
        /**
         * The object that asks the server for the recipes.
         * @return
         */
        BakingAppAPI getApi();
    }

    public interface Presenter {
        /**
         * To avoid memory leaks if the activity ends up being marked for garbage collection
         */
        void unsubscribe();

        /**
         * To test whether ther's an internet connection or not, mostly
         *
         * @return A Context to check if there's an internet connection
         */
        Context getContext();

        /**
         * Gets the recipe list from the server's response when asked for them.
         *
         * @return
         */
        List<Recipe> getRecipeList();

        /**
         * Asks the server for the recipes.
         */
        void getRecipes();

        /**
         * Saves a recipe as a favorite.
         *
         * @param view The button that was clicked to save the recipe (the heart in this case)
         */
        void updateFavorite(android.view.View view);
        void setView(MVP.View view);
    }

    public interface View {
        /**
         * Mostly hides the progress bar after the model returned with some answer
         * from the server.
         *
         * @param visibility if false, it hides the progress bar.
         */
        void showProgressBar(boolean visibility);

        /**
         * Passes the recipes to the adapter so the view can show them.
         */
        void updateRecipesList();

        /**
         * Toggles the recipe's state, i.e., whether it's a favorite or not.
         *
         * @param view The button that was clicked to toggle the recipe's state.
         * @param isFavorite true means that I want to keep this recipe 'cause I liked it.
         */
        void updateFavorite(android.view.View view, boolean isFavorite);

        /**
         * Show the view that will hold the recipe items.
         *
         * @param visibility
         */
        void showRecipes(boolean visibility);

        /**
         * Shows the messages (empty recipe list, internet error, etc) .
         *
         * @param message A (probably more human friendly) message to tell the
         *                user that something probably went wrong.
         */
        void showErrorMessageView(String message);
    }

}
