package com.vdoers.dynamiccontrolslibrary.mControls.contols;

import android.app.Activity;
import android.content.Context;
import android.text.InputFilter;
import android.text.Spanned;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.vdoers.dynamiccontrolslibrary.R;
import com.vdoers.dynamiccontrolslibrary.mControls.DynamicControlResponse;

import java.util.regex.Pattern;


public class mNumberEditBox extends LinearLayout {

    private EditText editText;


    public mNumberEditBox(Context context) {
        super(context);
    }

    public String getValue() {
        return editText.getText().toString();
    }


    public mNumberEditBox(Activity context, DynamicControlResponse.Field field) {
        super(context);
        setOrientation(LinearLayout.VERTICAL);
        initUI(context, field);

    }

    private void initUI(Activity context, DynamicControlResponse.Field field) {
        LinearLayout topLayout = (LinearLayout) context.getLayoutInflater().inflate(R.layout.dynamic_number_edittext, null);
        editText = (EditText) topLayout.findViewById(R.id.edit_text);
        editText.setText(field.getFieldValue());
        TextView tvHeading = (TextView) topLayout.findViewById(R.id.tv_heading);
        tvHeading.setText(field.getLabel());
        if (field.getMaxLength() !=0) {
            editText.setFilters(new InputFilter[]{filter, new InputFilter.LengthFilter(field.getMaxLength())});
        }

        addView(topLayout);


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
}
