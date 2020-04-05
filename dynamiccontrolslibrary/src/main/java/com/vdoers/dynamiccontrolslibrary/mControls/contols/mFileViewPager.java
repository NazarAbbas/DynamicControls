package com.vdoers.dynamiccontrolslibrary.mControls.contols;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.vdoers.Components.AutoScrollViewPager;
import com.vdoers.dynamiccontrolslibrary.R;
import com.vdoers.dynamiccontrolslibrary.Utils.FileUtilsPath;
import com.vdoers.dynamiccontrolslibrary.Utils.Permissions;
import com.vdoers.dynamiccontrolslibrary.location.FusedLocation;
import com.vdoers.dynamiccontrolslibrary.location.LocationClass;
import com.vdoers.dynamiccontrolslibrary.location.LocationGPSTracker;
import com.vdoers.dynamiccontrolslibrary.mControls.PreviewActivity;
import com.vdoers.dynamiccontrolslibrary.mControls.SignatureActivity;
import com.vdoers.dynamiccontrolslibrary.mControls.adapters.CustomPagerAdapter;
import com.vdoers.dynamiccontrolslibrary.mControls.models.FileSavedModel;

import java.util.List;

public class mFileViewPager extends LinearLayout implements View.OnClickListener {
    private Activity context;
    private TextView tvHeading;
    //private boolean isImageClicked;
    private JsonWorkflowList.Field field;
    List<FileSavedModel> fileSavedModelList = null;
    public static Uri fileUri;
    public static String controlName;
    private LinearLayout llCamera;
    private LinearLayout llPreview;
    private TabLayout tabLayout;
    private AutoScrollViewPager autoScrollViewPager;
    private CustomPagerAdapter customPagerAdapter;
    /*private ImageView image;
    private ImageView imgPreview;*/
    private mCircleButton button;
    private LinearLayout llBtn;
    private boolean isButtonClicked;
    private ScrollView scrollView;
    private RelativeLayout rltBackForword;
    private ImageView imgPagerBack;
    private ImageView imgPagerForword;
    int count = 0;

    public mFileViewPager(Context context) {
        super(context);
    }

    public mFileViewPager(Activity context, JsonWorkflowList.Field field, ScrollView scrollView) {

        super(context);
        setOrientation(LinearLayout.VERTICAL);
        /*LocationGPSTracker locationGPSTracker = new LocationGPSTracker(context);
        FusedLocation fusedLocation = new FusedLocation(context);*/
        this.context = context;
        this.field = field;
        this.scrollView = scrollView;
        initUI();
        setClickListener();
        showUI(field);

    }

    private void initUI() {
        LinearLayout topLayout = (LinearLayout) context.getLayoutInflater().inflate(R.layout.dynamic_mfile_view_pager, null);
        autoScrollViewPager = (AutoScrollViewPager) topLayout.findViewById(R.id.view_pager);
        llCamera = (LinearLayout) topLayout.findViewById(R.id.ll_camera);
        llPreview = (LinearLayout) topLayout.findViewById(R.id.ll_preview);
        tabLayout = (TabLayout) topLayout.findViewById(R.id.tab_layout);
        llBtn = (LinearLayout) topLayout.findViewById(R.id.ll_btn);
        rltBackForword = (RelativeLayout) topLayout.findViewById(R.id.rlt_back_forword);
        button = (mCircleButton) topLayout.findViewById(R.id.btn);
        imgPagerBack = (ImageView) topLayout.findViewById(R.id.img_pager_back);
        imgPagerForword = (ImageView) topLayout.findViewById(R.id.img_pager_forword);
        tabLayout.setupWithViewPager(autoScrollViewPager, true);
        if (field.getType().equalsIgnoreCase(Types.CROP_CAMERA) || field.getType().equalsIgnoreCase(Types.CAMERA)
                || field.getType().equalsIgnoreCase(Types.CAMERA_WITH_ADDRESS) || field.getType().equalsIgnoreCase(Types.CROP_CAMERA_WITH_ADDRESS)) {
            llBtn.setVisibility(View.VISIBLE);
            button.setVisibility(View.GONE);
        } else {
            if (field.getType().equalsIgnoreCase(Types.SIGNATURE)) {
                button.setImageResource(R.drawable.signature_button);
            } else {
                button.setImageResource(R.drawable.browse_icon);
            }
            llBtn.setVisibility(View.GONE);
            button.setVisibility(View.VISIBLE);
        }


        // image = (ImageView) topLayout.findViewById(R.id.img);
        // imgPreview = (ImageView) topLayout.findViewById(R.id.img_preview);

        tvHeading = (TextView) topLayout.findViewById(R.id.tv_heading);
        addView(topLayout);

        nestedScroll(scrollView, autoScrollViewPager);

    }

