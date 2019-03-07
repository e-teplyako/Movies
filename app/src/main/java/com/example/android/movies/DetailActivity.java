package com.example.android.movies;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.AsyncTask;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.example.android.movies.Utilities.DownloadImageTask;
import com.example.android.movies.Utilities.JSONUtilities;
import com.example.android.movies.Utilities.NetworkUtilities;
import com.example.android.movies.databinding.ActivityDetailBinding;

import java.io.IOException;
import java.net.URL;

public class DetailActivity extends AppCompatActivity {

    ActivityDetailBinding mBinding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_detail);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }


        Intent intent = getIntent();
        if (intent.hasExtra(Intent.EXTRA_TEXT)) {
            String imdbId = intent.getStringExtra(Intent.EXTRA_TEXT);
            if (!imdbId.isEmpty()) {
                loadMovie(imdbId);
            }
        }
    }

    private void loadMovie(String id){
        new FetchMovieData().execute(id);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return false;
    }


    private void displayData(MovieDetailsData data) {
        mBinding.titleValue.setText(data.mTitle + " (" + data.mYear + ")");
        mBinding.ratedValue.setText(data.mRated);
        mBinding.releasedValue.setText(data.mReleased);
        mBinding.runtimeValue.setText(data.mRuntime);
        mBinding.genreValue.setText(data.mGenre);
        mBinding.directorValue.setText(data.mDirector);
        mBinding.writerValue.setText(data.mWriter);
        mBinding.actorsValue.setText(data.mActors);
        mBinding.languageValue.setText(data.mLanguage);
        mBinding.countryValue.setText(data.mCountry);
        mBinding.plotValue.setText(data.mPlot);

        if (data.mRatings.containsKey(data.RT_RATING_KEY)){
            mBinding.rottenValue.setText(data.mRatings.getAsString(data.RT_RATING_KEY));
        }
        else {
            mBinding.rottenValue.setVisibility(View.GONE);
            mBinding.rottenLabel.setVisibility(View.GONE);
        }

        if (data.mRatings.containsKey(data.IMDB_RATING_KEY)) {
            mBinding.imdbValue.setText(data.mImdbRating + " (" + data.mImdbVotes + " votes)");
        }
        else {
            mBinding.imdbValue.setVisibility(View.GONE);
            mBinding.imdbLabel.setVisibility(View.GONE);
        }

        if (data.mRatings.containsKey(data.METACRITIC_RATING_KEY)) {
            mBinding.metascoreValue.setText(data.mMetascore);
        }
        else {
            mBinding.metascoreValue.setVisibility(View.GONE);
            mBinding.metascoreLabel.setVisibility(View.GONE);
        }

        new DownloadImageTask(mBinding.posterImage).execute(data.mPosterUrl);
    }


    public class FetchMovieData extends AsyncTask<String, Void, MovieDetailsData> {

        @Override
        protected MovieDetailsData doInBackground(String... args) {
            String id = args[0];
            if (id.isEmpty()) {
                return null;
            }
            URL queryURL = NetworkUtilities.buildUrlById(id);

            try {
                String jsonResponse = NetworkUtilities.getResponseFromHttpUrl(queryURL);
                return JSONUtilities.parseOmdbDetailsJSON(jsonResponse);
            }
            catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(MovieDetailsData data) {
            if (data == null) {
                mBinding.errorLabel.setVisibility(View.VISIBLE);
                return;
            }
            mBinding.errorLabel.setVisibility(View.GONE);
            displayData(data);
        }
    }

}
