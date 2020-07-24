package com.example.android.movies.Utilities;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

public class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
    public AsyncResponse _delegate = null;

    public DownloadImageTask(AsyncResponse delegate) {
        _delegate = delegate;
    }

    @Override
    protected Bitmap doInBackground(String... urls) {
        String url = urls[0];
        Bitmap poster = null;
        try {
            InputStream in = new URL(url).openStream();
            poster = BitmapFactory.decodeStream(in);
            in.close();
        }
        catch (MalformedURLException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return poster;
    }

    @Override
    protected void onPostExecute(Bitmap result) {
        _delegate.processFinish(result);
    }

    public interface AsyncResponse {
        void processFinish(Bitmap output);
    }
}
