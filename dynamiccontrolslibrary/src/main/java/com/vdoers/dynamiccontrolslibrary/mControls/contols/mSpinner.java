package com.vdoers.dynamiccontrolslibrary.mControls.contols;

import android.app.Activity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.vdoers.Database.StateCityDBHandler;
import com.vdoers.dynamiccontrolslibrary.R;
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
    private EditText editText;
    private TextView spnLablel;
    private List<DynamicSpinnerModel> spinnerModelList = new ArrayList<>();
    private Activity context;
    private static int selectedPos = -1;
    private boolean isSpinnerTouched = false;
    private JsonWorkflowList.Field field;

    public mSpinner(Activity context, JsonWorkflowList.Field field) {
        super(context);
        setOrientation(LinearLayout.VERTICAL);
        this.context = context;
        this.field = field;
        initUI(context);
        showUI(field, context);


    }

    private void initUI(Activity context) {
        LinearLayout topLayout = (LinearLayout) context.getLayoutInflater().inflate(R.layout.dynamic_spinner, null);
        spnLablel = (TextView) topLayout.findViewById(R.id.spn_label);
        spinner = (Spinner) topLayout.findViewById(R.id.spn);
        spinner.setOnItemSelectedListener(this);
        editText = (EditText) topLayout.findViewById(R.id.edit_text);
        addView(topLayout);

        /*spinner.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                isSpinnerTouched = true;
                return false;
            }
        });*/
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
        }
        spinner.setSelection(selectedIndex);

        if (isEditable) {
            editText.setVisibility(View.VISIBLE);
        } else {
            editText.setVisibility(View.GONE);
        }

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
        //DynamicSpinnerModel spinnerModel = (DynamicSpinnerModel) parent.getItemAtPosition(position);
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
}
