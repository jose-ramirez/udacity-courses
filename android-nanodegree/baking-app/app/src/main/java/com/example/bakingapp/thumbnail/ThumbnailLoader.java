package com.example.bakingapp.thumbnail;

import android.support.annotation.Nullable;

import com.bumptech.glide.load.Options;
import com.bumptech.glide.load.model.ModelLoader;
import com.bumptech.glide.signature.ObjectKey;

import java.io.InputStream;

/**
 * Created by jose on 16/12/17.
 */

public class ThumbnailLoader implements ModelLoader<ThumbnailUrl, InputStream>{

    public LoadData<InputStream> buildLoadData(ThumbnailUrl thumbnailUrl, int width, int height, Options options) {
        return new ModelLoader.LoadData(
                new ObjectKey(thumbnailUrl),
                new ThumbnailFetcher(thumbnailUrl));
    }


    public boolean handles(ThumbnailUrl thumbnailUrl) {
        return true;
    }
}