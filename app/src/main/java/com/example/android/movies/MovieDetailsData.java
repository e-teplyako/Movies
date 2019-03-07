package com.example.android.movies;

import android.content.ContentValues;

public class MovieDetailsData {

    public static final String IMDB_RATING_KEY = "Internet Movie Database";
    public static final String RT_RATING_KEY = "Rotten Tomatoes";
    public static final String METACRITIC_RATING_KEY = "Metacritic";

    public String mTitle;
    public String mYear;
    public String mId;
    public String mType;
    public String mPosterUrl;
    public String mRated;
    public String mReleased;
    public String mRuntime;
    public String mGenre;
    public String mDirector;
    public String mWriter;
    public String mActors;
    public String mPlot;
    public String mLanguage;
    public String mCountry;
    public String mAwards;
    public ContentValues mRatings;
    public String mMetascore;
    public String mImdbRating;
    public String mImdbVotes;

    public MovieDetailsData() {
        mRatings = new ContentValues();
    }

    @Override
    public String toString() {
        if (this == null){
            return "";
        }
        String data = mTitle + '\n' + mYear + '\n' + mId + '\n' + mType + '\n' + mPosterUrl + '\n' + mRated + '\n' + mReleased + '\n' + mRuntime + '\n' +
                mGenre + '\n' + mDirector + '\n' + mActors + '\n' + mWriter + '\n' + mPlot + '\n' + mLanguage + '\n' + mCountry + '\n' + mAwards + '\n' +
                mRatings.toString();
        return data;
    }
}
