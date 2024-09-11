package com.example.weatherappandroid_java.api;

import com.example.weatherappandroid_java.models.WeatherModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherAPI {

    @GET("/v1/current.json")
    Call<WeatherModel> getWeather(
            @Query("key") String apiKey,
            @Query("q") String city,
            @Query("aqi") String aqi
    );
}