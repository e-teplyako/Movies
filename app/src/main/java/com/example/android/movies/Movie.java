package com.example.android.movies;

public class Movie {

    private String _title;
    private String _year;
    private String _id;
    private String _type;
    private String _posterUrl;

    public Movie (String title, String year, String id, String type, String posterUrl) {
        _title = title;
        _year = year;
        _id = id;
        _type = type;
        _posterUrl = posterUrl;
    }

    public String getId() {
        return _id;
    }
    public String getTitle() {return _title;}
    public String getPosterUrl() {return _posterUrl;}
    public String getYear() {return _year;}
    public String getType() {return _type;}

    @Override
    public String toString() {
        String s = "Title: " + _title + '\n' + "Year: " + _year + '\n' + "imdbID: " + _id + '\n' + "Type: " + _type + '\n' + "Poster URL: " + _posterUrl + '\n' + '\n';
        return s;
    }
}
