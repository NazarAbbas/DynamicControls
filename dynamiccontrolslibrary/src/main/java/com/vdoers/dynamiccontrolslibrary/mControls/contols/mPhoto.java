package com.vdoers.dynamiccontrolslibrary.mControls.contols;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.vdoers.dynamiccontrolslibrary.R;
import com.vdoers.dynamiccontrolslibrary.Utils.Permissions;

import com.vdoers.dynamiccontrolslibrary.mControls.adapters.ImageAdapter;
import com.vdoers.dynamiccontrolslibrary.mControls.models.ImageSavedModel;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class mPhoto extends LinearLayout implements View.OnClickListener {
    private ImageView image;
    private mCircleButton button;
    private Activity context;
    private TextView tvHeading;
    private boolean isImageClicked;
    private Gallery gallery;
    private ImageAdapter imageAdapter;
    List<ImageSavedModel> imageSavedModelList = null;
    private JsonWorkflowList.Field field;
    public static Uri fileUri;


    public boolean isImageClicked() {
        return isImageClicked;
    }

    public void setImageClicked(boolean imageClicked) {
        isImageClicked = imageClicked;
    }


    public mPhoto(Context context) {
        super(context);
    }

    public mPhoto(Activity context, JsonWorkflowList.Field field) {
        super(context);
        setOrientation(LinearLayout.VERTICAL);
        this.context = context;
        this.field = field;
        initUI();
        setClickListener();
        showUI(field);
    }

    private void showUI(JsonWorkflowList.Field field) {
        tvHeading.setText(field.getLabel() + " (Min " + field.getMinLength() + ", Max " + field.getMaxLength() + ")");
        if (field.getImageSavedModelList() != null && field.getImageSavedModelList().size() > 0) {
            this.imageSavedModelList = field.getImageSavedModelList();
            if (imageAdapter == null) {
                imageAdapter = new ImageAdapter(this.getContext(), imageSavedModelList);
                gallery.setAdapter(imageAdapter);
            } else {
                imageAdapter.notifyDataSetChanged();
            }
        } else {

        }

    }

    public Bitmap StringToBitMap(String encodedString) {
        try {
            byte[] encodeByte = Base64.decode(encodedString, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        } catch (Exception e) {
            e.getMessage();
            return null;
        }
    }

    private void setClickListener() {
        button.setOnClickListener(this);

    }

    public void setImage(Bitmap bitmap) {
        image.setImageBitmap(bitmap);
    }

    public void bindImageAdapter(List<ImageSavedModel> imageSavedModelList) {
        this.imageSavedModelList = imageSavedModelList;
        if (imageAdapter == null) {
            imageAdapter = new ImageAdapter(this.getContext(), imageSavedModelList);
            gallery.setAdapter(imageAdapter);
        } else {
            imageAdapter.notifyDataSetChanged();
        }
    }


    private void initUI() {
        LinearLayout topLayout = (LinearLayout) context.getLayoutInflater().inflate(R.layout.dynamic_photo, null);

        gallery = (Gallery) topLayout.findViewById(R.id.gallery);
        alignGalleryToLeft(gallery);
        button = (mCircleButton) topLayout.findViewById(R.id.btn);
        tvHeading = (TextView) topLayout.findViewById(R.id.tv_heading);
        addView(topLayout);
        gallery.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                deleteImage("Do you want to remove this image?", position);
            }
        });

    }

    public void deleteImage(String message, final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(message)
                .setCancelable(true).setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                imageSavedModelList.remove(position);
                Permissions.dataObject.setProperty(field.getName(), imageSavedModelList);
                if (imageAdapter != null)
                    imageAdapter.notifyDataSetChanged();


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


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn) {
            if (imageSavedModelList == null) {
                callCropperActivity();
                return;
            }
            if (imageSavedModelList.size() < field.getMaxLength()) {
                callCropperActivity();
            } else {
                Toast.makeText(context, "You can take max " + field.getMaxLength() + " Photo", Toast.LENGTH_LONG).show();
            }
        }
     /*   switch (v.getId()) {
            case R.id.btn:
                if (imageSavedModelList == null) {
                    callCropperActivity();
                    return;
                }
                if (imageSavedModelList.size() < field.getMaxLength()) {
                    callCropperActivity();
                } else {
                    Toast.makeText(context, "You can take max " + field.getMaxLength() + " Photo", Toast.LENGTH_LONG).show();
                }
                break;
            default:
                break;
        }*/
    }

    private String findSystemCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        PackageManager packageManager = context.getPackageManager();
        List<ResolveInfo> listCam = packageManager.queryIntentActivities(intent, 0);
        for (ResolveInfo info :
                listCam) {
            if (isSystemApp(info.activityInfo.packageName)) {
                return info.activityInfo.packageName;
            }
        }
        return null;
    }

    boolean isSystemApp(String packageName) {
        ApplicationInfo ai;
        try {
            ai = context.getPackageManager().getApplicationInfo(packageName, 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return false;
        }
        int mask = ApplicationInfo.FLAG_SYSTEM | ApplicationInfo.FLAG_UPDATED_SYSTEM_APP;
        return (ai.flags & mask) != 0;
    }

    private boolean isPackageExists(String targetPackage) {
        List<ApplicationInfo> packages;
        PackageManager pm;
        pm = context.getPackageManager();
        packages = pm.getInstalledApplications(0);
        for (ApplicationInfo packageInfo : packages) {
            if (packageInfo.packageName.equals(targetPackage)) {
                return true;
            }
        }
        return false;
    }

    private void callCropperActivity() {
        /*setImageClicked(true);
        CropImage.activity()
                .setGuidelines(CropImageView.Guidelines.ON).setRequestedSize(400, 400)
                .start(context);*/
        setImageClicked(true);
        String systemCamera1 = null;
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);//Start intent with Action_Image_Capture
        fileUri = getOutputMediaFileUri(context);//get fileUri from CameraUtils


        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);//Send fileUri with intent
        cameraIntent.putExtra("URI", fileUri.toString());//Send fileUri with intent
        if (isPackageExists("com.google.android.camera")) {
            cameraIntent.setPackage("com.google.android.camera");
        } else {
            systemCamera1 = findSystemCamera();
        }

        if (systemCamera1 != null) {
            cameraIntent.setPackage(systemCamera1);
        }

        context.startActivityForResult(cameraIntent, 0);//start activity for result with CAMERA_REQ
    }

    //Get Uri Of captured Image
    public static Uri getOutputMediaFileUri(Context context) {
        File mediaStorageDir = new File(
                context.getExternalFilesDir(Environment.DIRECTORY_PICTURES), context.getString(R.string.app_name));
        //If File is not present create directory
        if (!mediaStorageDir.exists()) {
            if (mediaStorageDir.mkdir())
                Log.e("Create Directory", "Main Directory Created : " + mediaStorageDir);
        }

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
                Locale.getDefault()).format(new Date());//Get Current timestamp
        File mediaFile = new File(mediaStorageDir.getPath() + File.separator
                + "IMG_" + timeStamp + ".jpg");//create image path with system mill and image format
        return Uri.fromFile(mediaFile);

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
