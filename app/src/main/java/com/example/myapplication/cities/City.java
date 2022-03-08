package com.example.myapplication.cities;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Locale;

public class City implements Serializable {
    @SerializedName("id")
    @Expose
    private Integer _id;
    @SerializedName("name")
    @Expose
    private String _name;
    @SerializedName("country")
    @Expose
    private String _country;
    @SerializedName("capital")
    @Expose
    private String _capital;

    public City(int id, String name, String country, String capital) {
        this._id = id;
        this._name = name;
        this._country = country;
        this._capital = capital;
    }

    public Integer get_id() {
        return _id;
    }

    public String get_name() {
        return _name;
    }

    public String get_capital() {
        return _capital;
    }

    public String get_country() {
        return _country;
    }

    public void set_id(Integer _id) {
        this._id = _id;
    }

    public void set_name(String _name) {
        this._name = _name;
    }

    public void set_country(String _country) {
        this._country = _country;
    }

    public void set_capital(String _capital) {
        this._capital = _capital;
    }

    public boolean equals(@NonNull City city) {
        boolean equal = city._name.toLowerCase(Locale.ROOT).equals(this._name.toLowerCase(Locale.ROOT));
        equal = equal && city._country.toLowerCase(Locale.ROOT).equals(this._country.toLowerCase(Locale.ROOT));
        return equal && city._capital.toLowerCase(Locale.ROOT).equals(this._capital.toLowerCase(Locale.ROOT));
    }

    public boolean equals(String name, String country, String ra) {
        return (this._name.toLowerCase(Locale.ROOT).equals(name.toLowerCase(Locale.ROOT))
                && this._country.toLowerCase(Locale.ROOT).equals(country.toLowerCase())
                && this._capital.toLowerCase(Locale.ROOT).equals(ra.toLowerCase(Locale.ROOT))
        );
    }
}
