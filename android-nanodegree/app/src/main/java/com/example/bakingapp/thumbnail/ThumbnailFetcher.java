package com.example.bakingapp.thumbnail;

import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;

import com.bumptech.glide.Priority;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.data.DataFetcher;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;

/**
 * Created by jose on 16/12/17.
 */

public class ThumbnailFetcher implements DataFetcher<InputStream>{

    private ThumbnailUrl thumbnail;

    public ThumbnailFetcher(ThumbnailUrl thumbnail){
        this.thumbnail = thumbnail;
    }

    @Override
    public void cleanup() {

    }

    @Override
    public void cancel() {

    }

    @Override
    public void loadData(Priority priority, DataFetcher.DataCallback<? super InputStream> callback) {
        Bitmap bitmap = null;
        MediaMetadataRetriever mediaMetadataRetriever = null;
        try {
            mediaMetadataRetriever = new MediaMetadataRetriever();
            mediaMetadataRetriever.setDataSource(
                    thumbnail.getUrl(),
                    new HashMap<String, String>());

            bitmap = mediaMetadataRetriever.getFrameAtTime();

        } finally {
            mediaMetadataRetriever.release();
        }

        if (bitmap == null) {
            callback.onLoadFailed(new Exception("Bitmap is null"));
        } else {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, bos);
            InputStream bs = new ByteArrayInputStream(bos.toByteArray());

            callback.onDataReady(bs);
        }
    }

    @Override
    public Class<InputStream> getDataClass() {
        return InputStream.class;
    }

    @Override
    public DataSource getDataSource() {
        return DataSource.REMOTE;
    }
}
