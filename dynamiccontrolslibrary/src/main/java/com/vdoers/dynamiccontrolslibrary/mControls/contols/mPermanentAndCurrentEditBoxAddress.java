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
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.vdoers.dynamiccontrolslibrary.R;
import com.vdoers.dynamiccontrolslibrary.Utils.Permissions;


public class mPermanentAndCurrentEditBoxAddress extends LinearLayout {

    private EditText etCurrentAddress;
    private EditText etPermanenrAddress;
    private TextView tvCurrentAddressHeading;
    private TextView tvPermanentAddressHeading;
    private JsonWorkflowList.Field field;
    private Activity context;
    private CheckBox checkBox;

    public mPermanentAndCurrentEditBoxAddress(Context context) {
        super(context);
    }

    public String getValue() {
        return etPermanenrAddress.getText().toString() + "|" + checkBox.isChecked() + "|" + etCurrentAddress.getText().toString();
    }

    public mPermanentAndCurrentEditBoxAddress(Activity context, JsonWorkflowList.Field field) {
        super(context);
        setOrientation(LinearLayout.VERTICAL);
        this.field = field;
        this.context = context;
        initUI(context, field);
        setClickListener();
        setFilter(field);
        showUI(field);

    }

    private void setClickListener() {
        etPermanenrAddress.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (checkBox.isChecked()) {
                    etCurrentAddress.setText(etPermanenrAddress.getText().toString());
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void initUI(Activity context, JsonWorkflowList.Field field) {
        LinearLayout topLayout = (LinearLayout) context.getLayoutInflater().inflate(R.layout.dynamic_permanaent_and_current_address_edittext, null);
        etPermanenrAddress = (EditText) topLayout.findViewById(R.id.tv_permanent_address);
        tvPermanentAddressHeading = (TextView) topLayout.findViewById(R.id.tv_permanent_heading);
        etCurrentAddress = (EditText) topLayout.findViewById(R.id.tv_current_address);
        tvCurrentAddressHeading = (TextView) topLayout.findViewById(R.id.tv_current_address_heading);
        checkBox = (CheckBox) topLayout.findViewById(R.id.check);
        checkBox.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkBox.isChecked()) {
                    etCurrentAddress.setText(etPermanenrAddress.getText().toString());
                    //etCurrentAddress.setEnabled(false);
                    //etCurrentAddress.setKeyListener(null);
                    etCurrentAddress.setFocusable(false);
                } else {
                    etCurrentAddress.setText("");
                    //etCurrentAddress.setEnabled(true);
                    //etCurrentAddress.setKeyListener(etCurrentAddress.getKeyListener());
                    etCurrentAddress.setFocusable(true);
                    etCurrentAddress.setFocusableInTouchMode(true);
                }
            }
        });
        addView(topLayout);


    }

    private void setFilter(JsonWorkflowList.Field field) {
        InputFilter lengthFilter = null;
        if (field.getMaxLength() > 0) {
            etCurrentAddress.setFilters(new InputFilter[]{new InputFilter.LengthFilter(field.getMaxLength())});
            lengthFilter = new InputFilter.LengthFilter(field.getMaxLength());

            etPermanenrAddress.setFilters(new InputFilter[]{new InputFilter.LengthFilter(field.getMaxLength())});
            lengthFilter = new InputFilter.LengthFilter(field.getMaxLength());
        }
        if (field.getType().equalsIgnoreCase(Types.EDITBOX_NUMBER_TYPE)) {
            etCurrentAddress.setInputType(InputType.TYPE_CLASS_NUMBER);
            etPermanenrAddress.setInputType(InputType.TYPE_CLASS_NUMBER);
        }
        if (field.getType().equalsIgnoreCase(Types.EXACT_EDITBOX_NUMBER_TYPE)) {
            etCurrentAddress.setInputType(InputType.TYPE_CLASS_PHONE);
            etPermanenrAddress.setInputType(InputType.TYPE_CLASS_PHONE);
        }
        //field.setAllCaps(true);
        InputFilter capsFilter = null;
        if (field.isAllCaps()) {
            capsFilter = new InputFilter.AllCaps();
            InputFilter[] editFilters = etPermanenrAddress.getFilters();
            InputFilter[] newFilters = new InputFilter[editFilters.length + 1];
            System.arraycopy(editFilters, 0, newFilters, 0, editFilters.length);
            newFilters[editFilters.length] = capsFilter;
            etPermanenrAddress.setFilters(newFilters);
            etCurrentAddress.setFilters(newFilters);

        }

        /*if (lengthFilter != null && capsFilter != null) {
            editText.setFilters(new InputFilter[]{lengthFilter, capsFilter});
        }*/
    }

    private void showUI(JsonWorkflowList.Field field) {
        try {
            String[] separated = null;
            if (field.getAnswer() != null && !((String) field.getAnswer()).isEmpty()) {
                separated = ((String) field.getAnswer()).split("\\|");
                try {
                    if (separated[0] != null)
                        etPermanenrAddress.setText(separated[0]);
                } catch (Exception ex) {

                }
                try {
                    if (separated[1] != null) {
                        if (separated[1].equalsIgnoreCase("True")) {
                            checkBox.setChecked(true);
                        } else {
                            checkBox.setChecked(false);
                        }
                    }

                } catch (Exception ex) {

                }
                try {
                    if (separated[2] != null)
                        etCurrentAddress.setText(separated[2]);
                } catch (Exception ex) {

                }
            }
            if (field.getLabel() != null && !field.getLabel().isEmpty()) {
                separated = field.getLabel().split("\\|");
                try {
                    if (separated[0] != null)
                        tvPermanentAddressHeading.setText(separated[0]);
                } catch (Exception ex) {

                }
                try {
                    if (separated[1] != null)
                        checkBox.setText(separated[1]);
                } catch (Exception ex) {

                }
                try {
                    if (separated[2] != null)
                        tvCurrentAddressHeading.setText(separated[2]);
                } catch (Exception ezx) {

                }
            }
        } catch (Exception ex) {

        }
    }


}
