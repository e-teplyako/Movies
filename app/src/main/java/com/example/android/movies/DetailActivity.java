package com.example.android.movies;

import android.content.Intent;
import androidx.databinding.DataBindingUtil;
import android.os.AsyncTask;
import androidx.core.app.NavUtils;
import android.os.Bundle;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import com.example.android.movies.Utilities.DownloadImageTask;
import com.example.android.movies.Utilities.JSONUtilities;
import com.example.android.movies.Utilities.NetworkUtilities;
import com.example.android.movies.databinding.ActivityDetailBinding;

import java.io.IOException;
import java.net.URL;

public class DetailActivity extends AppCompatActivity {

    ActivityDetailBinding _binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        _binding = DataBindingUtil.setContentView(this, R.layout.activity_detail);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        _binding.plotHider.setVisibility(View.VISIBLE);
        _binding.plotHider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                _binding.plotHider.setVisibility(View.GONE);
            }
        });

        _binding.plotValue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                _binding.plotHider.setVisibility(View.VISIBLE);
            }
        });

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
        _binding.titleValue.setText(data.Title + " (" + data.Year + ")");
        _binding.ratedValue.setText(data.Rated);
        _binding.releasedValue.setText(data.Released);
        _binding.runtimeValue.setText(data.Runtime);
        _binding.genreValue.setText(data.Genre);
        _binding.directorValue.setText(data.Director);
        _binding.writerValue.setText(data.Writer);
        _binding.actorsValue.setText(data.Actors);
        _binding.languageValue.setText(data.Language);
        _binding.countryValue.setText(data.Country);
        _binding.plotValue.setText(data.Plot);

        if (data.Ratings.containsKey(data.RT_RATING_KEY)){
            _binding.rottenValue.setText(data.Ratings.getAsString(data.RT_RATING_KEY));
        }
        else {
            _binding.rottenValue.setVisibility(View.GONE);
            _binding.rottenLabel.setVisibility(View.GONE);
        }

        if (data.Ratings.containsKey(data.IMDB_RATING_KEY)) {
            _binding.imdbValue.setText(data.ImdbRating + " (" + data.ImdbVotes + " votes)");
        }
        else {
            _binding.imdbValue.setVisibility(View.GONE);
            _binding.imdbLabel.setVisibility(View.GONE);
        }

        if (data.Ratings.containsKey(data.METACRITIC_RATING_KEY)) {
            _binding.metascoreValue.setText(data.Metascore);
        }
        else {
            _binding.metascoreValue.setVisibility(View.GONE);
            _binding.metascoreLabel.setVisibility(View.GONE);
        }

        new DownloadImageTask(_binding.posterImage).execute(data.PosterUrl);
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
                _binding.errorLabel.setVisibility(View.VISIBLE);
                return;
            }
            _binding.errorLabel.setVisibility(View.GONE);
            displayData(data);
        }
    }

}
