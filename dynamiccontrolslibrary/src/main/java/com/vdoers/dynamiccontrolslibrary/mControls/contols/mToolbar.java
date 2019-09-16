package com.vdoers.dynamiccontrolslibrary.mControls.contols;

import android.app.Activity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.vdoers.dynamiccontrolslibrary.R;


public class mToolbar extends LinearLayout {

    private TextView tvTitle;
    private ImageView imgBack;
    private Activity context;


    public mToolbar(Activity context, JsonWorkflowList.Subform subForm) {
        super(context);
        setOrientation(LinearLayout.VERTICAL);
        this.context = context;
        initUI();
        setClickListener();
        showUI(subForm);
    }

    private void setClickListener() {
        imgBack.setOnClickListener((OnClickListener) context);
    }

    private void showUI(JsonWorkflowList.Subform subForm) {
        tvTitle.setText(subForm.getTitle());
        if (subForm.getBack().equalsIgnoreCase("true")) {
            imgBack.setVisibility(View.VISIBLE);
        } else {
            imgBack.setVisibility(View.GONE);
        }
    }


    private void initUI() {
        LinearLayout topLayout = (LinearLayout) context.getLayoutInflater().inflate(R.layout.dynamic_toolbar, null);
        tvTitle = (TextView) topLayout.findViewById(R.id.tv_header_title);
        imgBack = (ImageView) topLayout.findViewById(R.id.img_back);
        addView(topLayout);

    }
}
