package com.example.android.movies.Utilities;

import com.example.android.movies.Movie;
import com.example.android.movies.MovieDetailsData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class JSONUtilities {

    public static MovieDetailsData parseOmdbDetailsJSON (String JSONString) {
        MovieDetailsData movieDetailsData = new MovieDetailsData();

        try {
            JSONObject jsonRootObject = new JSONObject(JSONString);
            boolean notEmpty = Boolean.parseBoolean(jsonRootObject.getString("Response"));
            if (!notEmpty) {
                return null;
            }

            JSONArray ratingsJsonArray = jsonRootObject.getJSONArray("Ratings");
            if (ratingsJsonArray != null) {
                for (int i = 0; i < ratingsJsonArray.length(); i++){
                    JSONObject ratingObject = ratingsJsonArray.getJSONObject(i);
                    String source = ratingObject.getString("Source");
                    String value = ratingObject.getString("Value");
                    movieDetailsData.Ratings.put(source, value);
                }
            }
            movieDetailsData.Title = jsonRootObject.getString("Title");
            movieDetailsData.Year = jsonRootObject.getString("Year");
            movieDetailsData.Rated = jsonRootObject.getString("Rated");
            movieDetailsData.Released = jsonRootObject.getString("Released");
            movieDetailsData.Runtime = jsonRootObject.getString("Runtime");
            movieDetailsData.Genre = jsonRootObject.getString("Genre");
            movieDetailsData.Director = jsonRootObject.getString("Director");
            movieDetailsData.Writer = jsonRootObject.getString("Writer");
            movieDetailsData.Actors = jsonRootObject.getString("Actors");
            movieDetailsData.Plot = jsonRootObject.getString("Plot");
            movieDetailsData.Language = jsonRootObject.getString("Language");
            movieDetailsData.Country = jsonRootObject.getString("Country");
            movieDetailsData.Awards = jsonRootObject.getString("Awards");
            movieDetailsData.PosterUrl = jsonRootObject.getString("Poster");
            movieDetailsData.Metascore = jsonRootObject.getString("Metascore");
            movieDetailsData.ImdbRating = jsonRootObject.getString("imdbRating");
            movieDetailsData.ImdbVotes = jsonRootObject.getString("imdbVotes");
            movieDetailsData.Id = jsonRootObject.getString("imdbID");
            movieDetailsData.Type = jsonRootObject.getString("Type");
        }
        catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        return movieDetailsData;
    }

    public static ArrayList<Movie> parseImdbSearchJSON(String JSONString){
        if (JSONString == null)
            return null;
        ArrayList<Movie> movies = new ArrayList<>();

        try {
            JSONObject jsonRootObject = new JSONObject(JSONString);

            boolean notEmpty = Boolean.parseBoolean(jsonRootObject.getString("Response"));
            if (!notEmpty) {
                return null;
            }

            JSONArray searchResultsArray = jsonRootObject.getJSONArray("Search");

            for (int i = 0; i < searchResultsArray.length(); i++) {
                JSONObject movieObject = searchResultsArray.getJSONObject(i);

                String title = movieObject.getString("Title");
                String year = movieObject.getString("Year");
                String imdbId = movieObject.getString("imdbID");
                String type = movieObject.getString("Type");
                String posterURL = movieObject.getString("Poster");

                Movie newMovie = new Movie (title, year, imdbId, type, posterURL);
                movies.add(newMovie);
            }
        }
        catch (JSONException e){
            e.printStackTrace();
        }

        return movies;
    }

}
