package com.example.weatherappandroid_java.viewModels;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.weatherappandroid_java.models.WeatherModel;
import com.example.weatherappandroid_java.repository.WeatherRepository;
import com.example.weatherappandroid_java.utils.NetworkResult;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class WeatherViewModel extends ViewModel {
    private final WeatherRepository weatherRepository;
    private final MutableLiveData<WeatherModel> weatherLiveData = new MutableLiveData<>();
    private final MutableLiveData<String> errorLiveData = new MutableLiveData<>();

    @Inject
    public WeatherViewModel(WeatherRepository weatherRepository) {
        this.weatherRepository = weatherRepository;
    }

    public LiveData<WeatherModel> getWeatherLiveData() {
        return weatherLiveData;
    }

    public LiveData<String> getErrorLiveData() {
        return errorLiveData;
    }

    // Fetch weather data through the repository
    public void fetchWeather(String city, String apiKey, Context context) {
        weatherRepository.getWeatherFromJson(context, city, apiKey, new WeatherRepository.WeatherCallback() {
            @Override
            public void onSuccess(WeatherModel weather) {
                weatherLiveData.postValue(weather);
            }

            @Override
            public void onError(String error) {
                errorLiveData.postValue(error);
            }
        });
    }
}
