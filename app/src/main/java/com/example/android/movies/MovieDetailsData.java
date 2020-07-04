package com.example.android.movies;

import android.content.ContentValues;

public class MovieDetailsData {

    public static final String IMDB_RATING_KEY = "Internet Movie Database";
    public static final String RT_RATING_KEY = "Rotten Tomatoes";
    public static final String METACRITIC_RATING_KEY = "Metacritic";

    public String Title;
    public String Year;
    public String Id;
    public String Type;
    public String PosterUrl;
    public String Rated;
    public String Released;
    public String Runtime;
    public String Genre;
    public String Director;
    public String Writer;
    public String Actors;
    public String Plot;
    public String Language;
    public String Country;
    public String Awards;
    public ContentValues Ratings;
    public String Metascore;
    public String ImdbRating;
    public String ImdbVotes;

    public MovieDetailsData() {
        Ratings = new ContentValues();
    }

    @Override
    public String toString() {
        if (this == null){
            return "";
        }
        String data = Title + '\n' + Year + '\n' + Id + '\n' + Type + '\n' + PosterUrl + '\n' + Rated + '\n' + Released + '\n' + Runtime + '\n' +
                Genre + '\n' + Director + '\n' + Actors + '\n' + Writer + '\n' + Plot + '\n' + Language + '\n' + Country + '\n' + Awards + '\n' +
                Ratings.toString();
        return data;
    }
}
