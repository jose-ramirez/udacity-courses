package com.example.bakingapp;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.bakingapp.data.Recipe;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import retrofit2.HttpException;
import timber.log.Timber;

public class RecipesObserver implements Observer<List<Recipe>>{

    private MainActivity activity;
    private RecipeAdapter adapter;
    private ProgressBar pbLoading;
    private RecyclerView rvRecipes;
    private TextView tvMessage;

    public RecipesObserver(RecipeAdapter adapter, MainActivity activity){
        this.activity = activity;
        this.adapter = adapter;
        this.pbLoading = (ProgressBar) activity.findViewById(R.id.pb_loading);
        this.rvRecipes = (RecyclerView) activity.findViewById(R.id.rv_recipes);
        this.tvMessage = (TextView) activity.findViewById(R.id.tv_message);
    }

    @Override
    public void onSubscribe(Disposable d) {}

    @Override
    public void onComplete() {}

    @Override
    public void onNext(List<Recipe> recipes) {
        this.pbLoading.setVisibility(View.GONE);
        if(!recipes.isEmpty()){
            this.rvRecipes.setVisibility(View.VISIBLE);
            this.adapter.getRecipes().addAll(recipes);
            this.adapter.notifyDataSetChanged();
        }else{
            this.tvMessage.setVisibility(View.VISIBLE);
            this.tvMessage.setText(R.string.empty_recipe_list_msg);
        }
    }

    @Override
    public void onError(Throwable e) {
        this.pbLoading.setVisibility(View.GONE);
        if(e instanceof HttpException){
            HttpException ex = (HttpException)e;
            Timber.e(this.activity.getString(R.string.error_code) + ex.code());
            Timber.e(this.activity.getString(R.string.error_message) + ex.message());
            Timber.e(this.activity.getString(R.string.error_url) + ex.response().raw().request().url().toString());
        }else{
            e.printStackTrace();
        }
        this.tvMessage.setVisibility(View.VISIBLE);
        this.tvMessage.setText(this.activity.getString(R.string.recipe_loading_failed_msg) + e.getMessage());
    }
}
