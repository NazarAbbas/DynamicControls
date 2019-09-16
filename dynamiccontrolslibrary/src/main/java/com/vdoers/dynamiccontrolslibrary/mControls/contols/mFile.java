package com.vdoers.dynamiccontrolslibrary.mControls.contols;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Gallery;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.vdoers.dynamiccontrolslibrary.R;
import com.vdoers.dynamiccontrolslibrary.Utils.FileUtilsPath;
import com.vdoers.dynamiccontrolslibrary.Utils.Permissions;

import com.vdoers.dynamiccontrolslibrary.Utils.ThemeColor;
import com.vdoers.dynamiccontrolslibrary.mControls.SignatureActivity;
import com.vdoers.dynamiccontrolslibrary.mControls.adapters.FileAdapter;
import com.vdoers.dynamiccontrolslibrary.mControls.models.FileSavedModel;

import java.util.List;

public class mFile extends LinearLayout implements View.OnClickListener {

    private Gallery gallery;
    private mCircleButton button;
    private Activity context;
    private TextView tvHeading;
    private boolean isImageClicked;
    private JsonWorkflowList.Field field;
    //private TextView tvFileName;
    private FileAdapter fileAdapter;
    List<FileSavedModel> fileSavedModelList = null;
    public static Uri fileUri;
    public static String controlName;

    public mFile(Context context) {
        super(context);
    }

    public mFile(Activity context, JsonWorkflowList.Field field) {
        super(context);
        setOrientation(LinearLayout.VERTICAL);
        this.context = context;
        this.field = field;
        initUI();
        setClickListener();
        showUI(field);
    }

