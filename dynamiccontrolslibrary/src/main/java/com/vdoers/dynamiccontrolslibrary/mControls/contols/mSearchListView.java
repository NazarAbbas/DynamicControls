package com.vdoers.dynamiccontrolslibrary.mControls.contols;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.vdoers.dynamiccontrolslibrary.R;
import com.vdoers.dynamiccontrolslibrary.Utils.ThemeColor;
import com.vdoers.dynamiccontrolslibrary.mControls.adapters.SearchListviewCustomAdapter;

import java.util.ArrayList;
import java.util.List;



public class mSearchListView extends AppCompatActivity implements View.OnClickListener {

    private List<JsonWorkflowList.OptionList> optionList = new ArrayList<>();
    private SearchListviewCustomAdapter searchListviewCustomAdapter;
    private ListView listView;
    public static final String OPTIONS_LIST = "option_list";
    public static final String SEARCH_EDITTEXT_NAME_KEY = "search_edittetxt_name_key";
    public static final String TITLE = "title";
    private ImageView imgBack;
    private TextView tvHeader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dynamic_search_listview);
        //getLayoutInflater().inflate(R.layout.dynamic_search_listview, frameContainer);
        String jsonOptionList = getIntent().getStringExtra(OPTIONS_LIST);
        Gson gson = new GsonBuilder().create();
        TypeToken<List<JsonWorkflowList.OptionList>> token = new TypeToken<List<JsonWorkflowList.OptionList>>() {
        };
        optionList = gson.fromJson(jsonOptionList, token.getType());
        initUI();
        showUI();
    }

    private void initUI() {
        imgBack = (ImageView) findViewById(R.id.img_back);
        imgBack.setOnClickListener(this);
        tvHeader = (TextView) findViewById(R.id.tv_header);
        Button btnClear = (Button) findViewById(R.id.btn_clear);
        Button btnDone = (Button) findViewById(R.id.btn_done);
        btnDone.setBackgroundColor(ThemeColor.themeColor);
        btnDone.setOnClickListener(this);
        final EditText editSearch = (EditText) findViewById(R.id.edit_search);
        listView = (ListView) findViewById(R.id.lst);

        editSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if (searchListviewCustomAdapter != null)
                    searchListviewCustomAdapter.filterData(s.toString());

            }
        });
        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editSearch.setText("");
            }
        });
    }

    private void showUI() {
        tvHeader.setText(getIntent().getStringExtra(TITLE));
        searchListviewCustomAdapter = new SearchListviewCustomAdapter(this, optionList);
        listView.setAdapter(searchListviewCustomAdapter);

    }


    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.btn_done){
            String result = "";
            for (int i = 0; i < optionList.size(); i++) {
                if (optionList.get(i).getIsChecked()) {
                    Gson gson = new Gson();
                    result = gson.toJson(optionList.get(i));
                }
            }
            finish();
            //Log.d("sender", "Broadcasting message");
            Intent intent = new Intent(getIntent().getStringExtra(SEARCH_EDITTEXT_NAME_KEY));
            // You can also include some extra data.
            intent.putExtra("message", result);
            LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
        }
        else if(v.getId()==R.id.img_back){
            onBackPressed();
        }
/*        switch (v.getId()) {
            case R.id.btn_done:
                String result = "";
                for (int i = 0; i < optionList.size(); i++) {
                    if (optionList.get(i).getIsChecked()) {
                        Gson gson = new Gson();
                        result = gson.toJson(optionList.get(i));
                    }
                }
                finish();
                //Log.d("sender", "Broadcasting message");
                Intent intent = new Intent(getIntent().getStringExtra(SEARCH_EDITTEXT_NAME_KEY));
                // You can also include some extra data.
                intent.putExtra("message", result);
                LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
                break;
            case R.id.img_back:
                onBackPressed();
                break;
        }*/

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}
