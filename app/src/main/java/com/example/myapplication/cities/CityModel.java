package com.example.myapplication.cities;

import android.content.Context;

import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import com.opencsv.CSVReaderHeaderAware;

public class CityModel {
    /*
    General model for City related operations
     */
    private List<City> _list;
    private List<String> _available_countries;
    private List<City> _favorites;
    private boolean _seeded;

    public CityModel() {
        this._list = new ArrayList<City>();
        this._available_countries = new ArrayList<String>();
        this._favorites = new ArrayList<City>();
        this._seeded = false;
    }

    public List<City> get_list() {
        return _list;
    }

    public List<String> get_available_countries() {
        return _available_countries;
    }

    public void _seed(Context context) {
        System.out.println("SEEDING START");
        try {
            InputStreamReader fileReader = new InputStreamReader(context.getAssets().open("worldcities.csv"));
            CSVReaderHeaderAware reader = new CSVReaderHeaderAware(fileReader);
            Map<String, String> values;
            String country;
            while (true) {
                values = reader.readMap();
                if (values == null)
                    break;
                country = values.get("country");
                this._list.add(new City(
                        this._list.size(),
                        values.get("city_ascii"),
                        country
                ));
                if (!this._available_countries.contains(country))
                    this._available_countries.add(country.toLowerCase(Locale.ROOT));
                this._seeded = true;
            }
        } catch (Exception e) {
            System.out.println("SEEDING FAILED");
            System.out.println(e);
            return;
        }
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
            if (_c.equals(name, country))
                return true;
        }
        return false;
    }

    public City find(String name, String country) {
        for (City _c : this._list) {
            if (_c.equals(name,country))
                return _c;
        }
        return null;
    }

    public boolean add(String name, String country, String ra) {
        if (this.exists(name, country, ra))
                return false;
        this._list.add(new City(this._list.size(), name, country));
        return true;
    }

    public boolean add_favorite(String name, String country, String ra) {
        City found = this.find(name, country);
        if (found != null) {
            this._favorites.add(found);
            return true;
        }
        return false;
    }
}
