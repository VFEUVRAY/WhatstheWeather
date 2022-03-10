package com.example.myapplication.location;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.myapplication.MainActivity;

import java.util.List;

public class Locator implements LocationListener {

    private MainActivity _parent;
    public final int REQUEST_PERMISSION_LOCATION_CODE = 1;

    public Locator(MainActivity parent) {
        super();
        this._parent = parent;
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
        this._parent.locationChanged();
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        LocationListener.super.onStatusChanged(provider, status, extras);
    }

    @Override
    public void onProviderEnabled(@NonNull String provider) {
        LocationListener.super.onProviderEnabled(provider);
    }

    @Override
    public void onProviderDisabled(@NonNull String provider) {
        LocationListener.super.onProviderDisabled(provider);
    }

    public void check_perms(Context context) {
        if (ContextCompat.checkSelfPermission(this._parent, Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED) {
            this._parent.requestLocationPermission();

        } else {
            this._parent.locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 10, this);
        }
    }

}
