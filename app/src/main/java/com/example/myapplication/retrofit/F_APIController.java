package com.example.myapplication.retrofit;

import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.example.myapplication.retrofit.test.Fake;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class F_APIController implements Callback<List<Fake>>{

    static final String BASE_URL = "https://community-open-weather-map.p.rapidapi.com/";
    static final String FORCAST_EXT = "forecast?q=";
    static final String API_KEY = "ee15af9478msh15eaeaef8eda11fp169351jsn1cac4074d864";
    static final String HOST = "community-open-weather-map.p.rapidapi.com";

    static final String T_BASE_URL = "https://jsonplaceholder.typicode.com/";
    static final String T_TODO_EXT = "todos/";

    public void start() {
        Gson gson = new GsonBuilder().setLenient().create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(F_APIController.T_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        //WeatherAPI weatherAPI = retrofit.create(WeatherAPI.class);
        FakeAPI fakeAPI = retrofit.create(FakeAPI.class);
        //Call<List<Fake>> call = weatherAPI.LoadFakes("status:open");
        Call<List<Fake>> call = fakeAPI.LoadTodos("status:open");
        call.enqueue(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onResponse(@NonNull Call<List<Fake>> call, Response<List<Fake>> response) {
        if (response.isSuccessful()) {
            List<Fake> FakesList = response.body();
            FakesList.forEach(Fake -> System.out.println(Fake.getTitle()));
        } else {
            System.out.println(response.errorBody());
        }
    }

    @Override
    public void onFailure(Call<List<Fake>> call, Throwable error) {
        error.printStackTrace();
    }
}
