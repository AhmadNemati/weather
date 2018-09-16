package com.example.atta.myapplication;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Ahmad Nemati on 9/13/18.
 */
public class Service {
    private Retrofit retrofit;
    private WeatherApi service;
    private static final Service ourInstance = new Service();

    public static Service getInstance() {
        return ourInstance;
    }

    private Service() {
        retrofit = new Retrofit.Builder()
                .baseUrl("https://api.darksky.net/forecast/3e2214103d829752a8b7399c8bec16ca/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

         service = retrofit.create(WeatherApi.class);

    }

    public WeatherApi getService()
    {
        return service;
    }
}
