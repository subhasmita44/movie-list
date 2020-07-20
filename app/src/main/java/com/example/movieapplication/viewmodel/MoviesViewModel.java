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
import com.example.movieapplication.model.network.Api;
import com.example.movieapplication.view.MainActivity;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MoviesViewModel extends ViewModel {
    /*String apikey;
    String s;
    String page;
    String type;
    Context con;
    private MutableLiveData<MovieDetails> heroList;

    //we will call this method to get the data
    public LiveData<MovieDetails> getMovies() {
        //if the list is null
        if (heroList == null) {
            heroList = new MutableLiveData<MovieDetails>();
            //we will load it asynchronously from server in this method
            loadHeroes();
        }

        //finally we will return the list
        return heroList;
    }


    //This method is using Retrofit to get the JSON data from URL
    private void loadHeroes() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Api api = retrofit.create(Api.class);
        //Call<MovieDetails> call = api.getPhotoList(type,apikey,page,s);

Call<MovieDetails>call=api.getPhotoList(type,apikey,page,s);
        call.enqueue(new Callback<MovieDetails>() {
            @Override
            public void onResponse(Call<MovieDetails>call, Response<MovieDetails> response) {
try{
    if (response.isSuccessful()){
        Log.v("TAG","list"+"call");

            heroList.setValue(response.body());


    }else{
        //Toast.makeText()
    }
}catch (Exception e){
    Toast.makeText(con, "Movie Not Found", Toast.LENGTH_SHORT).show();

}

            }

            @Override
            public void onFailure(Call<MovieDetails> call, Throwable t) {
                Log.v("TAG",t.getMessage());
            }
        });
    }



    public void setMovieName(String apikey, String type, String page, String s, Context con) {
        this.apikey=apikey;
        this.type=type;
        this.page=page;
        this.s=s;
        this.con=con;
        getMovies();
    }*/
    private MovieRepostory movieRepostory;
    private LiveData<MovieDetails> movieResponseLiveData;


    public void init() {
        movieRepostory = new MovieRepostory();
        movieResponseLiveData = movieRepostory.getMovieResponseLiveData();
    }

    public void searchMovie(String apikey, String type, String page, String s) {
       // Dotenv dotenv = Dotenv.configure().directory("/assets").filename("env").load();
        movieRepostory.searchMovie(apikey,type,page,s);
    }

    public LiveData<MovieDetails> getMovieResponseLiveData() {
        return movieResponseLiveData;
    }

}
