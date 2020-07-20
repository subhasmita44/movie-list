package com.example.movieapplication.model.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitService {
    //public String BASE_URL = "http://www.omdbapi.com";

    private static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://www.omdbapi.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build();


    public static <S> S cteateService(Class<S> serviceClass) {
        return retrofit.create(serviceClass);
    }
}
