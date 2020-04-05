package com.vdoers.dynamiccontrolslibrary.location;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;

import com.vdoers.dynamiccontrolslibrary.mControls.Constant;

public class LocationGPSTracker extends Service implements LocationListener {
    private final Context mContext;
    public static Location location;
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 1; // 5 meters
    private static final long MIN_TIME_BW_UPDATES = 1; // 5 sec
    protected LocationManager locationManager;
    private Criteria criteria;
    public LocationGPSTracker(Context context) {
        this.mContext = context;
        locationManager = (LocationManager) mContext
                .getSystemService(LOCATION_SERVICE);

        startUsingGPS();
        getLocationFromGPS();
    }

    @SuppressLint("MissingPermission")
    public Location getLocationFromGPS() {
        try {
            setCriteriaforLocationUpdate();
            //this.canGetLocation = true;
            if (isGPSEnabled(mContext)) {
                locationManager.requestLocationUpdates(MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES, criteria, this, null);
                return location;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }

   /* public void stopUsingGPS() {
        if (locationManager != null) {
            locationManager.removeUpdates(LocationGPSTracker.this);
        }
    }*/

    private void setCriteriaforLocationUpdate() {
        criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        criteria.setPowerRequirement(Criteria.POWER_HIGH);
        criteria.setAltitudeRequired(false);
        criteria.setSpeedRequired(false);
        criteria.setCostAllowed(true);
        criteria.setBearingRequired(false);
        //API level 9 and up
        criteria.setHorizontalAccuracy(Criteria.ACCURACY_HIGH);
        criteria.setVerticalAccuracy(Criteria.ACCURACY_HIGH);
    }

    public void startUsingGPS() {
        if (locationManager != null) {
            if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }

            if (criteria == null) {
                setCriteriaforLocationUpdate();
            }
         //   try {
                //locationManager = getSystemService(Context.LOCATION_SERVICE);
                locationManager.requestLocationUpdates(MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES, criteria, this, null);
          //  }
         /*   catch (Exception ex){
                ex.printStackTrace();
            }*/
/*            locationManager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER,
                    MIN_TIME_BW_UPDATES,
                    MIN_DISTANCE_CHANGE_FOR_UPDATES, this);*/
        }
    }


    /**
     * Function to get latitude
     */
    /*public double getLatitude() {
        if (location != null) {
            latitude = location.getLatitude();
        }

        // return latitude
        return latitude;
    }*/

    /**
     * Function to get longitude
     */
   /* public double getLongitude() {
        if (location != null) {
            longitude = location.getLongitude();
        }

        // return longitude
        return longitude;
    }*/

    /**
     * Function to check GPS/wifi enabled
     *
     * @return boolean
     */
  /*  public boolean canGetLocation() {
        return this.canGetLocation;
    }*/

    /**
     * Function to show settings alert dialog
     * On pressing Settings button will lauch Settings Options
     */


    public boolean isGPSEnabled(Context activity) {
       /* return isGPSEnabled = locationManager
                .isProviderEnabled(LocationManager.GPS_PROVIDER);*/
        int locationMode = 0;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            try {
                locationMode = Settings.Secure.getInt(activity.getContentResolver(), Settings.Secure.LOCATION_MODE);

            } catch (Settings.SettingNotFoundException e) {
                e.printStackTrace();
            }
            return (locationMode != Settings.Secure.LOCATION_MODE_OFF && locationMode == Settings.Secure.LOCATION_MODE_HIGH_ACCURACY); //check location mode
        } else {
            String locationProviders = Settings.Secure.getString(activity.getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
            return !TextUtils.isEmpty(locationProviders);
        }

    }

    @Override
    public void onLocationChanged(Location loc) {
        location=loc;
    }

    @Override
    public void onProviderDisabled(String provider) {
    }

    @Override
    public void onProviderEnabled(String provider) {
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
    }

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }
}

