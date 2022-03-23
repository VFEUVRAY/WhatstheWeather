package com.example.myapplication.Meteo;

import java.util.Locale;

public class MiniForecast {
    private String summary;
    private Double temp;
    private Boolean farenheit;
    private String weather;
    private String date;

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public Double getTemp() {
        return temp;
    }

    public void setTemp(Double temp) {
        this.temp = temp;
    }

    public Boolean getFarenheit() {
        return farenheit;
    }

    public void setFarenheit(Boolean farenheit) {
        this.farenheit = farenheit;
    }

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }

    public MiniForecast(Forecast forecast) {
        this.temp = this.k_to_f(forecast.getTemp());
        this.weather = forecast.getWeather_type();
        this.farenheit = true;
        this.date = forecast.getDate().split(" ")[0];

        this.make_summary();
    }

    private Double k_to_f(Double temp) {return (temp * 1.8) - 459.67;}

    private void make_summary() {
        if (this.farenheit)
            this.summary = String.format(Locale.getDefault(), "%s | %g °F | %s", this.date, this.temp, this.weather);
        else
            this.summary = String.format(Locale.getDefault(), "%s | %g °C | %s", this.date, this.temp, this.weather);
    }

    public void switch_unit(Double new_temp) {
        this.farenheit = !this.farenheit;

        this.temp = new_temp;
        this.make_summary();
    }
}
