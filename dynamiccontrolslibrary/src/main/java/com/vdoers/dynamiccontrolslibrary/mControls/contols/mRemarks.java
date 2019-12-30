package com.vdoers.dynamiccontrolslibrary.mControls.contols;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.Spanned;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.vdoers.dynamiccontrolslibrary.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class mRemarks extends LinearLayout {
    private EditText etRemarks;
    private TextInputLayout tilRemarks;
    private TextView tvRemarks;

    public mRemarks(Context context) {
        super(context);
    }

    public String getValue() {
        return etRemarks.getText().toString();
    }


    public mRemarks(Activity context, JsonWorkflowList.Field field) {
        super(context);
        setOrientation(LinearLayout.VERTICAL);
        initUI(context, field);
        setClickListener();
        setFilter(field);
        showUI(field);

    }


    private void initUI(Activity context, JsonWorkflowList.Field field) {
        LinearLayout topLayout = (LinearLayout) context.getLayoutInflater().inflate(R.layout.dynamic_remarks, null);
        etRemarks = (EditText) topLayout.findViewById(R.id.et_remarks);
        tilRemarks = (TextInputLayout) topLayout.findViewById(R.id.til_remarks);
        tvRemarks = (TextView) topLayout.findViewById(R.id.tv_remarks_text);
        addView(topLayout);


    }

    private void setClickListener() {
        etRemarks.addTextChangedListener(new GenericTextWatcher(tilRemarks));
    }

    private void setFilter(JsonWorkflowList.Field field) {
        InputFilter lengthFilter = null;
        if (field.getMaxLength() > 0) {
            etRemarks.setFilters(new InputFilter[]{new InputFilter.LengthFilter(field.getMaxLength())});
            lengthFilter = new InputFilter.LengthFilter(field.getMaxLength());
        }
        if (field.getType().equalsIgnoreCase(Types.EDITBOX_NUMBER_TYPE)) {
            etRemarks.setInputType(InputType.TYPE_CLASS_NUMBER);
        }
        if (field.getType().equalsIgnoreCase(Types.EXACT_EDITBOX_NUMBER_TYPE)) {
            etRemarks.setInputType(InputType.TYPE_CLASS_PHONE);
        }
        //field.setAllCaps(true);
        InputFilter capsFilter = null;
        if (field.isAllCaps()) {
            capsFilter = new InputFilter.AllCaps();
            InputFilter[] editFilters = etRemarks.getFilters();
            InputFilter[] newFilters = new InputFilter[editFilters.length + 1];
            System.arraycopy(editFilters, 0, newFilters, 0, editFilters.length);
            newFilters[editFilters.length] = capsFilter;
            etRemarks.setFilters(newFilters);


        }

        if (lengthFilter != null && capsFilter != null) {
            etRemarks.setFilters(new InputFilter[]{lengthFilter, capsFilter});
        }
    }

    private void showUI(JsonWorkflowList.Field field) {
        etRemarks.setText((String) (field.getAnswer()));
        if (field.getMinLength() > 0 && field.getMaxLength() > 0) {
            tvRemarks.setText(field.getLabel() + (" (MIN " + field.getMinLength() + ", " + "MAX " + field.getMaxLength() + " Character)"));
        } else if (field.getMaxLength() > 0) {
            tvRemarks.setText(field.getLabel() + (" (MAX " + field.getMaxLength() + " Character)"));
        } else if (field.getMinLength() > 0) {
            tvRemarks.setText(field.getLabel() + (" (MIN " + field.getMinLength() + " Character)"));
        } else {
            tvRemarks.setText(field.getLabel());
        }

    }

    public void setError(String errorMessage) {
        tilRemarks.setError(errorMessage);
        tilRemarks.requestFocus();
        etRemarks.setBackgroundColor(Color.WHITE);
        etRemarks.setBackgroundResource(R.drawable.square_boder_red);
        etRemarks.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        if (tilRemarks.isErrorEnabled()) {
                            etRemarks.setBackgroundResource(R.drawable.square_border);
                            if (!etRemarks.getText().toString().trim().isEmpty()) {
                                etRemarks.setText(etRemarks.getText().toString().trim());
                            } else {
                                etRemarks.setText(".");
                                etRemarks.setText("");
                            }
                        }

                        break;
                    case MotionEvent.ACTION_UP:
                        break;
                    case MotionEvent.ACTION_MOVE:
                        break;
                }
                return false;

            }
        });
    }


   /* InputFilter filter = new InputFilter() {
        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
            for (int i = start; i < end; ++i) {
                if (!Pattern.compile("[ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890]*").matcher(String.valueOf(source.charAt(i))).matches()) {
                    return "";
                }
            }
            return null;
        }
    };*/


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
            if (til.getId() == R.id.til_remarks) {
                if (!etRemarks.getText().toString().trim().trim().equalsIgnoreCase(""))
                    til.setErrorEnabled(false);
            }
        }
    }

    /*  public static boolean isEmailValid(String email) {
          boolean isValid = false;
          String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
          CharSequence inputStr = email;
          Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
          Matcher matcher = pattern.matcher(inputStr);
          if (matcher.matches()) {
              isValid = true;
          }
          return isValid;
      }*/
    public static boolean isValid(String email, String regix, String controlType) {
        boolean isValid = false;
        if (regix == null) {
            regix = "";
        }
        if (controlType.equalsIgnoreCase(Types.EDITBOX_EMAIL)) {
            regix = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        }
        CharSequence inputStr = email;
        Pattern pattern = Pattern.compile(regix, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);
        if (matcher.matches()) {
            isValid = true;
        }
        return isValid;
    }

}
