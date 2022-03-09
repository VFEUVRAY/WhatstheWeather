package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.content.Intent;

import com.example.myapplication.cities.City;
import com.example.myapplication.cities.CityAdapter;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class CityFormActivity extends AppCompatActivity implements View.OnClickListener {

    private CityAdapter cityAdapter;
    private List<City> cities;
    private EditText nameEdit;
    private EditText countryEdit;
    private EditText raEdit;
    private Controller controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_form);
        //this.cityAdapter = (CityAdapter) getIntent().getSerializableExtra("adapter");
        this.cityAdapter = new CityAdapter(getApplicationContext(), (List<City>) getIntent().getSerializableExtra("line"));
        this.nameEdit = this.findViewById(R.id.editText_cityName);
        this.countryEdit = this.findViewById(R.id.editText_Country);
        this.raEdit = this.findViewById(R.id.editText_RA);
        this.controller = Controller.get();
        this.setResult(0);
        this.finish();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_Submit:
                this.submit();
                break;
            default:
                break;
        }
    }

    public void submit() {
        String name =  this.nameEdit.getText().toString();
        String country = this.countryEdit.getText().toString();
        String ra = this.raEdit.getText().toString();
        if (this.cityAdapter.exists(
                name,
                country,
                ra
        )){
            System.out.println("Not good");
            return;
        }
        String[] infos = new String[3];
        infos[0] = name;
        infos[1] = country;
        infos[2] = ra;
        System.out.println("Good");
        Intent intent = new Intent();
        intent.putExtra("infos", infos);
        this.setResult(0, intent);
        this.finish();
    }
}