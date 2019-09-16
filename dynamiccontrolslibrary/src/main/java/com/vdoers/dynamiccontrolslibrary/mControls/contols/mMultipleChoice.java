package com.vdoers.dynamiccontrolslibrary.mControls.contols;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.vdoers.dynamiccontrolslibrary.R;
import com.vdoers.dynamiccontrolslibrary.mControls.adapters.MultiSelectAlertAdapter;
import com.vdoers.dynamiccontrolslibrary.mControls.models.DynamicSpinnerModel;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;


public class mMultipleChoice extends LinearLayout implements View.OnClickListener {

    private TextView tvMultiChoice;
    private Activity context;
    private List<JsonWorkflowList.OptionList> optionList = new ArrayList<>();
    private int position = -1;
    private TextView tvHeading;
    private List<DynamicSpinnerModel> DynamicSpinnerModelList;
    private JsonWorkflowList.Field field;
    private String selectedIds = "";


    public mMultipleChoice(Activity context, JsonWorkflowList.Field field) {
        super(context);
        setOrientation(LinearLayout.VERTICAL);
        this.context = context;
        this.field = field;
        initUI(field);
        setClickListener();
        showUI();

    }

    private void showUI() {
        optionList.clear();
        optionList.addAll(field.getOptionList());
        DynamicSpinnerModelList = new ArrayList<>();
        DynamicSpinnerModelList.clear();
        for (int i = 0; i < optionList.size(); i++) {
            DynamicSpinnerModel DynamicSpinnerModel = new DynamicSpinnerModel();
            DynamicSpinnerModel.setValue(optionList.get(i).getValue());
            DynamicSpinnerModel.setGotoId(optionList.get(i).getGotoId());
            DynamicSpinnerModel.setId(optionList.get(i).getId());
            DynamicSpinnerModelList.add(DynamicSpinnerModel);
        }

        tvHeading.setText(field.getLabel());
        if (field.getAnswer() != null && !((String) field.getAnswer()).equalsIgnoreCase("")) {
            String spinnerSelectedValues = "";
            String spinnerSelectedIds = "";
            String[] fieldValues = ((String) field.getAnswer()).split(Pattern.quote("|"));
            for (int i = 0; i < field.getOptionList().size(); i++) {
                for (int j = 0; j < fieldValues.length; j++) {
                    if (field.getOptionList().get(i).getId().equalsIgnoreCase(fieldValues[j])) {
                        spinnerSelectedIds = spinnerSelectedIds + DynamicSpinnerModelList.get(i).getId() + "|";
                        setValue(spinnerSelectedIds);
                        DynamicSpinnerModelList.get(i).setSelected(true);
                        spinnerSelectedValues = spinnerSelectedValues + DynamicSpinnerModelList.get(i).getValue() + " | ";
                    }
                }
            }
            tvMultiChoice.setText(spinnerSelectedValues.substring(0, spinnerSelectedValues.length() - 3));
        }
        tvMultiChoice.setHint("Please select " + field.getLabel());
        tvMultiChoice.setHintTextColor(Color.BLACK);

    }

    public String getValue() {
        return selectedIds;
    }

    public void setValue(String Ids) {
        this.selectedIds = Ids;
    }


    private void setClickListener() {
        //btnNext.setOnClickListener((OnClickListener) context);
        tvMultiChoice.setOnClickListener(this);
    }

    private void initUI(JsonWorkflowList.Field field) {
        LinearLayout topLayout = (LinearLayout) context.getLayoutInflater().inflate(R.layout.dynamic_multiple_choice, null);
        tvMultiChoice = (TextView) topLayout.findViewById(R.id.tv_multi_choice);
        tvHeading = (TextView) topLayout.findViewById(R.id.tv_heading);
        tvHeading.setText(field.getLabel());
        addView(topLayout);

    }

    public void multiSelectAlert(final Activity activity) {

        final Dialog dialog = new Dialog(activity);
        dialog.setContentView(R.layout.dynamic_multi_select_alert_layout);
        ListView listView = (ListView) dialog.findViewById(R.id.list_multi_select);
        MultiSelectAlertAdapter multiSelectAlertAdapter = new MultiSelectAlertAdapter(activity, DynamicSpinnerModelList);
        listView.setAdapter(multiSelectAlertAdapter);
        TextView tvOk = (TextView) dialog.findViewById(R.id.tv_ok);
        TextView tvCancel = (TextView) dialog.findViewById(R.id.tv_cancel);
        tvOk.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                String spinnerSelectedValues = "";
                String spinnerSelectedIds = "";
                for (int i = 0; i < DynamicSpinnerModelList.size(); i++) {
                    if (DynamicSpinnerModelList.get(i).isSelected()) {
                        spinnerSelectedValues = spinnerSelectedValues + DynamicSpinnerModelList.get(i).getValue() + " | ";
                        spinnerSelectedIds = spinnerSelectedIds + DynamicSpinnerModelList.get(i).getId() + "|";
                    }

                }

                if (!spinnerSelectedValues.equalsIgnoreCase("")) {
                    tvMultiChoice.setText(spinnerSelectedValues.substring(0, spinnerSelectedValues.length() - 3));
                    spinnerSelectedIds = spinnerSelectedIds.substring(0, spinnerSelectedIds.length() - 1);
                } else {
                    tvMultiChoice.setText(spinnerSelectedValues);
                }

                setValue(spinnerSelectedIds);


            }
        });
        tvCancel.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                String spinnerSelectedValues = "";
                String spinnerSelectedIds = "";
                for (int i = 0; i < DynamicSpinnerModelList.size(); i++) {
                    DynamicSpinnerModelList.get(i).setSelected(false);
                }

                tvMultiChoice.setText(spinnerSelectedValues);
                setValue(spinnerSelectedIds);

            }
        });
        dialog.show();
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.tv_multi_choice) {
            multiSelectAlert(context);
        }
       /* switch (v.getId()) {
            case R.id.tv_multi_choice:
                multiSelectAlert(context);
                break;
            default:
                break;
        }*/
    }
}
