package com.vdoers.dynamiccontrolslibrary.mControls.contols;

import android.app.Activity;
import android.widget.Button;
import android.widget.LinearLayout;
import com.vdoers.dynamiccontrolslibrary.R;


public class mButton extends LinearLayout {

    private Button btnNext;
    private Activity context;

    public mButton(Activity context, JsonWorkflowList.Field field) {
        super(context);
        setOrientation(LinearLayout.VERTICAL);
        this.context = context;
        initUI();
        setClickListener();
        showUI(field);

    }

    private void showUI(JsonWorkflowList.Field field) {
        btnNext.setText(field.getLabel());
    }

    private void setClickListener() {
        btnNext.setOnClickListener((OnClickListener) context);
    }

    private void initUI() {
        LinearLayout topLayout = (LinearLayout) context.getLayoutInflater().inflate(R.layout.dynamic_button, null);
        btnNext = (Button) topLayout.findViewById(R.id.btn_next);
        addView(topLayout);

    }
}
