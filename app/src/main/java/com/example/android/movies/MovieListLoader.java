package com.example.android.movies;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

import com.example.android.movies.Utilities.JSONUtilities;
import com.example.android.movies.Utilities.NetworkUtilities;

import java.io.IOException;
import java.net.URL;
import java.util.List;

public class MovieListLoader extends AsyncTaskLoader<List<Movie>> {
	private String _query;

	public MovieListLoader(Context context, String query) {
		super(context);
		_query = query;
	}

	@Override
	protected void onStartLoading() {
		forceLoad();
	}

	@Nullable
	@Override
	public List<Movie> loadInBackground() {
		if (_query == null || _query.isEmpty()) {
			return null;
		}

		URL queryURL = NetworkUtilities.buildSearchUrlWithQuery(_query);

		try {
			String jsonResponse = NetworkUtilities.getResponseFromHttpUrl(queryURL);
			return JSONUtilities.parseOmdbSearchJSON(jsonResponse);
		}
		catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
}
