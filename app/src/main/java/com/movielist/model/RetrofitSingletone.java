package com.movielist.model;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitSingletone {
    private Retrofit sRetrofit;

    private static class SingletonHolder{
        static final RetrofitSingletone INSTANCE = new RetrofitSingletone();
    }

    private RetrofitSingletone(){
        sRetrofit = new Retrofit.Builder()
                .baseUrl(TmdbConstants.baseURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static RetrofitSingletone getInstance() {
        return SingletonHolder.INSTANCE;
    }

    public Retrofit getRetrofit(){
        return sRetrofit;
    }
}
