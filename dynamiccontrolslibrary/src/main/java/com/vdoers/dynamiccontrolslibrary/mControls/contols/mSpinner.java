package com.vdoers.dynamiccontrolslibrary.mControls.contols;

import android.app.Activity;
import android.graphics.Color;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.vdoers.Database.StateCityDBHandler;
import com.vdoers.dynamiccontrolslibrary.R;
import com.vdoers.dynamiccontrolslibrary.Utils.Permissions;
import com.vdoers.dynamiccontrolslibrary.mControls.adapters.DynamicSpinnerCustomAdapter;
import com.vdoers.dynamiccontrolslibrary.mControls.models.DynamicSpinnerModel;

import java.util.ArrayList;
import java.util.List;


public class mSpinner extends LinearLayout implements AdapterView.OnItemSelectedListener {
    private Spinner spinner = null;
    private List<JsonWorkflowList.OptionList> dropdownList = new ArrayList<>();
    private DynamicSpinnerCustomAdapter spnAdapter;
    private int position;
    private boolean isEditable;
    // private LinearLayout llRemarks;
    private TextView spnLablel;
    private List<DynamicSpinnerModel> spinnerModelList = new ArrayList<>();
    private Activity context;
    private static int selectedPos = -1;
    private boolean isSpinnerTouched = false;
    private JsonWorkflowList.Field field;

    public LinearLayout llSpinnerRemarks;
    public EditText etSpinnerRemarks;
    private TextInputLayout tilSpinnerRemarks;
    public TextView tvSpinnerRemarks;

    public mSpinner(Activity context, JsonWorkflowList.Field field) {
        super(context);
        setOrientation(LinearLayout.VERTICAL);
        this.context = context;
        this.field = field;
        initUI(context);
        setClickListener();
        showUI(field, context);


    }

    private void setClickListener() {
        etSpinnerRemarks.addTextChangedListener(new GenericTextWatcher(tilSpinnerRemarks));
    }

    private void initUI(Activity context) {
        LinearLayout topLayout = (LinearLayout) context.getLayoutInflater().inflate(R.layout.dynamic_spinner, null);
        spnLablel = (TextView) topLayout.findViewById(R.id.spn_label);
        spinner = (Spinner) topLayout.findViewById(R.id.spn);
        spinner.setOnItemSelectedListener(this);
        llSpinnerRemarks = (LinearLayout) topLayout.findViewById(R.id.ll_spinner_remarks);
        etSpinnerRemarks = (EditText) topLayout.findViewById(R.id.et_spinner_remarks);
        tilSpinnerRemarks = (TextInputLayout) topLayout.findViewById(R.id.til_spinner_remarks);
        tvSpinnerRemarks = (TextView) topLayout.findViewById(R.id.tv_spinner_remarks_text);
        addView(topLayout);

        /*spinner.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                isSpinnerTouched = true;
                return false;
            }
        });*/
    }

    public String getRemarksValue() {
        return etSpinnerRemarks.getText().toString();
    }

    private void showUI(JsonWorkflowList.Field field, Activity context) {
        spnLablel.setText(field.getLabel());
        //isEditable = field.getEditable();
        if (field.getOptionList() == null && field.getType().equalsIgnoreCase(Types.STATE)) {
            dropdownList = StateCityDBHandler.getSpinnerState(context);
        } else if (field.getOptionList() == null && field.getType().equalsIgnoreCase(Types.CITY)) {
            dropdownList = StateCityDBHandler.getSpinnerAllCities(context);
        } else {
            dropdownList.addAll(field.getOptionList());
        }

        // List<DynamicSpinnerModel> spinnerModelList = new ArrayList<>();
        if (dropdownList.size() > 0) {
            for (int i = 0; i < dropdownList.size(); i++) {
                DynamicSpinnerModel spinnerModel = new DynamicSpinnerModel();
                spinnerModel.setValue(dropdownList.get(i).getValue());
                //spinnerModel.setGotoId(dropdownList.get(i).getGotoId());
                spinnerModel.setEditFieldName(dropdownList.get(i).getEditFieldName());
                spinnerModel.setEnableEdit(dropdownList.get(i).isEnableEdit());
                spinnerModel.setId(dropdownList.get(i).getId());
                spinnerModelList.add(spinnerModel);
            }
        }
        spnAdapter = new DynamicSpinnerCustomAdapter(context, spinnerModelList);
        spinner.setAdapter(spnAdapter);

        int selectedIndex = 0;
        for (int i = 0; i < spinnerModelList.size(); i++) {
            if (spinnerModelList.get(i).getId().equalsIgnoreCase((String) field.getAnswer())
                    || spinnerModelList.get(i).getValue().equalsIgnoreCase((String) field.getAnswer())) {
                selectedIndex = i;
            }
            if (Permissions.dataObject.containsKey(field.getOptionList().get(i).getEditFieldName())) {
                etSpinnerRemarks.setText((String) Permissions.dataObject.get(field.getOptionList().get(i).getEditFieldName()));
            }
        }
        spinner.setSelection(selectedIndex);
    }

    public String getValue() {
        if (dropdownList != null && dropdownList.size() > 0) {
            return dropdownList.get(position).getId();
        } else {
            return "";
        }
    }

    //public static boolean isTrue = true;

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        DynamicSpinnerModel spinnerModel = (DynamicSpinnerModel) parent.getItemAtPosition(position);
        if (spinnerModel.isEnableEdit()) {
            llSpinnerRemarks.setVisibility(View.VISIBLE);
            tvSpinnerRemarks.setText(spinnerModel.getEditFieldName());
        } else {
            llSpinnerRemarks.setVisibility(View.GONE);
            //etSpinnerRemarks.setText("");
        }
        this.position = position;


        //  this code select cities on state base
      /*  if (field.getType().equalsIgnoreCase(Types.STATE)) {
            int selectedId = Integer.parseInt(dropdownList.get(position).getId());
            ((DynamicControlRendererActivity) context).displayScreen(selectedId);
        }*/

    }


    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void setError(String errorMessage) {
        tilSpinnerRemarks.setError(errorMessage);
        tilSpinnerRemarks.requestFocus();
        etSpinnerRemarks.setBackgroundColor(Color.WHITE);
        etSpinnerRemarks.setBackgroundResource(R.drawable.square_boder_red);
        etSpinnerRemarks.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        if (tilSpinnerRemarks.isErrorEnabled()) {
                            etSpinnerRemarks.setBackgroundResource(R.drawable.square_border);
                            if (!etSpinnerRemarks.getText().toString().trim().isEmpty()) {
                                etSpinnerRemarks.setText(etSpinnerRemarks.getText().toString().trim());
                            } else {
                                etSpinnerRemarks.setText(".");
                                etSpinnerRemarks.setText("");
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
            if (til.getId() == R.id.til_spinner_remarks) {
                if (!etSpinnerRemarks.getText().toString().trim().trim().equalsIgnoreCase(""))
                    til.setErrorEnabled(false);
            }
        }
    }
}
