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
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.chrisbanes.photoview.PhotoView;
import com.vdoers.dynamiccontrolslibrary.R;
import com.vdoers.dynamiccontrolslibrary.Utils.NetworkUtil;
import com.vdoers.dynamiccontrolslibrary.location.LocationClass;
import com.vdoers.dynamiccontrolslibrary.mControls.Constant;
import com.vdoers.dynamiccontrolslibrary.mControls.contols.Types;
import com.vdoers.dynamiccontrolslibrary.mControls.models.FileSavedModel;

import java.util.List;

public class PreviewAdapter extends PagerAdapter {
    Activity context;
    private List<FileSavedModel> fileSavedModels;
    LayoutInflater layoutInflater;


    public PreviewAdapter(Activity context, List<FileSavedModel> fileSavedModels) {
        this.context = context;
        this.fileSavedModels = fileSavedModels;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return fileSavedModels.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((RelativeLayout) object);
    }

    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    @Override
    public Object instantiateItem(final ViewGroup container, final int position) {
        View rowView = layoutInflater.inflate(R.layout.dynamic_zoom_layout_adapter, container, false);
        final PhotoView photoView = (PhotoView) rowView.findViewById(R.id.photo_view);


        TextView tvFileName = (TextView) rowView.findViewById(R.id.tv_file_name);
        tvFileName.setVisibility(View.GONE);
        photoView.setImageURI(Uri.parse(fileSavedModels.get(position).getFilePath()));
        tvFileName.setText(fileSavedModels.get(position).getFileName());
        TextView tvAddress = (TextView) rowView.findViewById(R.id.tv_address);
        LinearLayout llBottom = (LinearLayout) rowView.findViewById(R.id.ll_bottom);
        if (fileSavedModels.get(position).getType().equalsIgnoreCase(Types.CROP_CAMERA_WITH_ADDRESS)
                || fileSavedModels.get(position).getType().equalsIgnoreCase(Types.CAMERA_WITH_ADDRESS)) {
            llBottom.setVisibility(View.VISIBLE);
            String address = "";
            if (NetworkUtil.isNetAvailable(context)) {
                address = LocationClass.getCompleteAddressViaLocation(context, fileSavedModels.get(position).getLattitude(), fileSavedModels.get(position).getLongitude());
                tvAddress.setText(address + " (" + fileSavedModels.get(position).getLattitude() + ", " + fileSavedModels.get(position).getLongitude() + ")");
            } else {
                tvAddress.setText("Unable to find address (Please check your internet connection and try again...!)");
            }

        } else {
            llBottom.setVisibility(View.GONE);
        }

        ViewTreeObserver vto = photoView.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                photoView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                photoView.setScale(1.1f);
            }
        });

        container.addView(rowView, 0);
        return rowView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);
    }

    public void deleteImage(Activity activity, String message, final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setMessage(message)
                .setCancelable(true).setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                fileSavedModels.remove(position);
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


}
