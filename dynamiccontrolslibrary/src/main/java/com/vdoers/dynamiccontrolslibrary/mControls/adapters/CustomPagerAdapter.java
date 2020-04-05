package com.vdoers.dynamiccontrolslibrary.mControls.adapters;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.vdoers.dynamiccontrolslibrary.R;
import com.vdoers.dynamiccontrolslibrary.mControls.contols.JsonWorkflowList;
import com.vdoers.dynamiccontrolslibrary.mControls.contols.Types;
import com.vdoers.dynamiccontrolslibrary.mControls.contols.mFileViewPager;
import com.vdoers.dynamiccontrolslibrary.mControls.models.FileSavedModel;

import java.io.File;
import java.util.List;

public class CustomPagerAdapter extends PagerAdapter {
    Activity context;
    private List<FileSavedModel> imageSavedModels;
    LayoutInflater layoutInflater;
    private JsonWorkflowList.Field field;
    private mFileViewPager mFileViewPager;


    public CustomPagerAdapter(Activity context, List<FileSavedModel> imageSavedModels, JsonWorkflowList.Field field, mFileViewPager mFileViewPager) {
        this.context = context;
        this.mFileViewPager = mFileViewPager;
        this.imageSavedModels = imageSavedModels;
        this.field = field;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return imageSavedModels.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((LinearLayout) object);
    }

    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    @Override
    public Object instantiateItem(final ViewGroup container, final int position) {
        View rowView = layoutInflater.inflate(R.layout.dynamic_pager_adapter, container, false);
        ImageView image = (ImageView) rowView.findViewById(R.id.image);
        //image.setImageURI(Uri.parse(imageSavedModels.get(position).getFilePath()));
        ImageView imageDelete = (ImageView) rowView.findViewById(R.id.image_delete);
        TextView tvFileName = (TextView) rowView.findViewById(R.id.tv_file_name);

        if (imageSavedModels.size() > 0) {
            if (field.getType().equalsIgnoreCase(Types.PDF_FILE)) {
                image.setImageDrawable(context.getResources().getDrawable(R.drawable.pdf_img));
            } else if (field.getType().equalsIgnoreCase(Types.WORD_FILE)) {
                image.setImageDrawable(context.getResources().getDrawable(R.drawable.word_img));
            } else if (field.getType().equalsIgnoreCase(Types.PPT_FILE)) {
                image.setImageDrawable(context.getResources().getDrawable(R.drawable.ppt_img));
            } else if (field.getType().equalsIgnoreCase(Types.EXCEL_FILE)) {
                image.setImageDrawable(context.getResources().getDrawable(R.drawable.excel_img));
            } else if (field.getType().equalsIgnoreCase(Types.VIDEO_FILE)) {
                image.setImageDrawable(context.getResources().getDrawable(R.drawable.video_img));
            } else if (field.getType().equalsIgnoreCase(Types.GIF_FILE)) {
                image.setImageDrawable(context.getResources().getDrawable(R.drawable.gif_img));
            } else if (field.getType().equalsIgnoreCase(Types.AUDIO_FILE)) {
                image.setImageDrawable(context.getResources().getDrawable(R.drawable.audio_img));
            } else if (field.getType().equalsIgnoreCase(Types.ZIP_FILE)) {
                image.setImageDrawable(context.getResources().getDrawable(R.drawable.zip_img));
            } else if (field.getType().equalsIgnoreCase(Types.TEXT_FILE)) {
                image.setImageDrawable(context.getResources().getDrawable(R.drawable.text_img));
            } else if (field.getType().equalsIgnoreCase(Types.GALLERY) || field.getType().equalsIgnoreCase(Types.CAMERA)
                    || field.getType().equalsIgnoreCase(Types.CROP_CAMERA)
                    || field.getType().equalsIgnoreCase(Types.CAMERA_WITH_ADDRESS)
                    || field.getType().equalsIgnoreCase(Types.CROP_CAMERA_WITH_ADDRESS)
                    || field.getType().equalsIgnoreCase(Types.SIGNATURE)) {
                File file = new File(imageSavedModels.get(position).getFilePath());
                Uri uri = Uri.fromFile(file);
                image.setImageURI(uri);
            }
            if (field.getType().equalsIgnoreCase(Types.CAMERA)
                    || field.getType().equalsIgnoreCase(Types.CROP_CAMERA)
                    || field.getType().equalsIgnoreCase(Types.CAMERA_WITH_ADDRESS)
                    || field.getType().equalsIgnoreCase(Types.CROP_CAMERA_WITH_ADDRESS)
                    || field.getType().equalsIgnoreCase(Types.SIGNATURE)) {
                tvFileName.setVisibility(View.GONE);
            } else {
                tvFileName.setVisibility(View.VISIBLE);
                tvFileName.setText(imageSavedModels.get(position).getFileName());
            }

        }
        imageDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteImage(context, "Do you want to remove this image?", position);
            }
        });
        container.addView(rowView, 0);
        return rowView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);
    }

    public void deleteImage(final Activity activity, String message, final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setMessage(message)
                .setCancelable(true).setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                imageSavedModels.remove(position);
                mFileViewPager.bindFileAdapter(imageSavedModels);
                //notifyDataSetChanged();

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

}
