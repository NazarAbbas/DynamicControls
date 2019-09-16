package com.vdoers.dynamiccontrolslibrary.mControls.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.vdoers.dynamiccontrolslibrary.R;
import com.vdoers.dynamiccontrolslibrary.mControls.models.DynamicSpinnerModel;

import java.util.List;



public class MultiSelectAlertAdapter extends BaseAdapter {


    private static LayoutInflater inflater = null;
    private List<DynamicSpinnerModel> dataSet;
    private Context mContext;


    public MultiSelectAlertAdapter(Context context, List<DynamicSpinnerModel> data) {
        this.dataSet = data;
        this.mContext = context;
        inflater = (LayoutInflater) context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return dataSet.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View rowView = convertView;
        final ViewHolderAttandance viewHolder;
        if (convertView == null) {
            rowView = inflater.inflate(R.layout.dynamic_multi_select_alert_adapter, null);
            viewHolder = new ViewHolderAttandance();
            // viewHolder.tvValue = (TextView) rowView.findViewById(R.id.tv_value);
            viewHolder.chk = (CheckBox) rowView.findViewById(R.id.chk);
            viewHolder.chk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CheckBox cb = (CheckBox) v;
                    DynamicSpinnerModel DynamicSpinnerModel = (DynamicSpinnerModel) cb.getTag();
                    DynamicSpinnerModel.setSelected(cb.isChecked());

                }
            });
            rowView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolderAttandance) rowView.getTag();
        }

        viewHolder.chk.setChecked(dataSet.get(position).isSelected());
        viewHolder.chk.setText(dataSet.get(position).getValue());
        //viewHolder.tvValue.setText(dataSet.get(position).getValue());
        viewHolder.chk.setTag(dataSet.get(position));


        return rowView;
    }

    public static class ViewHolderAttandance {
        TextView tvValue;
        CheckBox chk;

    }
}