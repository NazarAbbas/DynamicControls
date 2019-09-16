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


public class mHeading extends LinearLayout {

    private TextView textView;
    private Activity context;

    public mHeading(Activity context, JsonWorkflowList.Field field) {
        super(context);
        setOrientation(LinearLayout.VERTICAL);
        this.context = context;
        initUI();
        setClickListener(field);
        showUI(field);

    }

    public String getValue(){
        return textView.getText().toString().trim();
    }

    private void showUI(JsonWorkflowList.Field field) {
        textView.setText(field.getLabel());
    }

    private void setClickListener(final JsonWorkflowList.Field field) {
    /*    if (field.getReadMoreURL() != null && !field.getReadMoreURL().equalsIgnoreCase("")) {
            textView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, TermsAndConditionWebViewActivity.class);
                    intent.putExtra(Constant.WEBVIEW_URL, field.getReadMoreURL());
                    context.startActivity(intent);
                }
            });
        }
*/
    }

    private void initUI() {
        LinearLayout topLayout = (LinearLayout) context.getLayoutInflater().inflate(R.layout.dynamic_heading, null);
        textView = (TextView) topLayout.findViewById(R.id.textview);
        addView(topLayout);

    }
}
