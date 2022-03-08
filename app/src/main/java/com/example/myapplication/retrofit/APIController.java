package com.example.myapplication.retrofit;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIController implements Callback<List<Change>>{

    static final String BASE_URL = "https://community-open-weather-map.p.rapidapi.com/";

    public void start() {
        Gson gson = new GsonBuilder().setLenient().create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(APIController.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        WeatherAPI weatherAPI = retrofit.create(WeatherAPI.class);
        Call<List<Change>> call = weatherAPI.LoadChanges("status:open");
        call.enqueue(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onResponse(Call<List<Change>> call, Response<List<Change>> response) {
        if (response.isSuccessful()) {
            List<Change> changesList = response.body();
            changesList.forEach(change -> System.out.println(change.getSubject()));
        } else {
            System.out.println(response.errorBody());
        }
    }

    @Override
    public void onFailure(Call<List<Change>> call, Throwable error) {
        error.printStackTrace();
    }
}
