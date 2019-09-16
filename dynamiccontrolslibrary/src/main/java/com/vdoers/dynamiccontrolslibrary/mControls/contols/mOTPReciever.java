package com.vdoers.dynamiccontrolslibrary.mControls.contols;

import android.app.Activity;
import android.content.Context;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.vdoers.dynamiccontrolslibrary.R;


public class mOTPReciever extends LinearLayout {

    private EditText editText;
    private TextInputLayout tilTvHeading;
    private Button btn;
    private Activity context;
    public static String otpText;
    private TextView tvResendOTP;


    public mOTPReciever(Context context) {
        super(context);
    }

    public String getValue() {
        return editText.getText().toString();
    }


    public mOTPReciever(Activity context, JsonWorkflowList.Field field) {
        super(context);
        setOrientation(LinearLayout.VERTICAL);
        this.context = context;
        initUI(context, field);
        setClickListener();
        setFilter(field);
        showUI(field);

    }


    private void initUI(Activity context, JsonWorkflowList.Field field) {
        LinearLayout topLayout = (LinearLayout) context.getLayoutInflater().inflate(R.layout.dynamic_otp_reciever, null);
        editText = (EditText) topLayout.findViewById(R.id.edit_text);
        tilTvHeading = (TextInputLayout) topLayout.findViewById(R.id.til);
        btn = (Button) topLayout.findViewById(R.id.btn_next);
        tvResendOTP = (TextView) topLayout.findViewById(R.id.tv_resend_otp);
        addView(topLayout);
        tvResendOTP.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //CALL OTP API
            }
        });


    }

    private void setClickListener() {
        editText.addTextChangedListener(new GenericTextWatcher(tilTvHeading));
        btn.setOnClickListener((OnClickListener) context);

    }

    private void setFilter(JsonWorkflowList.Field field) {
        InputFilter lengthFilter = null;
        if (field.getMaxLength() >0) {
            editText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(field.getMaxLength())});
            lengthFilter = new InputFilter.LengthFilter(field.getMaxLength());
        }
        if (field.getType().equalsIgnoreCase(Types.EDITBOX_NUMBER_TYPE)) {
            editText.setInputType(InputType.TYPE_CLASS_NUMBER);
        }
        if (field.getType().equalsIgnoreCase(Types.EXACT_EDITBOX_NUMBER_TYPE)) {
            editText.setInputType(InputType.TYPE_CLASS_PHONE);
        }
        //field.setAllCaps(true);
        InputFilter capsFilter = null;
        if (field.isAllCaps()) {
            capsFilter = new InputFilter.AllCaps();
            InputFilter[] editFilters = editText.getFilters();
            InputFilter[] newFilters = new InputFilter[editFilters.length + 1];
            System.arraycopy(editFilters, 0, newFilters, 0, editFilters.length);
            newFilters[editFilters.length] = capsFilter;
            editText.setFilters(newFilters);


        }

        /*if (lengthFilter != null && capsFilter != null) {
            editText.setFilters(new InputFilter[]{lengthFilter, capsFilter});
        }*/
    }

    private void showUI(JsonWorkflowList.Field field) {
        editText.setText((String) (field.getAnswer()));
        tilTvHeading.setHint(field.getLabel());
    }

    public void setError(String errorMessage) {
        tilTvHeading.setError(errorMessage);
        tilTvHeading.requestFocus();
    }

    private class GenericTextWatcher implements TextWatcher {
        private TextInputLayout til;

        public GenericTextWatcher(TextInputLayout til) {
            this.til = til;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            String text = editable.toString();
            otpText = text;
            if(til.getId()==R.id.til){
                if (!editText.getText().toString().trim().equalsIgnoreCase(""))
                    til.setErrorEnabled(false);
            }
         /*   switch (til.getId()) {
                case R.id.til:
                    if (!editText.getText().toString().trim().equalsIgnoreCase(""))
                        til.setErrorEnabled(false);
                    break;
            }*/

        }
    }


}
