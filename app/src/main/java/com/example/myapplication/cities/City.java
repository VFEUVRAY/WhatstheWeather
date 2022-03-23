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
    @SerializedName("lat")
    @Expose
    private Double _lat;
    @SerializedName("lon")
    @Expose
    private Double _lon;
    @SerializedName("iso")
    @Expose
    private String iso;



    public City(int id, String name, String country) {
        this._id = id;
        this._name = name;
        this._country = country;
        this._lat = null;
        this._lon = null;
        this.iso = null;
    }

    public City(int id, String name, String country, String iso) {
        this._id = id;
        this._name = name;
        this._country = country;
        this._lat = null;
        this._lon = null;
        this.iso = iso;
    }

    public City(int id, String name, String country, Double lat, Double lon) {
        this._id = id;
        this._name = name;
        this._country = country;
        this._lat = lat;
        this._lon = lon;
        this.iso = null;
    }

    public Integer get_id() {
        return _id;
    }

    public String get_name() {
        return _name;
    }

    public String get_country() {
        return _country;
    }

    public Double get_lat() { return _lat; }

    public Double get_lon() { return _lon; }

    public void set_id(Integer _id) {
        this._id = _id;
    }

    public void set_name(String _name) {
        this._name = _name;
    }

    public void set_country(String _country) {
        this._country = _country;
    }

    public void set_lat(Double _lat) { this._lat = _lat; }

    public void set_lon(Double _lon) { this._lon = _lon; }

    public String getIso() {
        return iso;
    }

    public void setIso(String iso) {
        this.iso = iso;
    }

    public boolean equals(@NonNull City city) {
        boolean equal = city._name.toLowerCase(Locale.ROOT).equals(this._name.toLowerCase(Locale.ROOT));
        equal = equal && city._country.toLowerCase(Locale.ROOT).equals(this._country.toLowerCase(Locale.ROOT));
        return equal;
    }

    public boolean equals(String name, String country) {
        return (this._name.toLowerCase(Locale.ROOT).equals(name.toLowerCase(Locale.ROOT))
                && this._country.toLowerCase(Locale.ROOT).equals(country.toLowerCase())
        );
    }
}
