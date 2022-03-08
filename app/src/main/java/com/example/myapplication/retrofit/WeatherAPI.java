package com.example.myapplication.retrofit;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherAPI {

    @GET("changes/")
    Call<List<Change>> LoadChanges(@Query("q") String status);
}
