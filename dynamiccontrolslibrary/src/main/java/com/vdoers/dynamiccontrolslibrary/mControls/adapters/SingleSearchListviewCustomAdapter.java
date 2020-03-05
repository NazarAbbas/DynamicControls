package com.vdoers.dynamiccontrolslibrary.mControls.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.vdoers.dynamiccontrolslibrary.R;
import com.vdoers.dynamiccontrolslibrary.mControls.contols.JsonWorkflowList;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class SingleSearchListviewCustomAdapter extends BaseAdapter {
    private static LayoutInflater inflater = null;
    private Activity activity;
    private List<JsonWorkflowList.OptionList> optionList;
    private List<JsonWorkflowList.OptionList> filterOptionsList = new ArrayList<>();


    public SingleSearchListviewCustomAdapter(Activity context, List<JsonWorkflowList.OptionList> optionList) {
        this.activity = context;
        this.optionList = optionList;
        this.filterOptionsList.addAll(optionList);
        this.inflater = (LayoutInflater) context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return optionList.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return optionList.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View rowView = convertView;
        final ViewHolder viewHolder;
        if (convertView == null) {
            rowView = inflater.inflate(R.layout.dynamic_single_searach_adapter, null);
            viewHolder = new ViewHolder();
            viewHolder.chk = (CheckBox) rowView.findViewById(R.id.chk);
            viewHolder.tvSerailNo = (TextView) rowView.findViewById(R.id.tv_serial_no);
            viewHolder.chk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    for (int i = 0; i < optionList.size(); i++) {
                        optionList.get(i).setIsChecked(false);
                    }
                    CheckBox cb = (CheckBox) v;
                    JsonWorkflowList.OptionList inventory = (JsonWorkflowList.OptionList) cb.getTag();
                    inventory.setIsChecked(cb.isChecked());
                    notifyDataSetChanged();
                }
            });

            rowView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) rowView.getTag();
        }
        viewHolder.chk.setChecked(optionList.get(position).getIsChecked());
        viewHolder.tvSerailNo.setText(optionList.get(position).getValue());
        viewHolder.chk.setTag(optionList.get(position));
        return rowView;
    }

    public void filterData(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        optionList.clear();
        if (charText.length() == 0) {
            optionList.addAll(filterOptionsList);
        } else {
            for (JsonWorkflowList.OptionList wp : filterOptionsList) {
                if (wp.getValue().toLowerCase(Locale.getDefault()).contains(charText)) {
                    optionList.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }

    public static class ViewHolder {
        TextView tvSerailNo;
        CheckBox chk;

    }
}