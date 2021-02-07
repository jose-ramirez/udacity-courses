package com.example.bakingapp.service;

import android.content.Intent;
import android.widget.RemoteViewsService;

import com.example.bakingapp.view.widget.BakingAppIngredientsRemoteFactory;

/**
 * Created by jose on 16/11/17.
 */

public class BakingAppIngredientsRemoteService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new BakingAppIngredientsRemoteFactory(getApplicationContext(), intent);
    }
}
