package com.example.myapplication.Meteo;

import com.example.myapplication.Controller;

import java.security.Key;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ForecastModel {
    Map<Integer, ForecastResponse> forecasts;
    Controller controller;

    public ForecastModel(Controller controller) {
        this.controller = Controller.get();
        this.forecasts = new HashMap<Integer, ForecastResponse>();
    }

    public Integer add_id(ForecastResponse forecastResponse) {
        Integer id = this.new_id();
        this.forecasts.put(this.new_id(), forecastResponse);
        return id;
    }

    public void handle_forecast(Integer id) {
        this.controller.forecast_ready(this.get_wheather(id));
    }

    private Integer new_id() {
        Integer highest = 0;
        for (Integer key : this.forecasts.keySet()) {
            if (key > highest) {
                highest = key;
            }
        }
        return highest + 1;
    }

    private String get_wheather_type(Map<String, Object> timeSlice) {
        List<Map<String, Object>> category = (ArrayList<Map<String, Object>>) timeSlice.get("weather");
        return (String) category.get(0).get("main");

    }

    private Double[] get_wind(Map<String, Object> timeSlice) {
        Map<String, Object> category = (Map<String, Object>) timeSlice.get("wind");
        return new Double[] {
                (Double) category.get("speed"),
                (Double) category.get("deg")
        };
    }

    private Double get_temp(Map<String, Object> timeSlice) {
        Map<String, Object> category = (Map<String, Object>) timeSlice.get("main");
        return (Double) category.get("temp");
    }

    public List<Forecast> get_wheather(Integer id) {
        ForecastResponse forecastResponse = this.forecasts.get(id);
        Integer[] indices = new Integer[] {3, 11, 19, 27, 35};
        List<Forecast> res = new ArrayList<Forecast>();
        Map<String, Object> timeSlice;
        Double[] wind;

        if (forecastResponse != null) {
            System.out.println("RESPONSE NOT NULL");
            List<Object> infos = forecastResponse.getList();
            for (Integer _i : indices) {
                timeSlice = (Map<String, Object>) infos.get(_i);
                wind = this.get_wind(timeSlice);
                res.add(new Forecast(
                        this.get_temp(timeSlice),
                        wind[0],
                        wind[1],
                        this.get_wheather_type(timeSlice)
                ));
            }
            System.out.println("RETURNING DATA");
            return res;
        }
        System.out.println("RESPONSE NULL");
        return null;
    }
}