    private void setClickListener() {
        llCamera.setOnClickListener(this);
        llPreview.setOnClickListener(this);
        button.setOnClickListener(this);
        imgPagerForword.setOnClickListener(this);
        imgPagerBack.setOnClickListener(this);

        autoScrollViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int position) {

            }

            @Override
            public void onPageSelected(int position) {
                count = position;
            }

            @Override
            public void onPageScrollStateChanged(int position) {

            }
        });

    }

    public void bindFileAdapter(List<FileSavedModel> fileSavedModelList) {
        this.fileSavedModelList = fileSavedModelList;
        if (fileSavedModelList != null && fileSavedModelList.size() > 1) {
            rltBackForword.setVisibility(View.VISIBLE);
            tabLayout.setVisibility(View.VISIBLE);
        } else {
            rltBackForword.setVisibility(View.GONE);
            tabLayout.setVisibility(View.GONE);
        }
        if (customPagerAdapter == null) {
            customPagerAdapter = new CustomPagerAdapter(context, fileSavedModelList, field, this);
            autoScrollViewPager.setAdapter(customPagerAdapter);
        } else {
            customPagerAdapter.notifyDataSetChanged();
        }

        autoScrollViewPager.setCurrentItem(fileSavedModelList.size());
        // count = fileSavedModelList.size()-1;
    }


    private void showUI(JsonWorkflowList.Field field) {
        String heading = field.getLabel();

        /*if (field.getMaxLength() > 1) {
            tabLayout.setupWithViewPager(autoScrollViewPager, true);
        }*/

        if (field.getMinLength() > 0) {
            heading = heading + " (Min " + field.getMinLength();
        }
        if (field.getMaxLength() > 0) {
            if (field.getMinLength() > 0)
                heading = heading + ", Max " + field.getMaxLength() + ")";
            else
                heading = heading + " (Max " + field.getMaxLength() + ")";
        }
        tvHeading.setText(heading);
        //tvHeading.setText(field.getLabel());
        if (field.getFileSavedModelList() != null && field.getFileSavedModelList().size() > 0) {
            this.fileSavedModelList = field.getFileSavedModelList();

            if (field.getFileSavedModelList() != null && field.getFileSavedModelList().size() > 1) {
                rltBackForword.setVisibility(View.VISIBLE);
                tabLayout.setVisibility(View.VISIBLE);
            } else {
                rltBackForword.setVisibility(View.GONE);
                tabLayout.setVisibility(View.GONE);
            }

            if (customPagerAdapter == null) {
                customPagerAdapter = new CustomPagerAdapter(context, fileSavedModelList, this.field, this);
                autoScrollViewPager.setAdapter(customPagerAdapter);
            } else {
                customPagerAdapter.notifyDataSetChanged();
            }


        } else {
            if (field.getFileSavedModelList() != null && field.getFileSavedModelList().size() > 1) {
                rltBackForword.setVisibility(View.VISIBLE);
                tabLayout.setVisibility(View.VISIBLE);
            } else {
                rltBackForword.setVisibility(View.GONE);
                tabLayout.setVisibility(View.GONE);
            }
        }
        autoScrollViewPager.setCurrentItem(0);
        // count = 0;


    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.ll_camera || v.getId() == R.id.btn) {
            if (((Permissions) context).isGPSEnabled(context)) {
                LocationGPSTracker locationGPSTracker = new LocationGPSTracker(context);
                FusedLocation fusedLocation = new FusedLocation(context);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (ContextCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        ((Permissions) context).checkCameraAndLocationPermission();
                        return;
                    }
                }

            } else {
                ((Permissions) context).turnOnGPS();
                return;
            }

            Location loc = LocationClass.getLocation(context);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ((Permissions) context).checkStoragePermission();
                    return;
                }
            }
            if (field.getRequired().equalsIgnoreCase("Y")) {
                if (field.getFileSavedModelList() == null || field.getFileSavedModelList().size() < field.getMaxLength()
                        || field.getMaxLength() == -1) {
                    openFileChooser();
                } else {
                    if (field.getType().equalsIgnoreCase(Types.CROP_CAMERA) || field.getType().equalsIgnoreCase(Types.CAMERA)
                            || field.getType().equalsIgnoreCase(Types.CAMERA_WITH_ADDRESS) || field.getType().equalsIgnoreCase(Types.CROP_CAMERA_WITH_ADDRESS)) {
                        Toast.makeText(context, "You can take max " + field.getMaxLength() + " Photo for " + field.getLabel(), Toast.LENGTH_LONG).show();
                    } else if (field.getType().equalsIgnoreCase(Types.SIGNATURE)) {
                        Toast.makeText(context, "You can take max " + field.getMaxLength() + " Signature for " + field.getLabel(), Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(context, "You can select max " + field.getMaxLength() + " File for " + field.getLabel(), Toast.LENGTH_LONG).show();
                    }
                }
            } else {
                if (field.getMaxLength() != 0) {
                    if (field.getFileSavedModelList() == null || field.getFileSavedModelList().size() < field.getMaxLength()
                            || field.getMaxLength() == -1) {
                        openFileChooser();
                    } else {
                        if (field.getType().equalsIgnoreCase(Types.CROP_CAMERA) || field.getType().equalsIgnoreCase(Types.CAMERA)
                                || field.getType().equalsIgnoreCase(Types.CAMERA_WITH_ADDRESS) || field.getType().equalsIgnoreCase(Types.CROP_CAMERA_WITH_ADDRESS)) {
                            Toast.makeText(context, "You can take max " + field.getMaxLength() + " Photo  " + field.getLabel(), Toast.LENGTH_LONG).show();
                        } else if (field.getType().equalsIgnoreCase(Types.SIGNATURE)) {
                            Toast.makeText(context, "You can take max " + field.getMaxLength() + " Signature for " + field.getLabel(), Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(context, "You can select max " + field.getMaxLength() + " File for " + field.getLabel(), Toast.LENGTH_LONG).show();
                        }
                        // Toast.makeText(context, "You can select max " + field.getMaxLength() + " " + field.getLabel(), Toast.LENGTH_LONG).show();
                    }
                } else {
                    openFileChooser();
                }
            }
        }

        if (v.getId() == R.id.ll_preview) {
            if (fileSavedModelList != null && fileSavedModelList.size() > 0) {
                Intent intent = new Intent(context, PreviewActivity.class);
                Gson gsonFestive = new Gson();
                String jsonFestive = gsonFestive.toJson(fileSavedModelList);
                intent.putExtra("ImagesList", jsonFestive);
                context.startActivity(intent);
            } else {
                Toast.makeText(context, "No Preview available!", Toast.LENGTH_LONG).show();
            }
        }

        if (v.getId() == R.id.img_pager_back) {
            if (count != 0) {
                count--;
                autoScrollViewPager.setCurrentItem(count, true);
            }
        }
        if (v.getId() == R.id.img_pager_forword) {
            if (count != fileSavedModelList.size() - 1) {
                count++;
                autoScrollViewPager.setCurrentItem(count, true);
            }
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
        } else if (field.getType().equalsIgnoreCase(Types.CAMERA) || field.getType().equalsIgnoreCase(Types.CAMERA_WITH_ADDRESS)) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    ((Permissions) context).checkCameraPermission();
                    return;
                }
            }
            fileUri = FileUtilsPath.takePicture(context, Types.File_REQUEST_CODE);
            return;
        } else if (field.getType().equalsIgnoreCase(Types.CROP_CAMERA) || field.getType().equalsIgnoreCase(Types.CROP_CAMERA_WITH_ADDRESS)) {
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
            intent.putExtra(SignatureActivity.SIGNATURE_HEADING, "TESING");
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

    public static void nestedScroll(final ScrollView scrollView, final AutoScrollViewPager viewPager) {
        viewPager.setOnTouchListener(new View.OnTouchListener() {
            int dragthreshold = 30;
            int downX;
            int downY;

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        downX = (int) event.getRawX();
                        downY = (int) event.getRawY();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        int distanceX = Math.abs((int) event.getRawX() - downX);
                        int distanceY = Math.abs((int) event.getRawY() - downY);

                        if (distanceY > distanceX && distanceY > dragthreshold) {
                            viewPager.getParent().requestDisallowInterceptTouchEvent(false);
                            scrollView.getParent().requestDisallowInterceptTouchEvent(true);
                        } else if (distanceX > distanceY && distanceX > dragthreshold) {
                            viewPager.getParent().requestDisallowInterceptTouchEvent(true);
                            scrollView.getParent().requestDisallowInterceptTouchEvent(false);
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        scrollView.getParent().requestDisallowInterceptTouchEvent(false);
                        viewPager.getParent().requestDisallowInterceptTouchEvent(false);
                        break;
                }
                return false;
            }
        });


    }

}
