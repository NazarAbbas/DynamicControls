package com.vdoers.dynamiccontrolslibrary.mControls.contols;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.vdoers.dynamiccontrolslibrary.R;
import com.vdoers.dynamiccontrolslibrary.Utils.ThemeColor;

import java.util.List;
import java.util.Locale;


public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback, View.OnClickListener,
        LocationListener, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    private GoogleMap googleMap;
    private Button btnDone;
    private EditText etAddress;
    public static final String ADDRESS_NAME_KEY = "address_name_key";
    public static final String ADDRESS_LABEL_KEY = "address_label_key";
    public static final String TITLE = "title";
    private ImageView imgBack;
    private TextView tvHeader;
    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;
    private Location location;
    private TextView tvSelectedAddressHeading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        //getLayoutInflater().inflate(R.layout.activity_maps, frameContainer);
        btnDone = (Button) findViewById(R.id.btn_done);
        btnDone.setBackgroundColor(ThemeColor.themeColor);
        btnDone.setOnClickListener(this);
        showProgressDialog(this, true);
        buildGoogleApiClient();
        etAddress = (EditText) findViewById(R.id.et_address);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        imgBack = (ImageView) findViewById(R.id.img_back);
        imgBack.setOnClickListener(this);
        tvHeader = (TextView) findViewById(R.id.tv_header);
        tvHeader.setText(getIntent().getStringExtra(ADDRESS_LABEL_KEY));
        tvSelectedAddressHeading = (TextView) findViewById(R.id.tv_map_address_heading);
        tvSelectedAddressHeading.setText(getIntent().getStringExtra(ADDRESS_LABEL_KEY));

    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap mMap) {
        this.googleMap = mMap;



   /*     googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            public void onMapClick(LatLng point) {
                etAddress.setText(point.latitude + ", " + point.longitude);
                etAddress.setSelection(etAddress.getText().length());


            }
        });*/
        googleMap.setOnCameraIdleListener(new GoogleMap.OnCameraIdleListener() {
            @Override
            public void onCameraIdle() {
                //get latlng at the center by calling

                LatLng midLatLng = googleMap.getCameraPosition().target;
                //AddressViaLattLong.getCompleteAddressViaLocation()
                String address = getCompleteAddressViaLocation(MapsActivity.this, midLatLng.latitude, midLatLng.longitude);
                etAddress.setText(address + "(" + midLatLng.latitude + ", " + midLatLng.longitude + ")");
                etAddress.setSelection(etAddress.getText().length());

            }
        });
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_done) {
            Intent intent = new Intent(getIntent().getStringExtra(ADDRESS_NAME_KEY));
            intent.putExtra("message", etAddress.getText().toString().trim());
            LocalBroadcastManager.getInstance(MapsActivity.this).sendBroadcast(intent);
            finish();
        } else if (v.getId() == R.id.img_back) {
            onBackPressed();
        }

    }

    public String getCompleteAddressViaLocation(Context activity, double LATITUDE, double LONGITUDE) {
        String strAdd = "Address not found!";
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API).build();
        mGoogleApiClient.connect();
    }

    @Override
    public void onLocationChanged(Location location) {
        //stop location updates
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        if (latLng == null) {
            location = new Location("");
            location.setLatitude(0.0);
            location.setLongitude(0.0);
        }
        LatLng sydney = new LatLng(location.getLatitude(), location.getLongitude());
        googleMap.setMyLocationEnabled(true);
        CameraPosition position = this.googleMap.getCameraPosition();
        CameraPosition.Builder builder = new CameraPosition.Builder();
        builder.zoom(15);
        builder.target(sydney);
        this.googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(builder.build()));
        if (mGoogleApiClient != null) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
            dismissProgressDialog();
        }

    }


    @Override
    public void onConnected(@Nullable Bundle bundle) {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(0);
        mLocationRequest.setFastestInterval(0);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    ProgressDialog progressDialog;

    public void showProgressDialog(Context context, boolean flag) {
        progressDialog = new ProgressDialog(context);
        if (flag) {
            if (!progressDialog.isShowing()) {
                progressDialog.show();
            }
        }
        progressDialog.setContentView(R.layout.custom_progressbar);
        progressDialog.setCancelable(true);
        progressDialog.setIndeterminate(true);

    }

    /**
     * @param :
     * @return :
     * @author : Nazar
     * @created : Nov 20, 2016
     * @method name : dismissProgressDialog().
     * @description : This method is used to show the dismiss Progress Dialog Box.
     */
    public void dismissProgressDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }
}




