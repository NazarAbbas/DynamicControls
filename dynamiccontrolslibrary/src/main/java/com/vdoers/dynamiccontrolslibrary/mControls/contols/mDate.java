package com.vdoers.dynamiccontrolslibrary.mControls.contols;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.vdoers.dynamiccontrolslibrary.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class mDate extends LinearLayout implements View.OnClickListener {

    private EditText editText;
    private TextInputLayout tilTvHeading;
    private static int selectedYear;
    private static int selectedMonth;
    private static int selctedDay;
    private Activity context;


    public String getValue() {
        return editText.getText().toString();
    }

    public void setValue(String value) {
        editText.setText(value);
    }


    public mDate(Activity context, JsonWorkflowList.Field field) {
        super(context);
        this.context = context;
        setOrientation(LinearLayout.VERTICAL);
        initUI();
        setClickListener();
        showUI(field);

    }

    private void initUI() {
        LinearLayout topLayout = (LinearLayout) context.getLayoutInflater().inflate(R.layout.dynamic_date, null);
        editText = (EditText) topLayout.findViewById(R.id.edit_text);
        tilTvHeading = (TextInputLayout) topLayout.findViewById(R.id.til);
        addView(topLayout);


    }

    private void setClickListener() {
        editText.addTextChangedListener(new GenericTextWatcher(tilTvHeading));
        editText.setOnClickListener(this);
    }

    private void showUI(JsonWorkflowList.Field field) {

        if ((String) field.getAnswer() != null && !((String) field.getAnswer()).equalsIgnoreCase("")) {
            editText.setText((String) field.getAnswer());
        } else {
            Date c = Calendar.getInstance().getTime();
            System.out.println("Current time => " + c);
            SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
            String formattedDate = df.format(c);
            editText.setText(formattedDate);

        }

        tilTvHeading.setHint(field.getLabel());
    }

    public void setError(String errorMessage) {
        tilTvHeading.setError(errorMessage);
        tilTvHeading.requestFocus();
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.edit_text) {
            DatePickerFragment newFragment = new DatePickerFragment(mDate.this);
            newFragment.show(((AppCompatActivity) context).getSupportFragmentManager(), "datePicker");
        }
     /*   switch (v.getId()) {
            case R.id.edit_text:
                DatePickerFragment newFragment = new DatePickerFragment(mDate.this);
                newFragment.show(((AppCompatActivity) context).getSupportFragmentManager(), "datePicker");
                break;
            default:
                break;
        }*/
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
            if(til.getId()==R.id.til){
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

    public static class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {
        // static String dateType = "";
        mDate dateInstance;

        public DatePickerFragment() {

        }

        @SuppressLint("ValidFragment")
        public DatePickerFragment(mDate date) {
            dateInstance = date;
        }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {

            String date = dateInstance.getValue();
            if (!date.equalsIgnoreCase("")) {
                String[] splitDate = date.split("/");
                selectedMonth = Integer.parseInt(splitDate[0]) - 1;
                selctedDay = Integer.parseInt(splitDate[1]);
                selectedYear = Integer.parseInt(splitDate[2]);
            } else {
                Calendar c = Calendar.getInstance();
                selectedYear = c.get(Calendar.YEAR);
                selectedMonth = c.get(Calendar.MONTH);
                selctedDay = c.get(Calendar.DAY_OF_MONTH);
            }

            DatePickerDialog dialog = new DatePickerDialog(getActivity(), this, selectedYear, selectedMonth, selctedDay);
            //dialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
            return dialog;
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            // edDate.setText(day + "/" + (month + 1) + "/" + year);

            selectedYear = year;
            selectedMonth = month;
            selctedDay = day;

            String d = "";
            String m = "";
            if (day < 10)
                d = "0" + day;
            else
                d = String.valueOf(day);
            month = month + 1;
            if (month < 10)
                m = "0" + month;
            else
                m = String.valueOf(month);

            String date = m + "/" + d + "/" + year;
            // editText.setText((m) + "/" + d + "/" + year);
            dateInstance.setValue(date);

        }
    }
}
