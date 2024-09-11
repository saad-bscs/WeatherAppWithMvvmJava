package com.example.weatherappandroid_java.di;

import com.example.weatherappandroid_java.api.WeatherAPI;
import com.example.weatherappandroid_java.utils.Constant;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@InstallIn(SingletonComponent.class)
@Module
public class NetworkModule {

    @Provides
    @Singleton
    public Retrofit provideRetrofit() {
        return new Retrofit.Builder()
                .baseUrl(Constant.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    @Provides
    @Singleton
    public WeatherAPI provideApiService(Retrofit retrofit) {
        return retrofit.create(WeatherAPI.class);
    }
}
