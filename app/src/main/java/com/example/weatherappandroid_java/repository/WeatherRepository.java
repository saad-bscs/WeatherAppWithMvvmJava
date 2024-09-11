package com.example.weatherappandroid_java.repository;

import static java.lang.Character.getType;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.weatherappandroid_java.R;
import com.example.weatherappandroid_java.api.WeatherAPI;
import com.example.weatherappandroid_java.models.WeatherModel;
import com.example.weatherappandroid_java.utils.Constant;
import com.example.weatherappandroid_java.utils.NetworkResult;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Type;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

public class WeatherRepository {

    private final WeatherAPI weatherAPI;

    @Inject
    public WeatherRepository(WeatherAPI weatherAPI) {
        this.weatherAPI = weatherAPI;
    }

    public void getWeatherFromJson(Context context, String city, String apiKey, WeatherCallback callback) {
        String jsonFileString = Constant.getJsonFromAssets(context, "Response.json");
        Timber.e("Data: %s", jsonFileString);

        Gson gson = new Gson();
        WeatherModel weatherModel = gson.fromJson(jsonFileString, WeatherModel.class);

        Timber.e(weatherModel.getCurrent().getCloud().toString());
        if (weatherModel != null) {
            callback.onSuccess(weatherModel);
            Timber.e(weatherModel.getCurrent().getCloud().toString());
        }
        else {
            callback.onError("Failed");
        }

    }

    // Fetch weather data and post results to the ViewModel via a callback
    public void getWeatherFromAPI(String city, String apiKey, WeatherCallback callback) {
        weatherAPI.getWeather(apiKey, city, "no").enqueue(new Callback<WeatherModel>() {
            @Override
            public void onResponse(Call<WeatherModel> call, Response<WeatherModel> response) {
                if (response.isSuccessful() && response.body() != null) {
                    //Log.e("Response-: ", "" + response.toString());
                    Timber.tag("Response-Success: ").e("" + response.body());
                    callback.onSuccess(response.body());
                } else {
                    //Log.e("Response-: ", "" + response.toString());
                    Timber.tag("Response-error: ").e("" + response.message());
                    callback.onError(response.message());
                }
            }

            @Override
            public void onFailure(Call<WeatherModel> call, Throwable t) {
                callback.onError(t.getMessage());
            }
        });
    }

    // Callback interface for communicating between Repository and ViewModel
    public interface WeatherCallback {
        void onSuccess(WeatherModel weather);

        void onError(String error);
    }
}
