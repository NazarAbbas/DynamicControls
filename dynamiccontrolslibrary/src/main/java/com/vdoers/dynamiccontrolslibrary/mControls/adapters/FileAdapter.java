package com.vdoers.dynamiccontrolslibrary.mControls.adapters;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.vdoers.dynamiccontrolslibrary.R;
import com.vdoers.dynamiccontrolslibrary.mControls.contols.JsonWorkflowList;
import com.vdoers.dynamiccontrolslibrary.mControls.contols.Types;
import com.vdoers.dynamiccontrolslibrary.mControls.models.FileSavedModel;

import java.io.File;
import java.util.List;


public class FileAdapter extends BaseAdapter {
    private static LayoutInflater inflater = null;
    private List<FileSavedModel> dataSet;
    private Context mContext;
    private JsonWorkflowList.Field field;


    public FileAdapter(Context context, List<FileSavedModel> data, JsonWorkflowList.Field field) {
        this.dataSet = data;
        this.mContext = context;
        this.field = field;
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
            rowView = inflater.inflate(R.layout.dynamic_file_adapter_layout, null);
            viewHolder = new ViewHolder();
            viewHolder.image = (ImageView) rowView.findViewById(R.id.image);
            viewHolder.tvFileName = (TextView) rowView.findViewById(R.id.tv_file_name);
            rowView.setTag(viewHolder);
           /* viewHolder.image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });*/
        } else {
            viewHolder = (ViewHolder) rowView.getTag();
        }
        if (dataSet.size() > 0) {
            if (field.getType().equalsIgnoreCase(Types.PDF_FILE)) {
                viewHolder.image.setImageDrawable(mContext.getResources().getDrawable(R.drawable.pdf_img));
            } else if (field.getType().equalsIgnoreCase(Types.WORD_FILE)) {
                viewHolder.image.setImageDrawable(mContext.getResources().getDrawable(R.drawable.word_img));
            } else if (field.getType().equalsIgnoreCase(Types.PPT_FILE)) {
                viewHolder.image.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ppt_img));
            } else if (field.getType().equalsIgnoreCase(Types.EXCEL_FILE)) {
                viewHolder.image.setImageDrawable(mContext.getResources().getDrawable(R.drawable.excel_img));
            } else if (field.getType().equalsIgnoreCase(Types.VIDEO_FILE)) {
                viewHolder.image.setImageDrawable(mContext.getResources().getDrawable(R.drawable.video_img));
            } else if (field.getType().equalsIgnoreCase(Types.GIF_FILE)) {
                viewHolder.image.setImageDrawable(mContext.getResources().getDrawable(R.drawable.gif_img));
            } else if (field.getType().equalsIgnoreCase(Types.AUDIO_FILE)) {
                viewHolder.image.setImageDrawable(mContext.getResources().getDrawable(R.drawable.audio_img));
            } else if (field.getType().equalsIgnoreCase(Types.ZIP_FILE)) {
                viewHolder.image.setImageDrawable(mContext.getResources().getDrawable(R.drawable.zip_img));
            } else if (field.getType().equalsIgnoreCase(Types.TEXT_FILE)) {
                viewHolder.image.setImageDrawable(mContext.getResources().getDrawable(R.drawable.text_img));
            } else if (field.getType().equalsIgnoreCase(Types.GALLERY) || field.getType().equalsIgnoreCase(Types.CAMERA)
                    || field.getType().equalsIgnoreCase(Types.CROP_CAMERA)
                    || field.getType().equalsIgnoreCase(Types.CAMERA_WITH_ADDRESS)
                    || field.getType().equalsIgnoreCase(Types.CROP_CAMERA_WITH_ADDRESS)
                    || field.getType().equalsIgnoreCase(Types.SIGNATURE)) {
                File file = new File(dataSet.get(position).getFilePath());
                Uri uri = Uri.fromFile(file);
                viewHolder.image.setImageURI(uri);
            }

            viewHolder.tvFileName.setText(dataSet.get(position).getFileName());

        }
        return rowView;
    }


    public static class ViewHolder {
        ImageView image;
        TextView tvFileName;
    }
}
