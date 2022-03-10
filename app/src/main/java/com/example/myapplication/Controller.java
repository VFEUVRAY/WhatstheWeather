package com.example.myapplication;

import android.content.Context;

import com.example.myapplication.cities.City;
import com.example.myapplication.cities.CityAdapter;
import com.example.myapplication.cities.CityModel;

import java.util.List;
import java.util.Locale;

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

    public void init_cities(Context context) {
        if (this.cityModel == null)
            this.cityModel = new CityModel();
        this.cityModel._seed(context);
    }

    public boolean cities_country_exists(String country) {
        return this.cityModel.get_available_countries().contains(country.toLowerCase(Locale.ROOT));
    }

    public List<City> cities_getList() {
        if (this.cityModel == null){
            this.cityModel = new CityModel();
        }
        return this.cityModel.get_list();
    }

    public boolean cityForm_submit(String name, String country, String ra) {
        if (name.length() == 0 || country.length() == 0 || ra.length() == 0)
            return false;
        return this.cityModel.add_favorite(name, country, ra);
    }
}
