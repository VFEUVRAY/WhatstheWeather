package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.icu.lang.UScript;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.Meteo.Forecast;
import com.example.myapplication.Meteo.ForecastResponse;
import com.example.myapplication.Meteo.MiniForecast;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Display_Forecast extends AppCompatActivity {

    private Controller controller;
    private List<Forecast> forecasts;
    private List<MiniForecast> miniForecasts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_forecast);
        this.setSwitchListener();
        this.forecasts = new ArrayList<Forecast>();
        this.miniForecasts = new ArrayList<MiniForecast>();
        this.controller = Controller.get();
        this.controller.set_display_forecast(this);
        this.controller.get_forecast_city();
    }

    @Override
    protected void onDestroy() {
        this.controller.unset_display_forecast();
        super.onDestroy();
    }

    public String wind_dir(Double deg) {
        if (deg > 315 || deg < 46)
            return "North";
        if (deg > 46 && deg < 135)
            return "East";
        if (deg > 136 && deg < 225)
            return "South";
        return "West";

    }

    private Double farenheit_to_celcius(Double temp) {
        return (temp - 32) * 1.8;
    }

    private Double celcius_to_farenheit(Double temp) {
        return (temp + 32) * (9 / 5);
    }

    private Double kelvin_to_farenheit(Double temp) { return (temp * 1.8) - 459.67;}

    private void switch_mini_forcasts() {
        TextView[] tvs = new TextView[] {
                this.findViewById(R.id.tv_infos2),
                this.findViewById(R.id.tv_infos3),
                this.findViewById(R.id.tv_infos4),
                this.findViewById(R.id.tv_infos5)
        };
        int index = 0;
        MiniForecast mf;

        while (index < 4) {
            mf = this.miniForecasts.get(index);
            if (mf.getFarenheit())
                mf.switch_unit(this.farenheit_to_celcius(mf.getTemp()));
            else
                mf.switch_unit(this.celcius_to_farenheit(mf.getTemp()));
            tvs[index].setText(this.miniForecasts.get(index).getSummary());
            index += 1;
        }
    }

    private void make_mini_forecasts(List<Forecast> forecasts) {
        Integer index = 1;
        TextView[] tvs = new TextView[] {
                this.findViewById(R.id.tv_infos2),
                this.findViewById(R.id.tv_infos3),
                this.findViewById(R.id.tv_infos4),
                this.findViewById(R.id.tv_infos5)
        };

        while (index < 5) {
            MiniForecast miniForecast = new MiniForecast(forecasts.get(index));
            tvs[index - 1].setText(miniForecast.getSummary());
            this.miniForecasts.add(miniForecast);
            index += 1;
        }
    }

    public void display(List<Forecast> forecastList) {
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    Display_Forecast.this.make_mini_forecasts(forecastList);
                    Forecast one = forecastList.get(0);
                    one.setTemp(Display_Forecast.this.kelvin_to_farenheit(one.getTemp()));
                    Display_Forecast.this.forecasts.add(one);
                    TextView v_temp = Display_Forecast.this.findViewById(R.id.tv_temp);
                    String temp_display = String.format(Locale.getDefault(), "%g °F", one.getTemp());
                    v_temp.setText(temp_display);
                    v_temp.invalidate();
                    ((TextView) Display_Forecast.this.findViewById(R.id.tv_cityName)).setText(one.getCity());
                    ((TextView) Display_Forecast.this.findViewById(R.id.tv_cityName)).invalidate();
                    temp_display = String.format(Locale.getDefault(), "Wind: spd: %g m/s | %g° (%s)", one.getWind_speed(), one.getWind_dir(), Display_Forecast.this.wind_dir(one.getWind_dir()));
                    ((TextView) Display_Forecast.this.findViewById(R.id.tv_wind)).setText(temp_display);
                    ((TextView) Display_Forecast.this.findViewById(R.id.tv_weatherType)).setText(one.getWeather_type());
                    ((TextView) Display_Forecast.this.findViewById(R.id.tv_date)).setText(one.getDate());
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    Display_Forecast.this.makeToast(new String[] {"Could not update UI"});
                }
            }
        });
    }

    public void makeToast(String[] to_display) {
        StringBuilder buff = new StringBuilder();

        for (String _b : to_display) {
            buff.append(_b);
        }
        Toast.makeText(getApplicationContext(), buff, Toast.LENGTH_SHORT).show();
    }

    private void setSwitchListener() {
        Switch sw_degrees = this.findViewById(R.id.sw_degrees);
        sw_degrees.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                TextView v_temp = Display_Forecast.this.findViewById(R.id.tv_temp);
                Double new_temp;
                //Double old_temp = Double.parseDouble(v_temp.getText().toString());
                Double old_temp = Display_Forecast.this.forecasts.get(0).getTemp();
                String temp_string;
                if (b) {
                    new_temp = Display_Forecast.this.farenheit_to_celcius(old_temp);
                    temp_string = String.format(Locale.getDefault(), "%g °C", new_temp);
                    sw_degrees.setText("Celcius");
                } else {
                    //new_temp = Display_Forecast.this.celcius_to_farenheit(old_temp);
                    new_temp = old_temp;
                    temp_string = String.format(Locale.getDefault(), "%g °F", new_temp);
                    sw_degrees.setText("Farenheit");
                }
                v_temp.setText(temp_string);
                Display_Forecast.this.switch_mini_forcasts();
            }
        });
    }
}