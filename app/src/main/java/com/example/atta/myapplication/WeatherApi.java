package com.example.atta.myapplication;

import com.example.atta.myapplication.model.WeatherModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Ahmad Nemati on 9/13/18.
 */
public interface WeatherApi {
    @GET("{lat},{long}")
    Call<WeatherModel> getWeather(@Path("lat") double lat,
                                  @Path("long") double lng);

}
