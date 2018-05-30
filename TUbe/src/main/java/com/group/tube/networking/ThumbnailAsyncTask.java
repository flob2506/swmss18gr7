package com.group.tube.networking;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.URL;

public class ThumbnailAsyncTask extends AsyncTask<String, Integer, Drawable> {
    public ThumbnailAsyncTask() {
        this.responseHandler = null;
    }

    private AsyncResponse<Drawable> responseHandler;

    @Override
    protected Drawable doInBackground(String... arg0) {
        try {
            URL url = new URL(arg0[0]);
            InputStream inputStream = url.openStream();

            BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
            Bitmap bMap = BitmapFactory.decodeStream(bufferedInputStream);

            inputStream.close();
            bufferedInputStream.close();

            return new BitmapDrawable(bMap);

        } catch (Exception e) {
            this.responseHandler.handleProcessException(e);
        }

        return null;
    }

    protected void onPostExecute(Drawable thumbnail) {
        if (thumbnail == null)
            return;

        this.responseHandler.processFinish(thumbnail);
    }

    public void setResponseHandler(AsyncResponse<Drawable> responseHandler){
        this.responseHandler = responseHandler;
    }
}