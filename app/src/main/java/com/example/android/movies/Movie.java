package com.example.android.movies;

public class Movie {

    private String mTitle;
    private String mYear;
    private String mId;
    private String mType;
    private String mPosterUrl;

    public Movie (String title, String year, String id, String type, String posterUrl) {
        mTitle = title;
        mYear = year;
        mId = id;
        mType = type;
        mPosterUrl = posterUrl;
    }

    public String getId() {
        return mId;
    }
    public String getTitle() {return mTitle;}
    public String getPosterUrl() {return mPosterUrl;}
    public String getYear() {return mYear;}
    public String getType() {return mType;}

    @Override
    public String toString() {
        String s = "Title: " + mTitle + '\n' + "Year: " + mYear + '\n' + "imdbID: " + mId + '\n' + "Type: " + mType + '\n' + "Poster URL: " + mPosterUrl + '\n' + '\n';
        return s;
    }
}
