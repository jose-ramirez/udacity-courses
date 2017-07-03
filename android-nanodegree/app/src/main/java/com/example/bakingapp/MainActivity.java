package com.example.bakingapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.bakingapp.api.BakingAPI;
import com.example.bakingapp.di.DaggerMainActivityComponent;
import com.example.bakingapp.di.MainActivityModule;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;

public class MainActivity extends AppCompatActivity{

    @Inject RecipeAdapter adapter;

    @Inject LinearLayoutManager manager;

    @Inject GridLayoutManager gridManager;

    @Inject BakingAPI api;

    @Inject OkHttpClient client;

    @Nullable @BindView(R.id.rv_recipes) RecyclerView rvRecipes;

    @Nullable @BindView(R.id.tv_message) TextView tvMessage;

    @Nullable @BindView(R.id.pb_loading) ProgressBar pbLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        DaggerMainActivityComponent.builder()
                .mainActivityModule(new MainActivityModule(this))
                .build()
                .inject(this);

        if(BakingAppUtil.sw(this, 600)){
            rvRecipes.setLayoutManager(gridManager);
        }else{
            rvRecipes.setLayoutManager(manager);
        }

        rvRecipes.setAdapter(adapter);

        if(BakingAppUtil.isNetworkAvailable(this)){
            api.getRecipes()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new RecipesObserver(adapter, this));
        }else{
            pbLoading.setVisibility(View.GONE);
            tvMessage.setVisibility(View.VISIBLE);
            tvMessage.setText(R.string.no_internet_msg);
        }
    }
}
