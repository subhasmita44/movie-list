package com.example.movieapplication.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Search {
    @SerializedName("imdbID")
    @Expose
    private String imdbID;
    @SerializedName("Poster")
    @Expose
    private String Poster;

    @SerializedName("Title")
    @Expose
    private String Title;

    @SerializedName("Type")
    @Expose
    private String Type;

    @SerializedName("Year")
    @Expose
    private String Year;
    public Search(){

    }
    public Search(String response, String poster, String title, String type, String year) {
        imdbID = response;
        Poster = poster;
        Title = title;
        Type = type;
        Year = year;
    }


    public String getPoster() {
        return Poster;
    }

    public String getImdbID() {
        return imdbID;
    }

    public String getTitle() {
        return Title;
    }

    public String getType() {
        return Type;
    }

    public String getYear() {
        return Year;
    }
}
