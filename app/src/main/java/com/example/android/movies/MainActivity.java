package com.example.android.movies;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import com.example.android.movies.Utilities.JSONUtilities;
import com.example.android.movies.Utilities.NetworkUtilities;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ImageButton _searchButton;
    private EditText _queryEditText;
    private RecyclerView _searchRV;
    private SearchAdapter _adapter;
    private RecyclerView.LayoutManager _layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        StrictMode.enableDefaults();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        View.OnClickListener showResultsListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String query = _queryEditText.getText().toString();
                if (!query.isEmpty()) {
                    loadShowingsData(_queryEditText.getText().toString());
                }
                //else show error or smth
            }
        };
        _searchButton = findViewById(R.id.ib_search);
        _searchButton.setOnClickListener(showResultsListener);

        _queryEditText = findViewById(R.id.et_search);

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
        _adapter = new SearchAdapter(null, getApplicationContext(), listener);
        _searchRV.setAdapter(_adapter);
    }


    private void loadShowingsData(String query){
        new FetchSearchData().execute(query);
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
