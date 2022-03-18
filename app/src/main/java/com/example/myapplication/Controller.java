package com.example.myapplication;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;

import com.example.myapplication.cities.City;
import com.example.myapplication.cities.CityAdapter;
import com.example.myapplication.cities.CityModel;
import com.example.myapplication.retrofit.F_APIController;

import java.util.ArrayList;
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

    private F_APIController apiController;
    private ConnectivityManager connectivityManager;

    private Boolean apiStarted;

    private Controller() {
        this.cityModel = null;
        Controller._instance = this;
        this.apiController = new F_APIController();
        this.apiController.init();
        this.apiStarted = false;
    }

    public static Controller get() {
        if (Controller._instance == null) {
            return new Controller();
        }
        return (Controller) Controller._instance;
    }

    // API

    public void init_connectivity(Context context) {
        this.connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
    }

    public Boolean is_user_online() {
        Network[] networks = this.connectivityManager.getAllNetworks();
        NetworkInfo info;
        Boolean wifi = false;
        Boolean mobile = false;
        for (Network _n : networks) {
            info = this.connectivityManager.getNetworkInfo(_n);
            if (info.isConnected()) {
                mobile = info.getTypeName().equals("MOBILE");
                wifi = info.getTypeName().equals("WIFI");
            }
        }
        return wifi || mobile;
    }

    private Boolean check_api() {
        if (is_user_online()) {
            if (!this.apiStarted) {
                //this.apiController.start();
                this.apiStarted = true;
            }
            return true;
        }
        return false;
    }

    public Boolean call_fake_api() {
        if (this.check_api()) {
            this.apiController.one();
        }
        return false;
    }

    // CITIES

    public void init_cities(Context context) {
        if (this.cityModel == null)
            this.cityModel = new CityModel();
        //this.cityModel._seed(context);
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
