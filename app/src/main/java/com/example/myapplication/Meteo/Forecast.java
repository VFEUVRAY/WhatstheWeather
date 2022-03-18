package com.example.myapplication.Meteo;

public class Forecast {
    private Double temp;
    private Double wind_speed;
    private Double wind_dir;
    private String weather_type;

    public Forecast(Double temp, Double wind_speed, Double wind_dir, String weather_type) {
        this.temp = temp;
        this.weather_type = weather_type;
        this.wind_dir = wind_dir;
        this.wind_speed = wind_speed;
    }

    public Double getTemp() {
        return temp;
    }

    public void setTemp(Double temp) {
        this.temp = temp;
    }

    public Double getWind_speed() {
        return wind_speed;
    }

    public void setWind_speed(Double wind_speed) {
        this.wind_speed = wind_speed;
    }

    public Double getWind_dir() {
        return wind_dir;
    }

    public void setWind_dir(Double wind_dir) {
        this.wind_dir = wind_dir;
    }

    public String getWeather_type() {
        return weather_type;
    }

    public void setWeather_type(String weather_type) {
        this.weather_type = weather_type;
    }
}
