package com.example.bakingapp.thumbnail;

import android.content.Context;

import com.bumptech.glide.Registry;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.module.AppGlideModule;

import java.io.InputStream;

import okhttp3.OkHttpClient;

/**
 * Created by jose on 16/12/17.
 */

@GlideModule
public class GlideConfiguration extends AppGlideModule{

    private OkHttpClient client;

    public GlideConfiguration(){
        this.client = new OkHttpClient
                .Builder()
                .build();
    }

    @Override
    public boolean isManifestParsingEnabled() {
        return false;
    }

    @Override
    public void registerComponents(Context context, Registry registry) {
        registry.replace(GlideUrl.class, InputStream.class, new OkHttpUrlLoader.Factory(client));
        registry.replace(ThumbnailUrl.class, InputStream.class, new ThumbnailFactory());
    }
}
