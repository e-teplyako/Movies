package com.example.android.movies.Utilities;

import com.example.android.movies.Movie;
import com.example.android.movies.MovieDetailsData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class JSONUtilities {

    public static final String TEST_JSON_STRING = "{\"Search\":[" +
            "{\"Title\":\"The Matrix\",\"Year\":\"1999\",\"imdbID\":\"tt0133093\",\"Type\":\"movie\",\"Poster\":\"https://m.media-amazon.com/images/M/MV5BNzQzOTk3OTAtNDQ0Zi00ZTVkLWI0MTEtMDllZjNkYzNjNTc4L2ltYWdlXkEyXkFqcGdeQXVyNjU0OTQ0OTY@._V1_SX300.jpg\"}," +
            "{\"Title\":\"The Matrix Reloaded\",\"Year\":\"2003\",\"imdbID\":\"tt0234215\",\"Type\":\"movie\",\"Poster\":\"https://m.media-amazon.com/images/M/MV5BODE0MzZhZTgtYzkwYi00YmI5LThlZWYtOWRmNWE5ODk0NzMxXkEyXkFqcGdeQXVyNjU0OTQ0OTY@._V1_SX300.jpg\"}," +
            "{\"Title\":\"The Matrix Revolutions\",\"Year\":\"2003\",\"imdbID\":\"tt0242653\",\"Type\":\"movie\",\"Poster\":\"https://m.media-amazon.com/images/M/MV5BNzNlZTZjMDctZjYwNi00NzljLWIwN2QtZWZmYmJiYzQ0MTk2XkEyXkFqcGdeQXVyNTAyODkwOQ@@._V1_SX300.jpg\"}]," +
            "\"totalResults\":\"87\",\"Response\":\"True\"}";

//parses JSON string in object MovieDetailsData
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
                    movieDetailsData.mRatings.put(source, value);
                }
            }
            movieDetailsData.mTitle = jsonRootObject.getString("Title");
            movieDetailsData.mYear = jsonRootObject.getString("Year");
            movieDetailsData.mRated = jsonRootObject.getString("Rated");
            movieDetailsData.mReleased = jsonRootObject.getString("Released");
            movieDetailsData.mRuntime = jsonRootObject.getString("Runtime");
            movieDetailsData.mGenre = jsonRootObject.getString("Genre");
            movieDetailsData.mDirector = jsonRootObject.getString("Director");
            movieDetailsData.mWriter = jsonRootObject.getString("Writer");
            movieDetailsData.mActors = jsonRootObject.getString("Actors");
            movieDetailsData.mPlot = jsonRootObject.getString("Plot");
            movieDetailsData.mLanguage = jsonRootObject.getString("Language");
            movieDetailsData.mCountry = jsonRootObject.getString("Country");
            movieDetailsData.mAwards = jsonRootObject.getString("Awards");
            movieDetailsData.mPosterUrl = jsonRootObject.getString("Poster");
            movieDetailsData.mMetascore = jsonRootObject.getString("Metascore");
            movieDetailsData.mImdbRating = jsonRootObject.getString("imdbRating");
            movieDetailsData.mImdbVotes = jsonRootObject.getString("imdbVotes");
            movieDetailsData.mId = jsonRootObject.getString("imdbID");
            movieDetailsData.mType = jsonRootObject.getString("Type");
        }
        catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        return movieDetailsData;
    }

//    parses JSON string into array of movies
    public static ArrayList<Movie> parseOmdbSearchJSON(String JSONString){
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
