package com.example.myapplication.cities;

import java.util.ArrayList;
import java.util.List;

public class CityModel {
    /*
    General model for City related operations
     */
    private List<City> _list;
    private List<String> _available_countries;

    public CityModel() {
        this._list = new ArrayList<City>();
        this._available_countries = new ArrayList<String>();
    }

    public List<City> get_list() {
        return _list;
    }

    private void _seed() {

    }

    public boolean exists(City city) {
        for (City _c : this._list) {
            if (city.equals(_c))
                return true;
        }
        return false;
    }

    public boolean exists(String name, String country, String ra) {
        for (City _c : this._list) {
            if (_c.equals(name, country, ra))
                return true;
        }
        return false;
    }

    public boolean add(String name, String country, String ra) {
        if (this.exists(name, country, ra))
                return false;
        this._list.add(new City(this._list.size(), name, country, ra));
        return true;
    }


}
