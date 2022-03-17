package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Button;
import android.widget.Toast;

import com.example.myapplication.cities.*;
import com.example.myapplication.location.Locator;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity  implements View.OnClickListener{

    private List<City> cities;
    private CityAdapter cityAdapter;
    private ListView citiesView;
    private Controller controller;
    public LocationManager locationManager;
    public Locator locationListener;
    public final int REQUEST_PERMISSION_LOCATION_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.controller = Controller.get();
        this.controller.init_cities(getApplicationContext());
        setContentView(R.layout.activity_main);

        this.findViewById(R.id.btn_switchActivity).setOnClickListener(this);

        this.citiesView = this.findViewById(R.id.citiesView);
        this.cities = this.controller.cities_getList();
        this.cityAdapter = new CityAdapter(getApplicationContext(), this.cities);
        this.citiesView.setAdapter(this.cityAdapter);
        this.cityAdapter.notifyDataSetChanged();
        this.initLocation();
        this.getLast();
    }

    @Override
    protected void onResume() {

        super.onResume();
        this.initLocation();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (this.locationManager != null && this.locationListener != null) {
            this.locationManager.removeUpdates(this.locationListener);
            this.locationManager = null;
            this.locationListener = null;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }



    @Override
    public void onClick(View v) {
        int caller_id = v.getId();

        if (caller_id == R.id.btn_switchActivity)
            this.startForm(v);
            //this.emptyList(v);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == this.REQUEST_PERMISSION_LOCATION_CODE) {
            if (grantResults.length != 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                this.initLocation();
            } else {
                System.out.println("Permission was not granted");
            }
        }
    }

    private void emptyList(View v) {
        List<City> new_list = new ArrayList<City>();
        new_list.add(new City(
                1,
                "Tombouktou",
                "Congo",
                "Caoutal"
        ));
        this.cityAdapter = new CityAdapter(getApplicationContext(), new_list);
        this.citiesView.setAdapter(this.cityAdapter);
        this.cityAdapter.notifyDataSetChanged();
    }

    private void startForm(View v) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (!isFinishing()) {
                    Intent intent = new Intent(MainActivity.this, CityFormActivity.class);
                    startActivity(intent);
                }

            }
        });
        System.out.println("HERE");
        System.out.println("THERE");
    }

    //LOCATION

    private void initLocation() {
        this.locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        this.locationListener = new Locator(this);
    }

    private Location getLast() {
        this.locationListener.check_perms(this);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
            return this.locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        return null;
    }

    public void requestLocationPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
            // afficher message explicant pourquoi la permission est requise
            new AlertDialog.Builder(this)
                    .setTitle("Permission de géolocalisation")
                    .setMessage("L'application a besoin de connaitre votre location pour récupérer les données météo de votre position")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            MainActivity.this.askLocationPersmission();
                        }
                    })
                    .setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    })
                    .create().show();
        } else {
            MainActivity.this.askLocationPersmission();
        }
        //ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, this.REQUEST_PERMISSION_LOCATION_CODE))
    }

    private void askLocationPersmission() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, this.REQUEST_PERMISSION_LOCATION_CODE);
    }

    public void makeToast(String[] to_display) {
        StringBuilder buff = new StringBuilder();

        for (String _b : to_display) {
            buff.append(_b);
        }
        Toast.makeText(getApplicationContext(), buff, Toast.LENGTH_SHORT).show();
    }

    public void locationChanged() {}
}