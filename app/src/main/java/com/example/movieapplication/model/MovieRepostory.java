package com.example.movieapplication.model;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.movieapplication.model.network.Api;
import com.example.movieapplication.model.network.RetrofitService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieRepostory {

    private Api movieSearchService;
    private MutableLiveData<MovieDetails> movieResponseLiveData;

    public MovieRepostory() {
        movieResponseLiveData = new MutableLiveData<>();
        movieSearchService = RetrofitService.cteateService(Api.class);
    }

    public void searchMovie(String api,String type,String page,String s) {
        movieSearchService.getPhotoList(type,api,page,s)
                .enqueue(new Callback<MovieDetails>() {
                    @Override
                    public void onResponse(Call<MovieDetails> call, Response<MovieDetails> response) {
                        Log.v("TAG",""+call.request().url());
                        if (response.body() != null) {
                            Log.v("TAG","success+bbj");
                            movieResponseLiveData.postValue(response.body());
                        }
                    }

                    @Override
                    public void onFailure(Call<MovieDetails> call, Throwable t) {
                       // movieResponseLiveData.postValue(null);
                        Log.v("TAG","fail"+t.getMessage());

                    }
                });
    }

    public LiveData<MovieDetails> getMovieResponseLiveData() {
        return movieResponseLiveData;
    }
}
