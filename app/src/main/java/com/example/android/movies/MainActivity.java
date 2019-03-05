package com.example.android.movies;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.movies.Utilities.JSONUtilities;
import com.example.android.movies.Utilities.NetworkUtilities;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ImageButton mSearhIButton;
    private EditText mQueryET;
    private RecyclerView mSearchRV;
    private SearchAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        Init "show results" button and set its listener
        View.OnClickListener showResultsListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String query = mQueryET.getText().toString();
                if (!query.isEmpty()) {
                    loadShowingsData(mQueryET.getText().toString());
                }
                //else show error or smth
            }
        };
        mSearhIButton = (ImageButton) findViewById(R.id.ib_search);
        mSearhIButton.setOnClickListener(showResultsListener);

        mQueryET = (EditText) findViewById(R.id.et_search);

        setupRecyclerView();
    }


//Sets up recycler view with adapter and listener
    private void setupRecyclerView() {
        SearchAdapter.SearchRecyclerViewClickListener listener = new SearchAdapter.SearchRecyclerViewClickListener() {
            @Override
            public void OnClick(View view, int position, String id) {
                Intent intent = new Intent(view.getContext(), DetailActivity.class);
                intent.putExtra(Intent.EXTRA_TEXT, id);
                startActivity(intent);
            }
        };

        mSearchRV = (RecyclerView) findViewById(R.id.rv_search);
        mSearchRV.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mSearchRV.setLayoutManager(mLayoutManager);
        mAdapter = new SearchAdapter(null, getApplicationContext(), listener);
        mSearchRV.setAdapter(mAdapter);
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
            URL queryURL = NetworkUtilities.buildUrlWithQuery(query);

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
            mAdapter.setMovies(movies);
        }
    }


}
