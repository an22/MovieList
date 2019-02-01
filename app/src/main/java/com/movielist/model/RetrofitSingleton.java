package com.movielist.model;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitSingleton {

    private Retrofit sRetrofit;

    private static class SingletonHolder{
        static final RetrofitSingleton INSTANCE = new RetrofitSingleton();
    }

    private RetrofitSingleton(){
        sRetrofit = new Retrofit.Builder()
                .baseUrl(TmdbConstants.baseURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static RetrofitSingleton getInstance() {
        return SingletonHolder.INSTANCE;
    }

    public Retrofit getRetrofit(){
        return sRetrofit;
    }
}
