package com.vdoers.dynamiccontrolslibrary.mControls.contols;

import android.app.Activity;
import android.app.TimePickerDialog;
import android.content.Context;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TimePicker;

import com.vdoers.dynamiccontrolslibrary.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class mTime extends LinearLayout implements View.OnClickListener {


    private EditText editText;
    private TextInputLayout tilTvHeading;
    private Activity context;


    public mTime(Context context) {
        super(context);
    }

    public String getValue() {
        return editText.getText().toString();
    }


    public mTime(Activity context, JsonWorkflowList.Field field) {
        super(context);
        setOrientation(LinearLayout.VERTICAL);
        this.context = context;
        initUI();
        setClickListener();
        showUI(field);

    }

    private void initUI() {
        LinearLayout topLayout = (LinearLayout) context.getLayoutInflater().inflate(R.layout.dynamic_time, null);
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
            SimpleDateFormat df = new SimpleDateFormat("hh:mm:ss a");
            String formattedTime = df.format(c);
            editText.setText(formattedTime);

        }
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
            if(til.getId()==R.id.til){
                if (!editText.getText().toString().trim().equalsIgnoreCase(""))
                    til.setErrorEnabled(false);
            }
           /* switch (til.getId()) {
                case R.id.til:
                    if (!editText.getText().toString().trim().equalsIgnoreCase(""))
                        til.setErrorEnabled(false);
                    break;
            }*/

        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.edit_text) {
            showTimePicker(context);
        }
        /*switch (v.getId()) {
            case R.id.edit_text:
                showTimePicker(context);
                break;
            default:
                break;
        }*/
    }


    private void showTimePicker(Activity activity) {
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);
        TimePickerDialog timePickerDialog = new TimePickerDialog(activity,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay,
                                          int minute) {
                        updateTime(hourOfDay, minute);
                    }
                }, hour, minute, false);
        timePickerDialog.show();
    }

    // Used to convert 24hr format to 12hr format with AM/PM values
    private void updateTime(int hours, int mins) {

        String timeSet = "";
        if (hours > 12) {
            hours -= 12;
            timeSet = " PM";
        } else if (hours == 0) {
            hours += 12;
            timeSet = " AM";
        } else if (hours == 12)
            timeSet = " PM";
        else
            timeSet = " AM";


        String minutes = "";
        if (mins < 10)
            minutes = "0" + mins;
        else
            minutes = String.valueOf(mins);

        Calendar c = Calendar.getInstance();
        int seconds = c.get(Calendar.SECOND);
        String sec = "";

        if (seconds < 10)
            sec = "0" + seconds;
        else
            sec = String.valueOf(seconds);

        String hour = "";
        if (hours < 10)
            hour = "0" + hours;
        else
            hour = String.valueOf(hours);
        // Append in a StringBuilder
        String aTime = new StringBuilder().append(hour).append(':')
                .append(minutes).append(':').append(sec).append(timeSet).toString();

        editText.setText(aTime);
    }
}

