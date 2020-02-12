package com.vdoers.dynamiccontrolslibrary.mControls.contols;

import android.app.Activity;
import android.content.Context;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.Spanned;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.vdoers.dynamiccontrolslibrary.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class mEditBox extends LinearLayout {

    private EditText editText;
    private TextInputLayout tilTvHeading;


    public mEditBox(Context context) {
        super(context);
    }

    public String getValue() {
        return editText.getText().toString();
    }


    public mEditBox(Activity context, JsonWorkflowList.Field field) {
        super(context);
        setOrientation(LinearLayout.VERTICAL);
        initUI(context, field);
        setClickListener();
        setFilter(field);
        showUI(field);

    }


    private void initUI(Activity context, JsonWorkflowList.Field field) {
        LinearLayout topLayout = (LinearLayout) context.getLayoutInflater().inflate(R.layout.dynamic_edittext, null);
        editText = (EditText) topLayout.findViewById(R.id.edit_text);
        tilTvHeading = (TextInputLayout) topLayout.findViewById(R.id.til);
        if (field.isEditable()) {
            editText.setEnabled(true);
        } else {
            editText.setEnabled(false);
        }
        addView(topLayout);


    }

    private void setClickListener() {
        editText.addTextChangedListener(new GenericTextWatcher(tilTvHeading));
    }

    private void setFilter(JsonWorkflowList.Field field) {
        InputFilter lengthFilter = null;
        if (field.getMaxLength() > 0) {
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


    InputFilter filter = new InputFilter() {
        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
            for (int i = start; i < end; ++i) {
                if (!Pattern.compile("[ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890]*").matcher(String.valueOf(source.charAt(i))).matches()) {
                    return "";
                }
            }
            return null;
        }
    };


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
            if (til.getId() == R.id.til) {
                if (!editText.getText().toString().trim().equalsIgnoreCase(""))
                    til.setErrorEnabled(false);
            }
          /*  switch (til.getId()) {
                case R.id.til:
                    if (!editText.getText().toString().trim().equalsIgnoreCase(""))
                        til.setErrorEnabled(false);
                    break;
            }*/

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
