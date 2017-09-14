package com.example.bakingapp.presenter;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.view.View;

import com.example.bakingapp.MVP;
import com.example.bakingapp.R;
import com.example.bakingapp.model.Recipe;
import com.example.bakingapp.model.RecipesModel;
import com.example.bakingapp.util.BakingAppUtil;
import com.example.bakingapp.util.DBUtils;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.HttpException;
import timber.log.Timber;

/**
 * Created by jose on 12/07/17.
 */

public class RecipesPresenter implements MVP.Presenter, Observer<List<Recipe>> {

    private Context context;
    public MVP.Model recipesModel;
    private MVP.View recipesView;
    private List<Recipe> recipes;

    public RecipesPresenter(){
        this.recipesModel = new RecipesModel();
        this.recipes = new ArrayList<Recipe>();
    }

    @Override
    public void unsubscribe() {
        this.unsubscribe();
    }

    @Override
    public Context getContext() {
        return this.context;
    }

    @Override
    public List<Recipe> getRecipeList() {
        return recipes;
    }

    @Override
    public void getRecipes() {
        if(BakingAppUtil.isNetworkAvailable(this.context)){
            this.recipesModel.getApi().getRecipes()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(this);
        }else{
            this.recipesView.showProgressBar(false);
            String noInternetMessage = this.context.getString(R.string.no_internet_msg);
            this.recipesView.showErrorMessageView(noInternetMessage);
        }
    }

    @Override
    public void setView(MVP.View recipesView) {
        this.recipesView = recipesView;
        this.context = ((Activity) recipesView).getBaseContext();
    }

    @Override
    public void updateFavorite(View view) {
        ContentResolver provider = this.context.getContentResolver();
        Recipe recipe = (Recipe) view.getTag();
        boolean isAlreadyFavorite = DBUtils.isFavorite(provider, recipe);
        if(!isAlreadyFavorite){
            DBUtils.saveRecipeAsFavorite(provider, recipe);
        }else{
            DBUtils.deleteRecipe(provider, recipe);
        }this.recipesView.updateFavorite(view, !isAlreadyFavorite);
    }

    @Override
    public void onNext(List<Recipe> newRecipes) {
        this.recipesView.showProgressBar(false);
        if(!newRecipes.isEmpty()){
            this.recipesView.showRecipes(true);
            this.recipes.addAll(newRecipes);
            this.recipesView.updateRecipesList();
        }else{
            String noRecipesRetrievedMsg = this.context.getString(R.string.empty_recipe_list_msg);
            this.recipesView.showErrorMessageView(noRecipesRetrievedMsg);
        }
    }

    @Override
    public void onError(Throwable e) {
        this.recipesView.showProgressBar(false);
        if(e instanceof HttpException){
            HttpException ex = (HttpException)e;
            String failingURL = ex.response().raw().request().url().toString();
            Timber.e(this.context.getString(R.string.error_code) + ex.code());
            Timber.e(this.context.getString(R.string.error_message) + ex.message());
            Timber.e(this.context.getString(R.string.error_url) + failingURL);
        }else{
            e.printStackTrace();
        }
        String errorMessage = this.context.getString(R.string.recipe_loading_failed_msg) + e.getMessage();
        this.recipesView.showErrorMessageView(errorMessage);
    }

    @Override
    public void onSubscribe(Disposable d) {}

    @Override
    public void onComplete() {}
}
