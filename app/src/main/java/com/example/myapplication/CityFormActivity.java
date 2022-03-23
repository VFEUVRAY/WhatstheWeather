package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.content.Intent;
import android.widget.TextView;

import com.example.myapplication.cities.City;
import com.example.myapplication.cities.CityAdapter;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;
import java.util.Locale;

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
        System.out.println("BEOFRE");
        setContentView(R.layout.activity_city_form);
        System.out.println("AFTER");
        //this.cityAdapter = (CityAdapter) getIntent().getSerializableExtra("adapter");
        this.nameEdit = this.findViewById(R.id.editText_cityName);
        this.countryEdit = this.findViewById(R.id.editText_Country);
        this.raEdit = this.findViewById(R.id.editText_RA);
        this.controller = Controller.get();
        System.out.println("INPUT TYPE: " + this.nameEdit.getInputType());
        this.nameEdit.setVisibility(View.GONE);
        this.raEdit.setVisibility(View.GONE);
        this.countryEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                CityFormActivity.this.check_country_ok();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        this.findViewById(R.id.btn_Submit).setOnClickListener(this);
        this.init_actv_country();
        //this.setResult(0);
        //this.finish();
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_Submit)
            this.submit();
    }

    private void init_actv_country() {
        AutoCompleteTextView country = findViewById(R.id.actv_country);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_expandable_list_item_1,
                new String[] {"France", "England", "Japan"});
        country.setAdapter(adapter);
    }

    public void check_country_ok() {
        String country = this.countryEdit.getText().toString();
        if (this.controller.cities_country_exists(country)) {
            this.nameEdit.setVisibility(View.VISIBLE);
        } else {
            this.countryEdit.requestFocus();
            this.nameEdit.setVisibility(View.GONE);
            this.raEdit.setVisibility(View.GONE);
        }
    }

    public void submit() {
        System.out.println("Not good");
        String name =  this.nameEdit.getText().toString();
        String country = this.countryEdit.getText().toString();
        String ra = "capital";
        if (this.controller.cityForm_submit(
                name,
                country,
                ra
        ))
            this.finish();
        else {
            System.out.println("Not good");
            return;
        }
    }
}