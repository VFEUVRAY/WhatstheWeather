package com.example.myapplication;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;

import com.example.myapplication.Meteo.Forecast;
import com.example.myapplication.Meteo.ForecastModel;
import com.example.myapplication.Meteo.ForecastResponse;
import com.example.myapplication.cities.City;
import com.example.myapplication.cities.CityAdapter;
import com.example.myapplication.cities.CityModel;
import com.example.myapplication.retrofit.APIController;
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
    private Display_Forecast display_activity;
    private ForecastModel forecastModel;

    private APIController apiController;
    private ConnectivityManager connectivityManager;

    private Boolean apiStarted;

    private String pending_request;
    private Double[] pending_request_loc;

    private Controller() {
        this.cityModel = null;
        Controller._instance = this;
        this.apiController = new APIController(this);
        this.apiController.init();
        this.apiStarted = false;
        this.display_activity = null;
        this.pending_request = null;
        this.pending_request_loc = new Double[] {0.0,0.0};
        this.forecastModel = new ForecastModel(this);
    }

    public static Controller get() {
        if (Controller._instance == null) {
            return new Controller();
        }
        return (Controller) Controller._instance;
    }

    public void set_display_forecast(Display_Forecast display_activity) {
        this.display_activity = display_activity;
    }

    public void unset_display_forecast() {
        this.display_activity = null;
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

    public void store_forecast(ForecastResponse forecastResponse) {
        Integer forecast_id = this.forecastModel.add_id(forecastResponse);
        this.forecastModel.handle_forecast(forecast_id);
    }

    public Boolean set_pending_forecast(Double lat, Double lon) {
        if (this.check_api()) {
            this.pending_request_loc[0] = lat;
            this.pending_request_loc[1] = lon;
        }
        return false;
    }

    public Boolean get_forecast_city() {
        if (this.check_api()) {
            this.apiController.get_forecast_from_coordinates(this.pending_request_loc[0], this.pending_request_loc[1]);
            return true;
        }
        return false;
    }

    public void forecast_ready(List<Forecast> forecasts) {
        if (this.display_activity != null) {
            this.display_activity.display(forecasts);
        }
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
