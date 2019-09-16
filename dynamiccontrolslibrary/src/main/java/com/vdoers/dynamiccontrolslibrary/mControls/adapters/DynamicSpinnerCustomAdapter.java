package com.vdoers.dynamiccontrolslibrary.mControls.adapters;

import android.app.Activity;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.vdoers.dynamiccontrolslibrary.mControls.models.DynamicSpinnerModel;

import java.util.List;



public class DynamicSpinnerCustomAdapter extends BaseAdapter implements SpinnerAdapter {
    private Activity activity;
    private List<DynamicSpinnerModel> spinnerClassList;


    public DynamicSpinnerCustomAdapter(Activity context, List<DynamicSpinnerModel> spinnerClassList) {
        this.activity = context;
        this.spinnerClassList = spinnerClassList;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return spinnerClassList.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return spinnerClassList.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewgroup) {

        TextView txt = new TextView(activity);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        txt.setLayoutParams(params);
        txt.setGravity(Gravity.LEFT);
        txt.setPadding(0, 16, 16, 16);
        txt.setTextSize(16);
        txt.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
        txt.setText(spinnerClassList.get(i).getValue());
        txt.setTextColor(Color.parseColor("#000000"));
        return txt;
    }


    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        TextView txt = new TextView(activity);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        txt.setLayoutParams(params);
        txt.setPadding(10, 16, 16, 16);
        txt.setTextSize(16);
        txt.setGravity(Gravity.LEFT);
        txt.setText(spinnerClassList.get(position).getValue());
        txt.setTextColor(Color.parseColor("#000000"));
        return txt;
    }


    private class ViewHolder {
        TextView tvValue;
    }
}