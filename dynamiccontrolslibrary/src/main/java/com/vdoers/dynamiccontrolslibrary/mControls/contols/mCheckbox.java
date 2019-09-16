package com.vdoers.dynamiccontrolslibrary.mControls.contols;

import android.app.Activity;
import android.widget.CheckBox;
import android.widget.LinearLayout;

import com.vdoers.dynamiccontrolslibrary.R;


public class mCheckbox extends LinearLayout {

    private CheckBox checkBox;
    private Activity context;
    private JsonWorkflowList.Field field;

    public mCheckbox(Activity context, JsonWorkflowList.Field field) {
        super(context);
        setOrientation(LinearLayout.VERTICAL);
        this.context = context;
        this.field = field;
        initUI();
        setClickListener();
        showUI(field);

    }

    private void showUI(JsonWorkflowList.Field field) {
        checkBox.setText(field.getLabel());
        if (field.getAnswer() != null) {
            String answer = ((String) field.getAnswer());
            if (answer.equalsIgnoreCase("true")) {
                checkBox.setChecked(true);
            }

        }

    }

    private void setClickListener() {
        //checkBox.setOnClickListener(this);
    }

    public boolean getValue() {
        return checkBox.isChecked();
    }


    private void initUI() {
        LinearLayout topLayout = (LinearLayout) context.getLayoutInflater().inflate(R.layout.dynamic_checkbox, null);
        checkBox = (CheckBox) topLayout.findViewById(R.id.check);
        addView(topLayout);

    }

  /*  @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.check:
                if (((CheckBox) v).isChecked()) {
                    Toast.makeText(context,
                            "Checked", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(context,
                            "Un Checked", Toast.LENGTH_LONG).show();
                }
                break;
            default:
                break;
        }
    }*/

}
