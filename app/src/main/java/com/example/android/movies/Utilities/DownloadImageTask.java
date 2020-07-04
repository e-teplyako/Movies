package com.example.android.movies.Utilities;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import com.example.android.movies.R;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

public class DownloadImageTask extends AsyncTask <String, Void, Bitmap> {

    private ImageView mImageView;

    public DownloadImageTask(ImageView imageView) {
        mImageView = imageView;
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
        if (result != null) {
            mImageView.setImageBitmap(result);
        }
        else {
            mImageView.setImageResource(R.drawable.test_movie_poster);
        }
    }
}
