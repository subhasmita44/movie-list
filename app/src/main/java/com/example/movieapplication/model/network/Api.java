package com.example.movieapplication.model.network;



import com.example.movieapplication.model.MovieDetails;


import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Api {

    public String BASE_URL="http://www.omdbapi.com";

    @GET("/")
    Call<MovieDetails> getPhotoList(
            @Query("type") String type,
            @Query("apikey") String apikey,
            @Query("page") String page,
            @Query("s") String s);
}
