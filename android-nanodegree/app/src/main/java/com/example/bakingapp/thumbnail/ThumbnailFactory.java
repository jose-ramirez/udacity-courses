package com.example.bakingapp.thumbnail;

import com.bumptech.glide.load.model.ModelLoader;
import com.bumptech.glide.load.model.ModelLoaderFactory;
import com.bumptech.glide.load.model.MultiModelLoaderFactory;

import java.io.InputStream;

/**
 * Created by jose on 16/12/17.
 */

public class ThumbnailFactory implements ModelLoaderFactory<ThumbnailUrl, InputStream>{

    @Override
    public ModelLoader<ThumbnailUrl, InputStream> build(MultiModelLoaderFactory multiFactory) {
        return new ThumbnailLoader();
    }

    @Override
    public void teardown() {

    }
}
