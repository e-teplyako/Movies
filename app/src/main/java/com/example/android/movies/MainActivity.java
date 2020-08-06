package com.example.android.movies;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.PersistableBundle;
import android.os.StrictMode;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener, LoaderManager.LoaderCallbacks<List<Movie>> {
	private static final String STATE_QUERY = "query";
	public static final int SEARCH_LOADER = 22;
	public static final String QUERY = "url";
    private RecyclerView _searchRV;
    private SearchAdapter _adapter;
    private RecyclerView.LayoutManager _layoutManager;
    private View _errorView;

    private SearchView _searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        StrictMode.enableDefaults();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupRecyclerView();
		LoaderManager.getInstance(this).initLoader(SEARCH_LOADER, null, this);
    }

    private void setupRecyclerView() {
        SearchAdapter.SearchRecyclerViewClickListener listener = new SearchAdapter.SearchRecyclerViewClickListener() {
            @Override
            public void OnClick(View view, int position, String id) {
				Intent intent = new Intent(getApplicationContext(), DetailActivity.class);
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
        _errorView = findViewById(R.id.error_view);
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
		Bundle queryBundle = new Bundle();
		queryBundle.putString(QUERY, query);
		LoaderManager loaderManager = getSupportLoaderManager();
		Loader<String> loader = loaderManager.getLoader(SEARCH_LOADER);
		if(loader == null){
			loaderManager.initLoader(SEARCH_LOADER, queryBundle, this);
		}
		else {
			loaderManager.restartLoader(SEARCH_LOADER, queryBundle, this);
		}
    }

	@Override
	public boolean onQueryTextSubmit(String query) {
		ConnectivityManager connMgr = (ConnectivityManager)	getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
		if (networkInfo != null && networkInfo.isConnected()) {
			if (!query.isEmpty()) {
				loadShowingsData(query);
			}
			_searchRV.setVisibility(View.VISIBLE);
			_errorView.setVisibility(View.GONE);
		}
		else {
			_searchRV.setVisibility(View.GONE);
			_errorView.setVisibility(View.VISIBLE);
		}
		return true;
	}

	@Override
	public boolean onQueryTextChange(String newText) {
		return false;
	}

	@NonNull
	@Override
	public Loader<List<Movie>> onCreateLoader(int id, @Nullable final Bundle args) {
    	String query = "";
    	if (args != null) {
			if (args.getCharSequence(QUERY) != null)
				query = (String) args.getCharSequence(QUERY);
		}
		return new MovieListLoader(getApplicationContext(), query);
	}

	@Override
	public void onLoadFinished(@NonNull Loader<List<Movie>> loader, List<Movie> movies) {
		_adapter.setMovies(movies);
	}

	@Override
	public void onLoaderReset(@NonNull Loader<List<Movie>> loader) {

	}
}
