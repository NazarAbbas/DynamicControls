package com.vdoers.dynamiccontrolslibrary.mControls;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.vdoers.dynamiccontrolslibrary.R;


public class TermsAndConditionWebViewActivity extends AppCompatActivity implements View.OnClickListener {
    private WebView wvTermsAndCondition;
    private TextView tvHeader;
    private ImageView imgBack;

    private ProgressDialog progressDialog;
    public static final String REPORT_URL = "reportURL";
    public static final String TITLE = "title";
    private String URL;
    private TextView tvMessage;
    private int Webviewtype;
    private LinearLayout llHeader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms_and_condition_web_view);
        //getLayoutInflater().inflate(R.layout.activity_terms_and_condition_web_view, frameContainer);
        init();
        showUI();
        setOnClickListener();


    }

    private void setOnClickListener() {
        imgBack.setOnClickListener(this);

    }

    private void showUI() {
        if (Webviewtype == 1) {
            tvHeader.setText(getString(R.string.preview));
        }
        tvHeader.setText(getString(R.string.terms_and_condition));
        tvHeader.setVisibility(View.INVISIBLE);
        // String url = getIntent().getStringExtra(Constant.WEBVIEW_URL);// MyApplication.sharedPreference.getString(Constant.TERM_CONDITION_URL);
       /* wvTermsAndCondition.setWebViewClient(new WebViewClient());
        WebSettings webSettings = wvTermsAndCondition.getSettings();
        webSettings.setJavaScriptEnabled(true);
        wvTermsAndCondition.loadUrl(url);*/

    }

    private void init() {
        llHeader = (LinearLayout) findViewById(R.id.header);

        if (getIntent().getBooleanExtra("IsFromProductDetailPage", false)) {
            llHeader.setVisibility(View.GONE);
        }
        tvHeader = (TextView) findViewById(R.id.tv_header);
        tvHeader.setVisibility(View.INVISIBLE);
        imgBack = (ImageView) findViewById(R.id.img_back);
        tvMessage = (TextView) findViewById(R.id.tv_message);

        WebView webView = (WebView) findViewById(R.id.webView);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        webView.setScrollbarFadingEnabled(false);
        webView.getSettings().setBuiltInZoomControls(true);

        URL = getIntent().getStringExtra(Constant.WEBVIEW_URL);

        if (URL != null && !URL.equalsIgnoreCase("")) {
            tvMessage.setVisibility(View.GONE);
            webView.setVisibility(View.VISIBLE);
            webView.getSettings().setJavaScriptEnabled(true);
            webView.loadUrl(URL);
          //  Util.showProgressDialog(this, true);
            webView.setWebViewClient(new WebViewClient() {
                //  ProgressDialog progressDialog;
//If you will not use this method url links are opeen in new brower not in webview
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    view.loadUrl(url);
                    return true;
                }

                //Show loader on url load
                public void onLoadResource(WebView view, String url) {

                }

                public void onPageFinished(WebView view, String url) {
                  //  Util.dismissProgressDialog();
                }

            });
        } else {
            tvMessage.setVisibility(View.VISIBLE);
            webView.setVisibility(View.GONE);
        }


    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.img_back){
            onBackPressed();
        }
       /* switch (v.getId()) {
            case R.id.img_back:
                onBackPressed();
                break;
        }*/
    }

    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}