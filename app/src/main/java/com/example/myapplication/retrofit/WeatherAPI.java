package com.example.myapplication.retrofit;

import com.example.myapplication.Meteo.ForecastResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface WeatherAPI {

    @GET("forecast")
    Call<ForecastResponse> getForecast(@Header("x-rapidapi-host") String host,
                                       @Header("x-rapidapi-key") String key,
                                       @Query("q") String city);

    @GET("forecast")
    Call<ForecastResponse> getFromcastFromCoords(@Header("x-rapidapi-host") String host,
                                                 @Header("x-rapidapi-key") String key,
                                                 @Query("lat") Double latitude,
                                                 @Query("lon") Double longitude);
}
