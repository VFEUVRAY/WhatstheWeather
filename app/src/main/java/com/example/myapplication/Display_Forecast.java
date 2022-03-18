package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.icu.lang.UScript;
import android.os.Bundle;
import android.widget.TextView;

import com.example.myapplication.Meteo.Forecast;
import com.example.myapplication.Meteo.ForecastResponse;

import java.util.List;

public class Display_Forecast extends AppCompatActivity {

    private Controller controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_forecast);
        this.controller = Controller.get();
        this.controller.set_display_forecast(this);
        this.controller.get_forecast_city();
    }

    @Override
    protected void onDestroy() {
        this.controller.unset_display_forecast();
        super.onDestroy();
    }

    public void display(List<Forecast> forecastList) {
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Forecast one = forecastList.get(0);
                TextView v_temp = Display_Forecast.this.findViewById(R.id.tv_temp);
                v_temp.setText(one.getTemp().toString() + "°F");
                System.out.println(one.getWind_dir().toString());
                System.out.println(one.getTemp());
                System.out.println(one.getWind_speed());
            }
        });
    }
}