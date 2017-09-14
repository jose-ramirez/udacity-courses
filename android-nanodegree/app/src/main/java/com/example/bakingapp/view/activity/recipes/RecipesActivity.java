package com.example.bakingapp.view.activity.recipes;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bakingapp.MVP;
import com.example.bakingapp.R;
import com.example.bakingapp.di.DaggerRecipesActivityComponent;
import com.example.bakingapp.di.RecipesActivityModule;
import com.example.bakingapp.model.Recipe;
import com.example.bakingapp.util.BakingAppUtil;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipesActivity extends AppCompatActivity implements MVP.View{

    private RecipesAdapter adapter;

    @Inject public MVP.Presenter recipesPresenter;

    @Inject LinearLayoutManager manager;

    @Inject GridLayoutManager gridManager;

    @Nullable @BindView(R.id.rv_recipes) RecyclerView rvRecipes;

    @Nullable @BindView(R.id.tv_message) TextView tvMessage;

    @Nullable @BindView(R.id.pb_loading) ProgressBar pbLoading;

    @Nullable @BindView(R.id.toolbar) Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        toolbar.setTitle(R.string.app_name);

        DaggerRecipesActivityComponent.builder()
                .recipesActivityModule(new RecipesActivityModule(this))
                .build()
                .inject(this);

        if(BakingAppUtil.sw(this, 600)){
            rvRecipes.setLayoutManager(gridManager);
        }else{
            rvRecipes.setLayoutManager(manager);
        }

        this.adapter = new RecipesAdapter(new ArrayList<Recipe>());
        rvRecipes.setAdapter(adapter);

        this.recipesPresenter.setView(this);
        this.recipesPresenter.getRecipes();
    }

    @Override
    public void showProgressBar(boolean visibility) {
        int show = visibility ? View.VISIBLE : View.GONE;
        this.pbLoading.setVisibility(show);
    }

    @Override
    public void showRecipes(boolean visibility) {
        int show = visibility ? View.VISIBLE : View.GONE;
        this.rvRecipes.setVisibility(show);
    }

    @Override
    public void showErrorMessageView(String message) {
        this.tvMessage.setVisibility(View.VISIBLE);
        this.tvMessage.setText(message);
    }

    @Override
    public void updateRecipesList() {
        this.adapter.getRecipes().addAll(this.recipesPresenter.getRecipeList());
        this.adapter.notifyDataSetChanged();
    }

    @Override
    public void updateFavorite(View view, boolean isFavorite) {
        view.setSelected(isFavorite);
        String msg = isFavorite ?
                getString(R.string.recipe_added_msg) :
                getString(R.string.recipe_removed_msg);
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

    public void favRecipe(View view){
        this.recipesPresenter.updateFavorite(view);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        this.recipesPresenter.unsubscribe();
    }
}
