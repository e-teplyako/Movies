package com.example.android.movies;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.android.movies.Utilities.JSONUtilities;
import com.example.android.movies.Utilities.NetworkUtilities;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ImageButton mSearhIButton;
    EditText mQueryET;
    TextView mResultsTV;

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

        mResultsTV = (TextView) findViewById(R.id.tv_results);
        mQueryET = (EditText) findViewById(R.id.et_search);

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
                mResultsTV.setText("No results found");
                return;
            }

            String data = "";
            for (int i = 0; i < movies.size(); i++) {
                data += movies.get(i).toString();
            }
            mResultsTV.setText(data);
        }
    }


}
