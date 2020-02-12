package commdel.com;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.google.gson.Gson;
import com.vdoers.cropper.CropImage;
import com.vdoers.cropper.CropImageView;
import com.vdoers.dynamiccontrolslibrary.Utils.Permissions;
import com.vdoers.dynamiccontrolslibrary.mControls.Constant;
import com.vdoers.dynamiccontrolslibrary.mControls.ControlRederer;
import com.vdoers.dynamiccontrolslibrary.mControls.contols.JsonWorkflowList;
import com.vdoers.dynamiccontrolslibrary.mControls.contols.Types;
import com.vdoers.dynamiccontrolslibrary.mControls.contols.mFile;
import com.vdoers.dynamiccontrolslibrary.mControls.contols.mOTPReciever;
import com.vdoers.dynamiccontrolslibrary.mControls.models.FileSavedModel;
import com.vdoers.dynamiccontrolslibrary.mControls.models.ImageSavedModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import commdel.Database.FileDBHandler;
import commdel.Database.JSONWorkflowDBHandler;
import commdel.Models.ResponseDto;
import commdel.com.dcontrols.R;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class DynamicControlRendererActivity extends Permissions implements View.OnClickListener {
    private LinearLayout llTopLayout;
    private final String TAG = "DynamicControl";
    //private LinearLayout llMainLayout, llHeaderLayout;
    private JsonWorkflowList.Subform subForm = null;
    private Stack<JsonWorkflowList.Subform> stack = new Stack<>();
    //public static HashMap<String, Object> mapValues = new HashMap<>();
    // public static DataObject dataObject = new DataObject();
    private static int tempIdx = 0;
    private ScrollView scrollView;
    // private DynamicControlResponse dynamicControlResponse;
    public static List<ImageSavedModel> imageSavedModelList = new ArrayList<>();
    private JsonWorkflowList jsonWorkflowList;
    public static final String WORKFLOW_ID = "workflow_id";
    public static final String POSITION = "position";
    private int workFlowId = 0;
    private String id = "";
    private int position = 0;
    private JSONObject obj = null;
    private Gson gson = new Gson();
    private String leadDetailsObject;
    public static final String ID = "id";
    private Map<Integer, JsonWorkflowList.Subform> workFlowListObject = new HashMap<Integer, JsonWorkflowList.Subform>();
    public static DynamicControlRendererActivity dynamicControlRendererActivity;
    //public static DataObject dataObject = new DataObject();

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dynamic_control_renderer);
        dynamicControlRendererActivity = this;
        initUI();
        workFlowId = getIntent().getIntExtra(WORKFLOW_ID, 0);
        id = getIntent().getStringExtra(ID);
        position = getIntent().getIntExtra(POSITION, 0);
        if (id == null) {
            jsonWorkflowList = JSONWorkflowDBHandler.getJsonWorkFlowByWorkflowId(this, workFlowId);
        } else {
            jsonWorkflowList = JSONWorkflowDBHandler.getJsonWorkFlow(this, workFlowId, id);
        }

        showUI();

    }

    private void initUI() {
        scrollView = (ScrollView) findViewById(R.id.scroll_view);
        llTopLayout = (LinearLayout) findViewById(R.id.ll_top_layout);
    }

    private void showUI() {
        if (jsonWorkflowList != null) {
            for (int i = 0; i < jsonWorkflowList.getSubforms().size(); i++) {
                workFlowListObject.put(Integer.parseInt(jsonWorkflowList.getSubforms().get(i).getId()), jsonWorkflowList.getSubforms().get(i));
            }
            subForm = workFlowListObject.get(Integer.parseInt(jsonWorkflowList.getSubforms().get(0).getId()));

            //for Testing

           /* leadDetailsObject = getIntent().getStringExtra(Constant.LEAD_DETAIL_OBJECT);
            if (leadDetailsObject != null && !leadDetailsObject.equalsIgnoreCase("")) {
                try {
                    obj = new JSONObject(leadDetailsObject);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                for (int i = 0; i < subForm.getFields().size(); i++) {
                    Iterator<String> iter = obj.keys();
                    JsonWorkflowList.Field field = subForm.getFields().get(i);
                    while (iter.hasNext()) {
                        String key = iter.next();
                        try {
                            if (field.getName().equalsIgnoreCase(key)) {
                                Object value = obj.get(key);
                                Permissions.dataObject.put(field.getName(), value);
                            }
                        } catch (JSONException e) {
                            String x = "";
                        }


                    }
                }
            }*/
            //Testing End

            ControlRederer.renderSubScreens(this, subForm, llTopLayout);

        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_back:
                onBackPressed();
                break;
            case R.id.btn_next:
                startNewActivity();
                break;
            default:
                break;
        }
    }

    String actionType = "";

    void startNewActivity() {
        boolean isTrue = ControlRederer.checkRequiredValues(this, subForm);
        if (!isTrue)
            return;

        scrollView.post(new Runnable() {
            @Override
            public void run() {
                scrollView.fullScroll(scrollView.FOCUS_UP);
            }
        });
        JsonWorkflowList.Field field = subForm.getFields().get(subForm.getFields().size() - 1);
        if (field.getName().equalsIgnoreCase("Submit")) {
            ControlRederer.setValues(subForm);
            insetFileIntoTable();
            submitData();
        } else if (field.getName().equalsIgnoreCase("Verify")) {
            //  Call OTP API
            String xx = mOTPReciever.otpText;
            String x = xx;
            ControlRederer.setValues(subForm);
            removeViews();
            callNextScreen();
        } else if (field.getName().equalsIgnoreCase("Saveandcontinue")) {
            ControlRederer.setValues(subForm);
            Intent returnIntent = new Intent();
            returnIntent.putExtra("result", position);
            setResult(100, returnIntent);
            finish();
        } else {
            ControlRederer.setValues(subForm);
            removeViews();
            callNextScreen();
        }
    }

    @Override
    public void onBackPressed() {
        // TODO Auto-generated method stub
        removeViews();
        callPreviousScreen();
    }

    private void submitData() {

        JSONObject obj = new JSONObject(Permissions.dataObject);
        Gson gson = new Gson();
        String requestString = gson.toJson(Permissions.dataObject);
        String x = "";


    }

    private void callNextScreen() {
        try {
            stack.push(subForm);
        } catch (Exception e) {
            e.printStackTrace();
        }


        List<JsonWorkflowList.OptionList> optionList = null;
        for (int i = 0; i < subForm.getFields().size(); i++) {
            if (subForm.getFields().get(i).getOptionList() != null && !subForm.getFields().get(i).getOptionList().isEmpty() && subForm.getFields().get(i).getRequired().equalsIgnoreCase("Y")
                    && subForm.getFields().get(i).getType().equalsIgnoreCase(Types.CHOICE)) {
                optionList = subForm.getFields().get(i).getOptionList();
            }
        }
        if (optionList != null) {
            String gotoId = "-100";
            for (int i = 0; i < optionList.size(); i++) {
                if (optionList.get(i).getIsChecked()) {
                    gotoId = optionList.get(i).getGotoId();
                }
            }
            if (gotoId.isEmpty() || gotoId.equalsIgnoreCase("-100")) {
                subForm = workFlowListObject.get(Integer.parseInt(subForm.getGotoId()));
            } else {
                subForm = workFlowListObject.get(Integer.parseInt(gotoId));
            }
        } else {
            subForm = workFlowListObject.get(Integer.parseInt(subForm.getGotoId()));
        }


        //For Testing

        if (obj != null) {
            for (int i = 0; i < subForm.getFields().size(); i++) {
                JsonWorkflowList.Field field = subForm.getFields().get(i);
                Iterator<String> iter = obj.keys();
                while (iter.hasNext()) {
                    String key = iter.next();
                    try {
                        if (field.getName().equalsIgnoreCase(key)) {
                            Object value = obj.get(key);
                            Permissions.dataObject.put(field.getName(), value);
                        }
                    } catch (JSONException e) {
                        // Something went wrong!
                        String x = "";
                    }


                }
            }
        }
        //Testing End


        //display subform
        ControlRederer.renderSubScreens(this, subForm, llTopLayout);
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.slide_right);
        llTopLayout.startAnimation(animation);

    }

    private void callPreviousScreen() {
        if (!stack.isEmpty()) {
            //Remove values of current screen on back press
            // removePreviousFillData();

            if (llTopLayout != null) {
                subForm = stack.pop();
            }
            ControlRederer.renderSubScreens(this, subForm, llTopLayout);
            Animation animation = AnimationUtils.loadAnimation(this, R.anim.slide_left);
            llTopLayout.startAnimation(animation);
        } else {


            ControlRederer.setValues(subForm);
           /* for (int i = 0; i < subForm.getFields().size(); i++) {
                if (subForm.getFields().get(i).getType().equalsIgnoreCase(Types.CHECKBOX)) {
                    if ((String) subForm.getFields().get(i).getAnswer() != null &&
                            ((String) subForm.getFields().get(i).getAnswer()).equalsIgnoreCase("true")) {

                    }
                }
            }*/

            super.onBackPressed();
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        }
    }

    private void removePreviousFillData() {
        for (int i = 0; i < subForm.getFields().size(); i++) {
            JsonWorkflowList.Field field = subForm.getFields().get(i);

            for (int j = 0; j < imageSavedModelList.size(); j++) {
                if (imageSavedModelList != null && imageSavedModelList.size() > 0) {
                    if (field.getAnswer() instanceof String) {
                        if (imageSavedModelList.get(j).getImageName().equalsIgnoreCase((String) field.getAnswer())) {
                            imageSavedModelList.remove(j);
                            field.setBitMapString(null);

                        }
                    }
                }
            }

            if (Permissions.dataObject.containsKey(field.getName())) {
                field.setAnswer("", field);
                Permissions.dataObject.remove(field.getName());
            }

        }
    }


    private void removeViews() {
        LinearLayout headerLayout = (LinearLayout) llTopLayout.getChildAt(0);
        ScrollView scrollView = (ScrollView) llTopLayout.getChildAt(1);
        LinearLayout contentLayout = (LinearLayout) scrollView.getChildAt(0);
        contentLayout.removeAllViews();
        headerLayout.removeAllViews();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {


        if (requestCode == 100) {
            boolean isTrue = data.getBooleanExtra("isBack", false);
            if (isTrue) {
            } else {
                Intent returnIntent = new Intent();
                returnIntent.putExtra("result", position);
                setResult(100, returnIntent);
                finish();
            }

        }
       /* if (data != null && requestCode == Types.SIGNATURE_CODE) {
            ControlRederer.setImage(requestCode, resultCode, data, subForm, this);
        }*/
        if (data != null && requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            //ControlRederer.setImage(requestCode, resultCode, data, subForm, this);
            if (resultCode == RESULT_OK) {

                ControlRederer.setFile(data, subForm, this);
            }
        }

        if (requestCode == Types.File_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                ControlRederer.setFile(data, subForm, this);
            }
        }


        if (requestCode == Types.CROP_File_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Uri selectedImage = mFile.fileUri;//getOutputMediaFileUri(this);// fileUri.getPath();//.getData();
                CropImage.activity(selectedImage)
                        .setGuidelines(CropImageView.Guidelines.ON)//.setRequestedSize(Constant.IMAGE_REQUESTED_WIDTH, Constant.IMAGE_REQUESTED_HEIGHT)
                        .start(this);
            }
        }
    }//onActivityResult


    private void insetFileIntoTable() {
        for (int i = 0; i < jsonWorkflowList.getSubforms().size(); i++) {
            for (int j = 0; j < jsonWorkflowList.getSubforms().get(i).getFields().size(); j++) {
                if (jsonWorkflowList.getSubforms().get(i).getFields().get(j).getType().equalsIgnoreCase(Types.GALLERY)
                        || jsonWorkflowList.getSubforms().get(i).getFields().get(j).getType().equalsIgnoreCase(Types.SIGNATURE)
                        || jsonWorkflowList.getSubforms().get(i).getFields().get(j).getType().equalsIgnoreCase(Types.SNAP)
                        || jsonWorkflowList.getSubforms().get(i).getFields().get(j).getType().equalsIgnoreCase(Types.PDF_FILE)
                        || jsonWorkflowList.getSubforms().get(i).getFields().get(j).getType().equalsIgnoreCase(Types.WORD_FILE)
                        || jsonWorkflowList.getSubforms().get(i).getFields().get(j).getType().equalsIgnoreCase(Types.PPT_FILE)
                        || jsonWorkflowList.getSubforms().get(i).getFields().get(j).getType().equalsIgnoreCase(Types.EXCEL_FILE)
                        || jsonWorkflowList.getSubforms().get(i).getFields().get(j).getType().equalsIgnoreCase(Types.AUDIO_FILE)
                        || jsonWorkflowList.getSubforms().get(i).getFields().get(j).getType().equalsIgnoreCase(Types.VIDEO_FILE)
                        || jsonWorkflowList.getSubforms().get(i).getFields().get(j).getType().equalsIgnoreCase(Types.ZIP_FILE)
                        || jsonWorkflowList.getSubforms().get(i).getFields().get(j).getType().equalsIgnoreCase(Types.PPT_FILE)
                        || jsonWorkflowList.getSubforms().get(i).getFields().get(j).getType().equalsIgnoreCase(Types.TEXT_FILE)
                        || jsonWorkflowList.getSubforms().get(i).getFields().get(j).getType().equalsIgnoreCase(Types.GALLERY)
                        || jsonWorkflowList.getSubforms().get(i).getFields().get(j).getType().equalsIgnoreCase(Types.CAMERA)
                        || jsonWorkflowList.getSubforms().get(i).getFields().get(j).getType().equalsIgnoreCase(Types.CROP_CAMERA)) {
                    if (Permissions.dataObject.containsKey(jsonWorkflowList.getSubforms().get(i).getFields().get(j).getName())) {
                        List<FileSavedModel> fileImageSavedModelList = null;
                        int caseId = 0;// MyApplication.sharedPreference.getString(Constant.CASE_ID);
                        FileSavedModel fileSavedModel = null;
                        try {
                            fileImageSavedModelList = (List<FileSavedModel>) Permissions.dataObject.get(jsonWorkflowList.getSubforms().get(i).getFields().get(j).getName());
                            Permissions.dataObject.removeProperty(jsonWorkflowList.getSubforms().get(i).getFields().get(j).getName());
                            if (fileImageSavedModelList != null && fileImageSavedModelList.size() > 0) {
                                for (int k = 0; k < fileImageSavedModelList.size(); k++) {
                                    fileSavedModel = new FileSavedModel();
                                    fileSavedModel.setCaseId("12345");
                                    fileSavedModel.setFileName(fileImageSavedModelList.get(k).getFileName());
                                    fileSavedModel.setFilePath(fileImageSavedModelList.get(k).getFilePath());
                                    FileDBHandler.saveFile(this, fileSavedModel);
                                }
                            }
                            String fileName = "";
                            if (fileImageSavedModelList != null && fileImageSavedModelList.size() > 0) {
                                for (int k = 0; k < fileImageSavedModelList.size(); k++) {
                                    fileName = fileName + fileImageSavedModelList.get(k).getFileName() + "|";
                                }
                                fileName = fileName.substring(0, fileName.length() - 1);
                                Permissions.dataObject.setProperty(jsonWorkflowList.getSubforms().get(i).getFields().get(j).getName(), fileName);
                            } else {
                                Permissions.dataObject.setProperty(jsonWorkflowList.getSubforms().get(i).getFields().get(j).getName(), "");
                            }

                        } catch (Exception ex) {
                            String e = ex.getMessage();

                        }
                    }
                }

            }
        }


    }

    int idx = 0;

    private void uplodaDataOnServer() {
        showProgressDialog(this, true);
        final LinkedList<Integer> imageIdList = FileDBHandler.getUnsendFileIds(getApplicationContext());
        if (imageIdList != null && imageIdList.size() > 0) {
            //sendTimelineNotification(imageIdList.size());
            for (idx = 0; idx < imageIdList.size(); idx++) {
                int rowId = imageIdList.get(idx);
                try {
                    // get image all data from DB
                    final FileSavedModel fileSavedModel = FileDBHandler.getFileDetails(getApplicationContext(), rowId);
                    if (fileSavedModel == null) continue;
                    File file = new File(fileSavedModel.getFilePath());
                    RequestBody requestBody = RequestBody.create(MediaType.parse("*/*"), file);
                    MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("file", fileSavedModel.getFileName(), requestBody);
                    DataServiceApi api = new RestClient().getApi();
                    Call<ResponseDto> call = api.uploadCaseImage("111", fileSavedModel.getFileName(), fileToUpload);
                    call.enqueue(new Callback<ResponseDto>() {
                        @Override
                        public void onResponse(Call<ResponseDto> call, Response<ResponseDto> response) {
                            ResponseDto responseDto = response.body();
                            if (responseDto.getResponseCode() == 200) {
                                if (idx == imageIdList.size()) {
                                    dismissProgressDialog();
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseDto> call, Throwable t) {
                            dismissProgressDialog();
                        }
                    });

                } catch (Exception ex) {
                    ex.printStackTrace();
                    dismissProgressDialog();
                }
            }
        }
    }


    /**
     * @param :
     * @return :
     * @author : Nazar
     * @created : Nov 20, 2016
     * @method name : showProgressDialog().
     * @description : This method is used to show the Progress Dialog Box.
     */

    ProgressDialog progressDialog;

    public void showProgressDialog(Context context, boolean flag) {
        progressDialog = new ProgressDialog(context);
        if (flag) {
            if (!progressDialog.isShowing()) {
                progressDialog.show();
            }
        }
        progressDialog.setContentView(R.layout.custom_progressbar);
        progressDialog.setCancelable(false);
        progressDialog.setIndeterminate(true);

    }

    /**
     * @param :
     * @return :
     * @author : Nazar
     * @created : Nov 20, 2016
     * @method name : dismissProgressDialog().
     * @description : This method is used to show the dismiss Progress Dialog Box.
     */
    public void dismissProgressDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }


}



