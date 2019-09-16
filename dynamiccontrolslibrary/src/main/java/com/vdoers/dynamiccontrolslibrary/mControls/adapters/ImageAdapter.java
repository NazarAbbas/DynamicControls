package com.vdoers.dynamiccontrolslibrary.mControls.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.vdoers.dynamiccontrolslibrary.R;
import com.vdoers.dynamiccontrolslibrary.mControls.models.ImageSavedModel;

import java.util.List;



public class ImageAdapter extends BaseAdapter {
    private static LayoutInflater inflater = null;
    private List<ImageSavedModel> dataSet;
    private Context mContext;


    public ImageAdapter(Context context, List<ImageSavedModel> data) {
        this.dataSet = data;
        this.mContext = context;
        inflater = (LayoutInflater) mContext.
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
        ViewHolder viewHolder;
        if (convertView == null) {
            rowView = inflater.inflate(R.layout.gallery_adapter_layout, null);
            viewHolder = new ViewHolder();
            viewHolder.image = (ImageView) rowView.findViewById(R.id.image);
            rowView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) rowView.getTag();
        }


        if (dataSet.size() > 0) {
            viewHolder.image.setImageBitmap(dataSet.get(position).getBitmap());

        }
    /*    rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteImage("Do you want to remove this image?", position);

            }
        });*/
        return rowView;
    }

    public void deleteImage(String message, final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setMessage(message)
                .setCancelable(true).setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dataSet.remove(position);
                notifyDataSetChanged();

            }
        })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.dismiss();
                                dialog.cancel();
                            }
                        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    public static class ViewHolder {
        ImageView image;
    }
}
