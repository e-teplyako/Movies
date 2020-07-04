package com.example.android.movies;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.PersistableBundle;
import android.os.StrictMode;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.android.movies.Utilities.JSONUtilities;
import com.example.android.movies.Utilities.NetworkUtilities;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {
	private static final String STATE_QUERY = "query";
    private RecyclerView _searchRV;
    private SearchAdapter _adapter;
    private RecyclerView.LayoutManager _layoutManager;

    private SearchView _searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        StrictMode.enableDefaults();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupRecyclerView();
    }

    private void setupRecyclerView() {
        SearchAdapter.SearchRecyclerViewClickListener listener = new SearchAdapter.SearchRecyclerViewClickListener() {
            @Override
            public void OnClick(View view, int position, String id) {
                Intent intent = new Intent(view.getContext(), DetailActivity.class);
                intent.putExtra(Intent.EXTRA_TEXT, id);
                startActivity(intent);
            }
        };

        _searchRV = findViewById(R.id.rv_search);
        _searchRV.setHasFixedSize(true);
        _layoutManager = new LinearLayoutManager(this);
        _searchRV.setLayoutManager(_layoutManager);
        _adapter = new SearchAdapter(null, listener);
        _searchRV.setAdapter(_adapter);
    }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main_menu, menu);
		configureSearchView(menu);
		return true;
	}

	private void configureSearchView(Menu menu) {
		MenuItem search = menu.findItem(R.id.search);
		_searchView = (SearchView) search.getActionView();
		_searchView.setOnQueryTextListener(this);
		_searchView.setQueryHint("Search a movie");
		_searchView.setSubmitButtonEnabled(true);
		_searchView.setIconifiedByDefault(true);
	}

	@Override
	public void onSaveInstanceState(Bundle state) {
		super.onSaveInstanceState(state);
		if (!_searchView.isIconified()) {
			state.putCharSequence(STATE_QUERY, _searchView.getQuery());
		}
	}

	@Override
	public void onRestoreInstanceState(Bundle savedInstanceState, PersistableBundle persistentState) {
		super.onRestoreInstanceState(savedInstanceState, persistentState);
		_searchView.setQuery(savedInstanceState.getCharSequence(STATE_QUERY, ""), false);
	}

	private void loadShowingsData(String query){
        new FetchSearchData().execute(query);
    }

	@Override
	public boolean onQueryTextSubmit(String query) {
		ConnectivityManager connMgr = (ConnectivityManager)	getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
		if (networkInfo != null && networkInfo.isConnected()) {
			if (!query.isEmpty()) {
				loadShowingsData(query);
			}
		} else {
			Toast.makeText(this, "NO INTERNET", Toast.LENGTH_SHORT).show();
		}
		return true;
	}

	@Override
	public boolean onQueryTextChange(String newText) {
		return false;
	}

	public class FetchSearchData extends AsyncTask<String, Void, ArrayList<Movie>>{

        @Override
        protected ArrayList<Movie> doInBackground(String... args) {
            String query = args[0];
            if (query.isEmpty()) {
                return null;
            }
            URL queryURL = NetworkUtilities.buildSearchUrlWithQuery(query);

            try {
                String jsonResponse = NetworkUtilities.getResponseFromHttpUrl(queryURL);
                return JSONUtilities.parseOmdbSearchJSON(jsonResponse);
            }
            catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(ArrayList<Movie> movies) {
            if (movies == null || movies.isEmpty()) {
               // mResultsTV.setText("No results found");
                return;
            }
            _adapter.setMovies(movies);
        }
    }


}
