package com.example.myapplication.retrofit;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

import com.example.myapplication.retrofit.test.Fake;

import java.util.List;

public interface FakeAPI {
    @GET("todos/")
    Call<List<Fake>> LoadTodos(@Query("q") String status);
}
