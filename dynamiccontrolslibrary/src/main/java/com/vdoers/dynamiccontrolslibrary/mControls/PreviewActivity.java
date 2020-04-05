package com.vdoers.dynamiccontrolslibrary.mControls;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.vdoers.Components.AutoScrollViewPager;
import com.vdoers.dynamiccontrolslibrary.R;
import com.vdoers.dynamiccontrolslibrary.location.LocationClass;
import com.vdoers.dynamiccontrolslibrary.mControls.adapters.PreviewAdapter;
import com.vdoers.dynamiccontrolslibrary.mControls.contols.mCircleButton;
import com.vdoers.dynamiccontrolslibrary.mControls.models.FileSavedModel;

import java.lang.reflect.Type;
import java.util.List;

public class PreviewActivity extends AppCompatActivity implements View.OnClickListener {

    private AutoScrollViewPager autoScrollViewPager;
    private TabLayout tabLayout;
    private TextView tvHeader;
    private ImageView imgBack;
    private List<FileSavedModel> fileSavedModelList;
    private RelativeLayout rltBackForword;
    private ImageView imgPagerBack;
    private ImageView imgPagerForword;
    int count = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview);
        initUI();
        setOnClickListener();
        showUI();
    }

    private void initUI() {
        autoScrollViewPager = (AutoScrollViewPager) findViewById(R.id.view_pager);
        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tvHeader = (TextView) findViewById(R.id.tv_header);
        imgBack = (ImageView) findViewById(R.id.img_back);
        rltBackForword = (RelativeLayout) findViewById(R.id.rlt_back_forword);
        imgPagerBack = (ImageView) findViewById(R.id.img_pager_back);
        imgPagerForword = (ImageView) findViewById(R.id.img_pager_forword);


    }

    private void setOnClickListener() {
        imgBack.setOnClickListener(this);
        imgPagerForword.setOnClickListener(this);
        imgPagerBack.setOnClickListener(this);

        autoScrollViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int position) {
                count = position;
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

    }


    private void showUI() {
        tvHeader.setText("");
        Type listType = new TypeToken<List<FileSavedModel>>() {
        }.getType();
        fileSavedModelList = (List<FileSavedModel>) new Gson().fromJson(getIntent().getStringExtra("ImagesList"), listType);
        PreviewAdapter customImageAdapter = new PreviewAdapter(this, fileSavedModelList);
        autoScrollViewPager.setAdapter(customImageAdapter);
        if (fileSavedModelList.size() > 1) {
            tabLayout.setVisibility(View.VISIBLE);
            tabLayout.setupWithViewPager(autoScrollViewPager);
            rltBackForword.setVisibility(View.VISIBLE);
        } else {
            tabLayout.setVisibility(View.GONE);
            rltBackForword.setVisibility(View.GONE);
        }
       /* String address = LocationClass.getCompleteAddressViaLocation(this, fileSavedModels.getLatitude(), location.getLongitude());
        tvAddress.setText();
*/
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.img_back) {
            onBackPressed();

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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}
