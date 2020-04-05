package com.vdoers.dynamiccontrolslibrary.location;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;

import com.google.android.gms.maps.model.LatLng;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.List;
import java.util.Locale;


public class LocationClass {

    public static JSONObject getLocationViaGoogleApi(String address) {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            HttpResponse response = null;
            stringBuilder = new StringBuilder();
            String url = "http://maps.google.com/maps/api/geocode/json?address=" + URLEncoder.encode(address) + "&sensor=false";
            try {
                HttpPost httppost = new HttpPost(url);
                HttpClient client = new DefaultHttpClient();
                response = client.execute(httppost);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (response != null) {
                HttpEntity entity = response.getEntity();
                InputStream stream = entity.getContent();
                int b;
                while ((b = stream.read()) != -1) {
                    stringBuilder.append((char) b);
                }
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject = new JSONObject(stringBuilder.toString());
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return jsonObject;
    }

    public static String getCompleteAddressViaLocation(Context activity, double LATITUDE, double LONGITUDE) {
        String strAdd = "";
        Geocoder geocoder = new Geocoder(activity, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(LATITUDE, LONGITUDE, 1);
            if (addresses != null) {
                Address returnedAddress = addresses.get(0);
                StringBuilder strReturnedAddress = new StringBuilder("");

                for (int i = 0; i <= returnedAddress.getMaxAddressLineIndex(); i++) {
                    strReturnedAddress.append(returnedAddress.getAddressLine(i)).append("\n");
                }
                strAdd = strReturnedAddress.toString();
                //Log.w("My Current loction address", strReturnedAddress.toString());
            } else {
                //Log.w("My Current loction address", "No Address returned!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            //Log.w("My Current loction address", "Canont get Address!");
        }
        return strAdd;
    }


    public static LatLng getLatLong(JSONObject jsonObject) {

        try {

            if (jsonObject == null) {
                return new LatLng(0.0, 0.0);
            }
            JSONArray array = (JSONArray) jsonObject.get("results");

            if (array == null || array.length() == 0) {
                return new LatLng(0.0, 0.0);
            }
            double longitute = array.getJSONObject(0)
                    .getJSONObject("geometry").getJSONObject("location")
                    .getDouble("lng");

            double latitude = array.getJSONObject(0)
                    .getJSONObject("geometry").getJSONObject("location")
                    .getDouble("lat");
            return new LatLng(latitude, longitute);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }


    public static LatLng reverseGeocoding(Context context, String locationName) {
        if (!Geocoder.isPresent()) {
            //Log.w("zebia", "Geocoder implementation not present !");
        }
        Geocoder geoCoder = new Geocoder(context, Locale.getDefault());

        try {
            List<Address> addresses = geoCoder.getFromLocationName(locationName, 1);

            if (addresses != null && addresses.size() > 0) {
                // Log.d("zebia", "reverse Geocoding : locationName " + locationName + "Latitude " + addresses.get(0).getLatitude());
                return new LatLng(addresses.get(0).getLatitude(), addresses.get(0).getLongitude());
            }

        } catch (IOException e) {
            // Log.d("@@@@", "Not possible finding LatLng for Address : " + locationName);
        }
        return null;
    }


    public static Location getLocation(Context activity) {
        Location location = null;
        LocationGPSTracker locationGPSTracker = new LocationGPSTracker(activity);
        FusedLocation fusedLocation = new FusedLocation(activity);
        try {
            location = locationGPSTracker.getLocationFromGPS();
            if (location == null) {
                location = locationGPSTracker.getLocationFromGPS();
            }
            if (location == null) {
                location = fusedLocation.getLocation();
            }

        } catch (Exception ex) {

        }
        if (location == null) {
            location = new Location("");
            location.setLatitude(0.0);
            location.setLongitude(0.0);
        }
        return location;
    }


}
