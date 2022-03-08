package com.example.myapplication.cities;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.myapplication.R;

import java.io.Serializable;
import java.util.List;

public class CityAdapter extends BaseAdapter implements Serializable {
    private Context context;
    private List<City> cityList;


    public CityAdapter(Context context, List<City> cityList) {
        this.context = context;
        this.cityList = cityList;
    }

    public boolean exists(City city) {
        for (City _c : this.cityList) {
            if (city.equals(_c))
                return true;
        }
        return false;
    }

    public boolean exists(String name, String country, String ra) {
        for (City _c : this.cityList) {
            if (_c.equals(name, country, ra))
                return true;
        }
        return false;
    }

    @Override
    public int getCount() {
        return this.cityList.size();
    }

    @Override
    public Object getItem(int _id) {
        return this.cityList.get(_id);
    }

    @Override
    public long getItemId(int _id) {
        return _id;
    }

    @Override
    public View getView(int _id, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(this.context).inflate(R.layout.city_row, null);
        }

        City city = this.cityList.get(_id);
        TextView cityName = (TextView) convertView.findViewById(R.id.cityName);
        TextView cityCountry = (TextView) convertView.findViewById(R.id.cityCountry);

        cityName.setText(city.get_name());
        cityCountry.setText(city.get_country());

        return convertView;
    }
}
