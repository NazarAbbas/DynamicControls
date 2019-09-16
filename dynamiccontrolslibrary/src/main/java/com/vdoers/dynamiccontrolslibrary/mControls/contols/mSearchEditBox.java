package com.vdoers.dynamiccontrolslibrary.mControls.contols;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.vdoers.dynamiccontrolslibrary.R;

import java.util.ArrayList;
import java.util.List;



public class mSearchEditBox extends LinearLayout implements View.OnClickListener {

    private TextView tvText;
    private TextView tvHeading;
    private Activity activity;
    private List<JsonWorkflowList.OptionList> optionList = new ArrayList<>();
    private String jsonOptionList;
    private JsonWorkflowList.OptionList option;
    private JsonWorkflowList.Field field;

    public mSearchEditBox(Context context) {
        super(context);
    }

    public String getValue() {
        if (option != null) {
            return option.getId().trim().toString();
        } else {
            return "";
        }
    }

    public void setValue(String value) {
        tvText.setText(value);
    }

    public mSearchEditBox(Activity context, JsonWorkflowList.Field field) {
        super(context);
        setOrientation(LinearLayout.VERTICAL);
        this.field = field;
        this.activity = context;
        this.optionList = field.getOptionList();
        Gson gson = new GsonBuilder().create();
        jsonOptionList = gson.toJson(optionList);

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
            Gson gson = new Gson();
            option = gson.fromJson(result, JsonWorkflowList.OptionList.class);
            if (option != null)
                setValue(option.getValue());
            else
                setValue("");


        }
    };


    private void initUI(Activity context, JsonWorkflowList.Field field) {
        LinearLayout topLayout = (LinearLayout) context.getLayoutInflater().inflate(R.layout.dynamic_search_edittext, null);
        tvText = (TextView) topLayout.findViewById(R.id.tv_text);
        tvHeading = (TextView) topLayout.findViewById(R.id.tv_heading);
        tvText.setOnClickListener(this);
        addView(topLayout);


    }


    private void showUI(JsonWorkflowList.Field field) {
        tvText.setText((String) field.getAnswer());
        tvHeading.setText(field.getLabel());
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.tv_text){
            Intent intent = new Intent(activity, mSearchListView.class);
            intent.putExtra(mSearchListView.SEARCH_EDITTEXT_NAME_KEY, field.getName());
            intent.putExtra(mSearchListView.TITLE, field.getLabel());
            intent.putExtra(mSearchListView.OPTIONS_LIST, jsonOptionList);
            activity.startActivity(intent);
            activity.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        }
       /* switch (v.getId()) {
            case R.id.tv_text:
                Intent intent = new Intent(activity, mSearchListView.class);
                intent.putExtra(mSearchListView.SEARCH_EDITTEXT_NAME_KEY, field.getName());
                intent.putExtra(mSearchListView.TITLE, field.getLabel());
                intent.putExtra(mSearchListView.OPTIONS_LIST, jsonOptionList);
                activity.startActivity(intent);
                activity.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                break;
        }*/

    }


}
