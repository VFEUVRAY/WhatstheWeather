package com.example.myapplication;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Button;

import com.example.myapplication.cities.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity  implements View.OnClickListener{

    private List<City> cities = new ArrayList<City>();
    private CityAdapter cityAdapter;
    private ListView citiesView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.findViewById(R.id.btn_switchActivity).setOnClickListener(this);

        this.citiesView = this.findViewById(R.id.citiesView);
        this.cityAdapter = new CityAdapter(getApplicationContext(), this.cities);
        this.citiesView.setAdapter(this.cityAdapter);

        City defaultCity = new City(4980, "Paris", "France", "Capital");
        this.cities.add(defaultCity);
        this.cities.add(new City(
                1,
                "Kyiv",
                "Ukraine",
                "Capital"
        ));
        this.cities.add(new City (
                2,
                "Birmhingan",
                "England",
                "None"
        ));
        this.cityAdapter.notifyDataSetChanged();

        //layout.addView(nb);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
//    @Override
//    protected void onResume() {
//        super.onResume();
//        System.out.println("REUSIMING");
//        Toolbar tb = this.findViewById(R.id.toolbar);
//        tb.setTitle((CharSequence) "Welcome Back");
//    }

    @Override
    public void onClick(View v) {
        int caller_id = v.getId();

        if (caller_id == R.id.btn_switchActivity)
            this.startForm(v);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void startForm(View v) {
        Intent intent = new Intent(this, CityFormActivity.class);
        intent.putExtra("list", (Serializable) this.cities);
        startActivity(intent);
    }

    private void changeText(View v) {
        System.out.println("Hello");
        EditText ed = this.findViewById(R.id.editText_Name);
        TextView t = this.findViewById(R.id.textView4);
        String text = "salut";
        t.setText(ed.getText().toString());
        ListView lst = this.findViewById(R.id.citiesView);
    }
}