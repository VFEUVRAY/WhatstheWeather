package com.example.myapplication;

import android.content.Context;

import com.example.myapplication.cities.City;
import com.example.myapplication.cities.CityAdapter;
import com.example.myapplication.cities.CityModel;

import java.util.List;

public class Controller {
    /*
        Ugly Singleton controller
        Use as a pass through for views/activities to controllers
     */
    private static Object _instance = null;
    private List<Object> activities;
    public CityAdapter cityAdapter;
    private CityModel cityModel;

    private Controller() {
        this.cityModel = null;
        Controller._instance = this;
    }

    public static Controller get() {
        if (Controller._instance == null) {
            return new Controller();
        }
        return (Controller) Controller._instance;
    }

    public List<City> cities_getList() {
        if (this.cityModel == null){

        }
        return this.cityModel.get_list();
    }

    public boolean cityForm_submit(String name, String country, String ra) {
        return this.cityModel.add(name, country, ra);
    }
}