    private void initUI() {
        LinearLayout topLayout = (LinearLayout) context.getLayoutInflater().inflate(R.layout.dynamic_file_layout, null);
        gallery = (Gallery) topLayout.findViewById(R.id.gallery);
        alignGalleryToLeft(gallery);
        button = (mCircleButton) topLayout.findViewById(R.id.btn);
        button.setColor(ThemeColor.themeColor);
        if (field.getType().equalsIgnoreCase(Types.CAMERA)
                || field.getType().equalsIgnoreCase(Types.CROP_CAMERA)) {
            button.setImageDrawable(context.getResources().getDrawable(R.drawable.camera_white));
        }
        if (field.getType().equalsIgnoreCase(Types.SIGNATURE)) {
            button.setImageDrawable(context.getResources().getDrawable(R.drawable.signature_button));
        }

        tvHeading = (TextView) topLayout.findViewById(R.id.tv_heading);
        addView(topLayout);
        gallery.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                deleteImage("Do you want to remove this file?", position);
            }
        });

    }

    public void deleteImage(String message, final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(message)
                .setCancelable(true).setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                fileSavedModelList.remove(position);
                Permissions.dataObject.setProperty(field.getName(), fileSavedModelList);
                if (fileAdapter != null)
                    fileAdapter.notifyDataSetChanged();


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

    private void setClickListener() {
        button.setOnClickListener(this);

    }


    public void bindFileAdapter(List<FileSavedModel> fileSavedModelList) {
        this.fileSavedModelList = fileSavedModelList;
        if (fileAdapter == null) {
            fileAdapter = new FileAdapter(this.getContext(), fileSavedModelList, this.field);
            gallery.setAdapter(fileAdapter);
        } else {
            fileAdapter.notifyDataSetChanged();
        }
    }


    private void showUI(JsonWorkflowList.Field field) {
        String heading = field.getLabel();

        if (field.getMinLength() > 0) {
            heading = heading + " (Min " + field.getMinLength();
        }
        if (field.getMaxLength() > 0) {
            heading = heading + ", Max " + field.getMaxLength() + ")";
        }
        tvHeading.setText(heading);
        //tvHeading.setText(field.getLabel());
        if (field.getFileSavedModelList() != null && field.getFileSavedModelList().size() > 0) {
            this.fileSavedModelList = field.getFileSavedModelList();
            if (fileAdapter == null) {
                fileAdapter = new FileAdapter(this.getContext(), fileSavedModelList, this.field);
                gallery.setAdapter(fileAdapter);
            } else {
                fileAdapter.notifyDataSetChanged();
            }
        } else {

        }

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ((Permissions) context).checkStoragePermission();
                    return;
                }
            }
            if (field.getRequired().equalsIgnoreCase("Y")) {
                if (field.getFileSavedModelList() == null || field.getFileSavedModelList().size() < field.getMaxLength()) {
                    openFileChooser();
                } else {
                    Toast.makeText(context, "You can select max " + field.getMaxLength() + " file", Toast.LENGTH_LONG).show();
                }
            } else {
                if (field.getMaxLength() != 0) {
                    if (field.getFileSavedModelList() == null || field.getFileSavedModelList().size() < field.getMaxLength()) {
                        openFileChooser();
                    } else {
                        Toast.makeText(context, "You can select max " + field.getMaxLength() + " file", Toast.LENGTH_LONG).show();
                    }
                } else {
                    openFileChooser();
                }
            }
            /*if (fileSavedModelList == null || fileSavedModelList.size() < field.getMaxLength()
            || field.getRequired().equalsIgnoreCase("N")) {

            } else {
                Toast.makeText(context, "You can select max " + field.getMaxLength() + " file", Toast.LENGTH_LONG).show();
            }*/


        }


    }

    private void openFileChooser() {
        controlName = field.getName();
        Intent intent = new Intent();
        if (field.getType().equalsIgnoreCase(Types.PDF_FILE)) {
            intent.setType("application/pdf");
        } else if (field.getType().equalsIgnoreCase(Types.WORD_FILE)) {
            intent.setType("application/msword");
        } else if (field.getType().equalsIgnoreCase(Types.EXCEL_FILE)) {
            intent.setType("application/vnd.ms-excel");
        } else if (field.getType().equalsIgnoreCase(Types.PPT_FILE)) {
            intent.setType("application/vnd.ms-powerpoint");
        } else if (field.getType().equalsIgnoreCase(Types.VIDEO_FILE)) {
            intent.setType("video/*");
        } else if (field.getType().equalsIgnoreCase(Types.GIF_FILE)) {
            intent.setType("image/gif");
        } else if (field.getType().equalsIgnoreCase(Types.AUDIO_FILE)) {
            intent.setType("audio/*");
        } else if (field.getType().equalsIgnoreCase(Types.ZIP_FILE)) {
            intent.setType("application/x-wav");
        } else if (field.getType().equalsIgnoreCase(Types.TEXT_FILE)) {
            intent.setType("text/plain");
        } else if (field.getType().equalsIgnoreCase(Types.GALLERY)) {
            intent.setType("image/*");
        } else if (field.getType().equalsIgnoreCase(Types.CAMERA)) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    ((Permissions) context).checkCameraPermission();
                    return;
                }
            }
            fileUri = FileUtilsPath.takePicture(context, Types.File_REQUEST_CODE);
            return;
        } else if (field.getType().equalsIgnoreCase(Types.CROP_CAMERA)) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    ((Permissions) context).checkCameraPermission();
                    return;
                }
            }
            fileUri = FileUtilsPath.takePicture(context, Types.CROP_File_REQUEST_CODE);
            return;
        } else if (field.getType().equalsIgnoreCase(Types.SIGNATURE)) {
            intent = new Intent(context, SignatureActivity.class);
            context.startActivityForResult(intent, Types.File_REQUEST_CODE);
            return;
        } else {
            //if you want you can also define the intent type for any other file
            //additionally use else clause below, to manage other unknown extensions
            //in this case, Android will show all applications installed on the device
            //so you can choose which application to use
            intent.setType("*/*");
        }

        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        intent.setAction(Intent.ACTION_GET_CONTENT);
        context.startActivityForResult(Intent.createChooser(intent, "Select File"), Types.File_REQUEST_CODE);

    }

    private void alignGalleryToLeft(Gallery gallery) {

        WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);

        int galleryWidth = manager.getDefaultDisplay().getWidth();

        // We are taking the item widths and spacing from a dimension resource
        // because:
        // 1. No way to get spacing at runtime (no accessor in the Gallery
        // class)
        // 2. There might not yet be any item view created when we are calling
        // this
        // function
        int itemWidth = getsize(100);
        int spacing = getsize(20);

        // The offset is how much we will pull the gallery to the left in order
        // to simulate left alignment of the first item
        final int offset;
        if (galleryWidth <= itemWidth) {
            offset = galleryWidth / 2 - itemWidth / 2 - spacing;
        } else {
            offset = galleryWidth - itemWidth - 2 * spacing;
        }

        // Now update the layout parameters of the gallery in order to set the
        // left margin
        MarginLayoutParams mlp = (MarginLayoutParams) gallery.getLayoutParams();
        mlp.setMargins(-offset, mlp.topMargin, mlp.rightMargin,
                mlp.bottomMargin);
    }

    public int getsize(int sizeInDip) {
        DisplayMetrics metrics = new DisplayMetrics();
        metrics = getResources().getDisplayMetrics();
        return (int) ((sizeInDip * metrics.density) + 0.5);
    }
}
