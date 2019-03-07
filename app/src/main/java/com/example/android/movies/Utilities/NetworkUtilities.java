package com.example.android.movies.Utilities;

import android.net.Uri;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class NetworkUtilities {


    private static final String LOG_TAG = NetworkUtilities.class.getSimpleName();

    public static final String TEST_OMDB_URL = "http://www.omdbapi.com/?apikey=bc58fbd4&s=Matrix&r=json";

    private static final String OMDB_BASE_URL = "http://www.omdbapi.com/?";
    private static final String APIKEY_KEY = "apikey";
    private static final String QUERY_KEY = "s";
    private static final String FORMAT_KEY = "r";
    private static final String ID_KEY = "i";
    private static final String PLOT_KEY = "plot";

    private static final String APIKEY_VALUE = "bc58fbd4";
    private static final String FORMAT_VALUE = "json";
    private static final String PLOT_VALUE = "short";

    public static URL buildSearchUrlWithQuery(String query) {
        Uri buildUri = Uri.parse(OMDB_BASE_URL).buildUpon()
                .appendQueryParameter(APIKEY_KEY, APIKEY_VALUE)
                .appendQueryParameter(QUERY_KEY, query)
                .appendQueryParameter(FORMAT_KEY, FORMAT_VALUE)
                .build();

        URL url = null;
        try {
            url = new URL(buildUri.toString());
        }
        catch (MalformedURLException e) {
            e.printStackTrace();
        }
        Log.e(LOG_TAG, "Built uri: " + url);
        return url;
    }

    public static URL buildUrlById(String id) {
        Uri buildUri = Uri.parse(OMDB_BASE_URL).buildUpon()
                .appendQueryParameter(APIKEY_KEY, APIKEY_VALUE)
                .appendQueryParameter(ID_KEY, id)
                .appendQueryParameter(FORMAT_KEY, FORMAT_VALUE)
                .appendQueryParameter(PLOT_KEY, PLOT_VALUE)
                .build();

        URL url = null;
        try {
            url = new URL(buildUri.toString());
        }
        catch (MalformedURLException e) {
            e.printStackTrace();
        }
        Log.e(LOG_TAG, "Built uri: " + url);
        return url;
    }


    public static URL buildTestUrl(){
        URL url = null;
        try {
            url = new URL(TEST_OMDB_URL);
        }
        catch (MalformedURLException e){
            e.printStackTrace();
        }
        return url;
    }

    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            }
            else {
                return null;
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            urlConnection.disconnect();
        }
        return null;
    }
}
