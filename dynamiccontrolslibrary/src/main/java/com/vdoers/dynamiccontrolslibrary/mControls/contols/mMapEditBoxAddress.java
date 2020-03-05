package com.vdoers.dynamiccontrolslibrary.mControls.contols;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.vdoers.dynamiccontrolslibrary.R;
import com.vdoers.dynamiccontrolslibrary.Utils.Permissions;


public class mMapEditBoxAddress extends LinearLayout implements View.OnClickListener {

    private TextView tvAddress;
    private TextView tvHeading;
    private JsonWorkflowList.Field field;
    private Activity context;

    public mMapEditBoxAddress(Context context) {
        super(context);
    }

    public String getValue() {
        return tvAddress.getText().toString().trim();
    }

    public void setValue(String value) {
        tvAddress.setText(value);
    }


    public mMapEditBoxAddress(Activity context, JsonWorkflowList.Field field) {
        super(context);
        setOrientation(LinearLayout.VERTICAL);

        this.field = field;
        this.context = context;
        initUI(context, field);
        showUI(field);

        LocalBroadcastManager.getInstance(context).unregisterReceiver(mMessageReceiver);
        LocalBroadcastManager.getInstance(context).registerReceiver(mMessageReceiver,
                new IntentFilter(field.getName()));

    }

    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Get extra data included in the Intent
            String result = intent.getStringExtra("message");
            String x = "";
            setValue(result);
        }
    };


    private void initUI(Activity context, JsonWorkflowList.Field field) {
        LinearLayout topLayout = (LinearLayout) context.getLayoutInflater().inflate(R.layout.dynamic_map_address_edittext, null);
        tvAddress = (TextView) topLayout.findViewById(R.id.tv_address);
        tvHeading = (TextView) topLayout.findViewById(R.id.tv_heading);
        tvAddress.setOnClickListener(this);
        addView(topLayout);


    }


    private void showUI(JsonWorkflowList.Field field) {
        tvAddress.setText((String) field.getAnswer());
        tvHeading.setText(field.getLabel());
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.tv_address) {
            if (((Permissions) context).isGPSEnabled(context)) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (ContextCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        ((Permissions) context).checkLocationPermissions();
                        return;
                    }
                }
                Intent intent = new Intent(context, MapsActivity.class);
                intent.putExtra(MapsActivity.ADDRESS_NAME_KEY, field.getName());
                intent.putExtra(MapsActivity.ADDRESS_LABEL_KEY, field.getLabel());
                context.startActivity(intent);
                context.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            } else {
                ((Permissions) context).turnOnGPS();
            }

        }
      /*  switch (v.getId()) {
            case R.id.tv_address:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (ContextCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        ((Permissions) context).checkLocationPermissions();
                        return;
                    }
                }
                Intent intent = new Intent(context, MapsActivity.class);
                intent.putExtra(MapsActivity.ADDRESS_NAME_KEY, field.getName());
                context.startActivity(intent);
                context.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                break;
        }
*/
    }


}
