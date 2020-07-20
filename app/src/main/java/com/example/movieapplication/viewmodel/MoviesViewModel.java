package com.example.movieapplication.viewmodel;

import android.app.Application;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;


import com.example.movieapplication.model.MovieDetails;
import com.example.movieapplication.model.MovieRepostory;
public class MoviesViewModel extends ViewModel {

    private MovieRepostory movieRepostory;
    private LiveData<MovieDetails> movieResponseLiveData;


    public void init() {
        movieRepostory = new MovieRepostory();
        movieResponseLiveData = movieRepostory.getMovieResponseLiveData();
    }

    public void searchMovie(String apikey, String type, String page, String s) {
               movieRepostory.searchMovie(apikey,type,page,s);
    }

    public LiveData<MovieDetails> getMovieResponseLiveData() {
        return movieResponseLiveData;
    }

}
