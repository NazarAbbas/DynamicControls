package com.vdoers.dynamiccontrolslibrary.mControls.contols;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.vdoers.dynamiccontrolslibrary.R;

import java.util.ArrayList;
import java.util.List;



public class mSingleChoice extends LinearLayout {

    private LinearLayout linearLayout;
    private Activity context;
    private RadioGroup radioGroup;
    private List<JsonWorkflowList.OptionList> optionList = new ArrayList<>();
    private int position = -1;
    private TextView tvHeading;
    private RadioButton radioButton;

    public mSingleChoice(Activity context, JsonWorkflowList.Field field) {
        super(context);
        setOrientation(LinearLayout.VERTICAL);
        this.context = context;
        optionList.addAll(field.getOptionList());
        initUI(field);
        setClickListener();
        showUI(field);

    }

    private void showUI(JsonWorkflowList.Field field) {
        //btnNext.setText(field.getLabel());
    }

    public String getValue() {
        if (position != -1) {
            JsonWorkflowList.OptionList option = optionList.get(position);
            for (int i = 0; i < optionList.size(); i++) {
                optionList.get(i).setIsChecked(false);
            }
            option.setIsChecked(true);
            return option.getId();
        } else {
            return "";
        }
    }


    private void setClickListener() {
        //btnNext.setOnClickListener((OnClickListener) context);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int pos) {
                position = pos;
             /*   Intent intent = new Intent("custom-event-name");
                LocalBroadcastManager.getInstance(context).sendBroadcast(intent);*/
            }
        });

        radioGroup.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                RadioGroup radioGroup = (RadioGroup) v;
                radioGroup.getTag();
            }
        });
    }

    private void initUI(JsonWorkflowList.Field field) {
        LinearLayout topLayout = (LinearLayout) context.getLayoutInflater().inflate(R.layout.dynamic_single_choice, null);
        radioGroup = (RadioGroup) topLayout.findViewById(R.id.radio_group);
        tvHeading = (TextView) topLayout.findViewById(R.id.tv_heading);
        tvHeading.setText(field.getLabel());
        LayoutInflater inflater = (LayoutInflater) context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        for (int i = 0; i < optionList.size(); i++) {
            View view = inflater.inflate(R.layout.dynamic_radio, null);
            radioButton = (RadioButton) view.findViewById(R.id.radio_button);
            radioButton.setText(optionList.get(i).getValue());
            radioButton.setId(i);
            RadioGroup.LayoutParams params = new RadioGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.setMargins(0, 25, 0, 25);
            radioButton.setLayoutParams(params);
            if (optionList.get(i).getId().equalsIgnoreCase((String) field.getAnswer())) {
                radioButton.setChecked(true);
                position = i;
            }
            radioGroup.addView(radioButton);
        }


        addView(topLayout);

    }
}
