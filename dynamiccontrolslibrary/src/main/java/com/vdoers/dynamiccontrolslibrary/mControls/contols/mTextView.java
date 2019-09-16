package com.vdoers.dynamiccontrolslibrary.mControls.contols;

import android.app.Activity;
import android.content.Intent;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.vdoers.dynamiccontrolslibrary.R;
import com.vdoers.dynamiccontrolslibrary.mControls.Constant;
import com.vdoers.dynamiccontrolslibrary.mControls.TermsAndConditionWebViewActivity;


public class mTextView extends LinearLayout {

    private TextView textView;
    private Activity context;

    public mTextView(Activity context, JsonWorkflowList.Field field) {
        super(context);
        setOrientation(LinearLayout.VERTICAL);
        this.context = context;
        initUI();
        setClickListener(field);
        showUI(field);

    }

    private void showUI(JsonWorkflowList.Field field) {
        if (field.getReadMoreURL() != null && !field.getReadMoreURL().equalsIgnoreCase("")) {
            SpannableString content = new SpannableString(field.getLabel());
            content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
            textView.setText(content);
        } else {
            textView.setText(field.getLabel());
        }
    }

    private void setClickListener(final JsonWorkflowList.Field field) {
        //textView.setOnClickListener((OnClickListener) context);
        if (field.getReadMoreURL() != null && !field.getReadMoreURL().equalsIgnoreCase("")) {
            textView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Toast.makeText(context, "coming soon...", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(context, TermsAndConditionWebViewActivity.class);
                    intent.putExtra(Constant.WEBVIEW_URL, field.getReadMoreURL());
                    context.startActivity(intent);
                }
            });
        }

    }

    private void initUI() {
        LinearLayout topLayout = (LinearLayout) context.getLayoutInflater().inflate(R.layout.dynamic_textview, null);
        textView = (TextView) topLayout.findViewById(R.id.textview);
        addView(topLayout);

    }
}
