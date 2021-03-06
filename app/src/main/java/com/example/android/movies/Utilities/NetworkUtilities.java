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
        return url;
    }

    public static String getResponseFromHttpUrl(URL url) throws IOException {
        Log.e("NetworkUtilities", "getResponseFromHttpUrl() called");
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        InputStream in = urlConnection.getInputStream();
        Scanner scanner = new Scanner(in);
        try {
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            in.close();
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
            scanner.close();
            urlConnection.disconnect();
        }
        return null;
    }
}
