package com.vdoers.dynamiccontrolslibrary.mControls;

import android.app.Activity;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.OpenableColumns;
import android.util.Base64;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import com.theartofdev.edmodo.cropper.CropImage;
import com.vdoers.dynamiccontrolslibrary.R;
import com.vdoers.dynamiccontrolslibrary.Utils.FileUtilsPath;
import com.vdoers.dynamiccontrolslibrary.Utils.Permissions;
import com.vdoers.dynamiccontrolslibrary.mControls.contols.JsonWorkflowList;
import com.vdoers.dynamiccontrolslibrary.mControls.contols.Types;
import com.vdoers.dynamiccontrolslibrary.mControls.contols.mButton;
import com.vdoers.dynamiccontrolslibrary.mControls.contols.mCheckbox;
import com.vdoers.dynamiccontrolslibrary.mControls.contols.mDate;
import com.vdoers.dynamiccontrolslibrary.mControls.contols.mEditBox;
import com.vdoers.dynamiccontrolslibrary.mControls.contols.mFile;
import com.vdoers.dynamiccontrolslibrary.mControls.contols.mHeading;
import com.vdoers.dynamiccontrolslibrary.mControls.contols.mMapEditBoxAddress;
import com.vdoers.dynamiccontrolslibrary.mControls.contols.mMultipleChoice;
import com.vdoers.dynamiccontrolslibrary.mControls.contols.mOTPReciever;
import com.vdoers.dynamiccontrolslibrary.mControls.contols.mSearchEditBox;
import com.vdoers.dynamiccontrolslibrary.mControls.contols.mSingleChoice;
import com.vdoers.dynamiccontrolslibrary.mControls.contols.mSpinner;
import com.vdoers.dynamiccontrolslibrary.mControls.contols.mTextView;
import com.vdoers.dynamiccontrolslibrary.mControls.contols.mTime;
import com.vdoers.dynamiccontrolslibrary.mControls.contols.mToolbar;
import com.vdoers.dynamiccontrolslibrary.mControls.models.FileSavedModel;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


public class ControlRederer {

    public static void renderSubScreens(
            final Activity ctx, JsonWorkflowList.Subform subForm, LinearLayout llTopLayout) {
        LinearLayout headerLayout = (LinearLayout) llTopLayout.getChildAt(0);
        ScrollView scrollView = (ScrollView) llTopLayout.getChildAt(1);
        LinearLayout contentLayout = (LinearLayout) scrollView.getChildAt(0);
        headerLayout.addView(new mToolbar(ctx, subForm));
        renderFields(ctx, contentLayout, subForm);
    }


    private static void renderFields(Activity ctx, LinearLayout mainLayout, JsonWorkflowList.Subform subForm) {
        for (int i = 0; i < subForm.getFields().size(); i++) {

            JsonWorkflowList.Field field = subForm.getFields().get(i);

/*            if (field.getType().equalsIgnoreCase(Types.SNAP) || field.getType().equalsIgnoreCase(Types.GALLERY) || field.getType().equalsIgnoreCase(Types.SIGNATURE)) {
                if (DynamicControlRendererActivity.dataObject.containsKey(field.getName())) {
                    List<ImageSavedModel> imageSavedModelsList = null;
                    try {
                        imageSavedModelsList = (List<ImageSavedModel>) DynamicControlRendererActivity.dataObject.get(field.getName());
                    } catch (Exception ex) {
                        String e = ex.getMessage();
                    }
                    if (imageSavedModelsList != null) {
                        List<ImageSavedModel> tempImageSavedModelsList = new ArrayList<>(imageSavedModelsList);
                        int totalCount = imageSavedModelsList.size();
                        if (field.getImageSavedModelList() != null) {
                            field.getImageSavedModelList().clear();
                        }
                        for (int k = 0; k < totalCount; k++) {
                            field.setImageSavedModelList(tempImageSavedModelsList.get(k));
                        }
                    }

                }
            } else */


            if (field.getType().equalsIgnoreCase(Types.PDF_FILE)
                    || field.getType().equalsIgnoreCase(Types.WORD_FILE)

                    || field.getType().equalsIgnoreCase(Types.EXCEL_FILE)
                    || field.getType().equalsIgnoreCase(Types.AUDIO_FILE)
                    || field.getType().equalsIgnoreCase(Types.VIDEO_FILE)
                    || field.getType().equalsIgnoreCase(Types.ZIP_FILE)
                    || field.getType().equalsIgnoreCase(Types.PPT_FILE)
                    || field.getType().equalsIgnoreCase(Types.TEXT_FILE)
                    || field.getType().equalsIgnoreCase(Types.GALLERY)
                    || field.getType().equalsIgnoreCase(Types.CAMERA)
                    || field.getType().equalsIgnoreCase(Types.CROP_CAMERA)
                    || field.getType().equalsIgnoreCase(Types.SIGNATURE)) {
                if (Permissions.dataObject.containsKey(field.getName())) {
                    List<FileSavedModel> fileSavedModelList = null;
                    try {
                        fileSavedModelList = (List<FileSavedModel>) Permissions.dataObject.get(field.getName());
                    } catch (Exception ex) {
                        String e = ex.getMessage();
                    }
                    if (fileSavedModelList != null) {
                        List<FileSavedModel> tempImageSavedModelsList = new ArrayList<>(fileSavedModelList);
                        int totalCount = fileSavedModelList.size();
                        if (field.getFileSavedModelList() != null) {
                            field.getFileSavedModelList().clear();
                        }
                        for (int k = 0; k < totalCount; k++) {
                            field.setFileSavedModelList(tempImageSavedModelsList.get(k));
                        }
                    }

                }
            } else {
                Object answer = field.getAnswer();
                if (answer == null || ((String) answer).equalsIgnoreCase("")) {
                    if (Permissions.dataObject.containsKey(field.getName())) {
                        answer = Permissions.dataObject.get(field.getName());
                    }
                } else {
                    if (field.getAnswer() instanceof String) {
                        answer = (String) field.getAnswer();
                    } else if (field.getAnswer() instanceof Boolean) {
                        answer = (Boolean) field.getAnswer();
                    } else {

                    }
                }
                field.setAnswer(answer, field);
            }


            if (field.getType().equalsIgnoreCase(Types.EDITBOX_TYPE)
                    || field.getType().equalsIgnoreCase(Types.EDITBOX_NUMBER_TYPE)
                    || field.getType().equalsIgnoreCase(Types.EXACT_EDITBOX_NUMBER_TYPE)
                    || field.getType().equalsIgnoreCase(Types.EDITBOX_EMAIL)) {
                field.setObject(new mEditBox(ctx, field));
                mainLayout.addView((View) field.getObject());
            } else if (field.getType().equalsIgnoreCase(Types.BUTTON_TYPE)) {
                field.setObject(new mButton(ctx, field));
                mainLayout.addView((View) field.getObject());

            } else if (field.getType().equalsIgnoreCase(Types.DROPDOWN) || field.getType().equalsIgnoreCase(Types.CITY) || field.getType().equalsIgnoreCase(Types.STATE)) {
                field.setObject(new mSpinner(ctx, field));
                mainLayout.addView((View) field.getObject());
            } else if (field.getType().equalsIgnoreCase(Types.CHOICE)) {
                field.setObject(new mSingleChoice(ctx, field));
                mainLayout.addView((View) field.getObject());
            } else if (field.getType().equalsIgnoreCase(Types.MUTIPLE_CHOICE)) {
                field.setObject(new mMultipleChoice(ctx, field));
                mainLayout.addView((View) field.getObject());
            } else if (field.getType().equalsIgnoreCase(Types.CHECKBOX)) {
                field.setObject(new mCheckbox(ctx, field));
                mainLayout.addView((View) field.getObject());
            } else if (field.getType().equalsIgnoreCase(Types.TEXTVIEW)) {
                field.setObject(new mTextView(ctx, field));
                mainLayout.addView((View) field.getObject());
            } else if (field.getType().equalsIgnoreCase(Types.TIME)) {
                field.setObject(new mTime(ctx, field));
                mainLayout.addView((View) field.getObject());
            } else if (field.getType().equalsIgnoreCase(Types.DATE)) {
                field.setObject(new mDate(ctx, field));
                mainLayout.addView((View) field.getObject());
            } /*else if (field.getType().equalsIgnoreCase(Types.SIGNATURE)) {
                field.setObject(new mSignature(ctx, field));
                mainLayout.addView((View) field.getObject());
            }*//* else if (field.getType().equalsIgnoreCase(Types.SNAP)) {
                field.setObject(new mPhoto(ctx, field));
                mainLayout.addView((View) field.getObject());
            } *//*else if (field.getType().equalsIgnoreCase(Types.GALLERY)) {
                field.setObject(new mGallery(ctx, field));
                mainLayout.addView((View) field.getObject());
            }*/ else if (field.getType().equalsIgnoreCase(Types.SEARCH_EDITTEXT_TYPE)) {
                field.setObject(new mSearchEditBox(ctx, field));
                mainLayout.addView((View) field.getObject());
            } else if (field.getType().equalsIgnoreCase(Types.MAP_ADDRESS)) {
                field.setObject(new mMapEditBoxAddress(ctx, field));
                mainLayout.addView((View) field.getObject());
            } else if (field.getType().equalsIgnoreCase(Types.OTP_RECIEVER)) {
                field.setObject(new mOTPReciever(ctx, field));
                mainLayout.addView((View) field.getObject());
            }
            else if (field.getType().equalsIgnoreCase(Types.HEADING)) {
                field.setObject(new mHeading(ctx, field));
                mainLayout.addView((View) field.getObject());
            }
            else if (field.getType().equalsIgnoreCase(Types.PDF_FILE)
                    || field.getType().equalsIgnoreCase(Types.WORD_FILE)

                    || field.getType().equalsIgnoreCase(Types.EXCEL_FILE)
                    || field.getType().equalsIgnoreCase(Types.AUDIO_FILE)
                    || field.getType().equalsIgnoreCase(Types.VIDEO_FILE)
                    || field.getType().equalsIgnoreCase(Types.ZIP_FILE)
                    || field.getType().equalsIgnoreCase(Types.PPT_FILE)
                    || field.getType().equalsIgnoreCase(Types.TEXT_FILE)
                    || field.getType().equalsIgnoreCase(Types.GALLERY)
                    || field.getType().equalsIgnoreCase(Types.CAMERA)
                    || field.getType().equalsIgnoreCase(Types.CROP_CAMERA)
                    || field.getType().equalsIgnoreCase(Types.SIGNATURE)) {
                field.setObject(new mFile(ctx, field));
                mainLayout.addView((View) field.getObject());

            }


        }
    }

    public static void setValues(JsonWorkflowList.Subform subForm) {
        for (int fieldIdx = subForm.getFields().size() - 1; fieldIdx >= 0; fieldIdx--) {
            JsonWorkflowList.Field field = subForm.getFields().get(fieldIdx);
            if (field.getType().equalsIgnoreCase(Types.EDITBOX_TYPE)
                    || field.getType().equalsIgnoreCase(Types.EDITBOX_NUMBER_TYPE)
                    || field.getType().equalsIgnoreCase(Types.EXACT_EDITBOX_NUMBER_TYPE)
                    || field.getType().equalsIgnoreCase(Types.EDITBOX_EMAIL)) {
                mEditBox dynamicEditBox = (mEditBox) field.getObject();
                field.setAnswer(dynamicEditBox.getValue(), field);
            }

            if (field.getType().equalsIgnoreCase(Types.DROPDOWN) || field.getType().equalsIgnoreCase(Types.CITY) || field.getType().equalsIgnoreCase(Types.STATE)) {
                mSpinner spinner = (mSpinner) field.getObject();
                field.setAnswer(spinner.getValue(), field);
            }
            if (field.getType().equalsIgnoreCase(Types.CHOICE)) {
                mSingleChoice choice = (mSingleChoice) field.getObject();
                field.setAnswer(choice.getValue(), field);
            }
            if (field.getType().equalsIgnoreCase(Types.MUTIPLE_CHOICE)) {
                mMultipleChoice multipleChoice = (mMultipleChoice) field.getObject();
                field.setAnswer(multipleChoice.getValue(), field);
            }
            if (field.getType().equalsIgnoreCase(Types.CHECKBOX)) {
                mCheckbox checkbox = (mCheckbox) field.getObject();
                field.setAnswer(checkbox.getValue() + "", field);
            }

            if (field.getType().equalsIgnoreCase(Types.DATE)) {
                mDate date = (mDate) field.getObject();
                field.setAnswer(date.getValue(), field);
            }

            if (field.getType().equalsIgnoreCase(Types.TIME)) {
                mTime time = (mTime) field.getObject();
                field.setAnswer(time.getValue(), field);
            }

            if (field.getType().equalsIgnoreCase(Types.HEADING)) {
                mHeading mHeading = (mHeading) field.getObject();
                field.setAnswer(mHeading.getValue(), field);
            }
           /* if (field.getType().equalsIgnoreCase(Types.SIGNATURE)) {
                if (field.getImageSavedModelList() == null || field.getImageSavedModelList().size() == 0) {
                    field.setAnswer("", field);
                }
            }*/
           /* if (field.getType().equalsIgnoreCase(Types.SNAP)) {
                if (field.getImageSavedModelList() == null || field.getImageSavedModelList().size() == 0) {
                    field.setAnswer("", field);
                }
            }*/

          /*  if (field.getType().equalsIgnoreCase(Types.GALLERY)) {
                if (field.getImageSavedModelList() == null || field.getImageSavedModelList().size() == 0) {
                    field.setAnswer("", field);
                }
            }*/

            if (field.getType().equalsIgnoreCase(Types.OTP_RECIEVER)) {
                mOTPReciever mOTPReciever = (mOTPReciever) field.getObject();
                field.setAnswer(mOTPReciever.getValue(), field);
            }


            if (field.getType().equalsIgnoreCase(Types.PDF_FILE)
                    || field.getType().equalsIgnoreCase(Types.WORD_FILE)

                    || field.getType().equalsIgnoreCase(Types.EXCEL_FILE)
                    || field.getType().equalsIgnoreCase(Types.AUDIO_FILE)
                    || field.getType().equalsIgnoreCase(Types.VIDEO_FILE)
                    || field.getType().equalsIgnoreCase(Types.ZIP_FILE)
                    || field.getType().equalsIgnoreCase(Types.PPT_FILE)
                    || field.getType().equalsIgnoreCase(Types.TEXT_FILE)
                    || field.getType().equalsIgnoreCase(Types.GALLERY)
                    || field.getType().equalsIgnoreCase(Types.CAMERA)
                    || field.getType().equalsIgnoreCase(Types.CROP_CAMERA)
                    || field.getType().equalsIgnoreCase(Types.SIGNATURE)) {
                if (field.getFileSavedModelList() == null || field.getFileSavedModelList().size() == 0) {
                    field.setAnswer("", field);
                }
            }


            if (field.getType().equalsIgnoreCase(Types.SEARCH_EDITTEXT_TYPE)) {
                mSearchEditBox checkbox = (mSearchEditBox) field.getObject();
                field.setAnswer(checkbox.getValue(), field);
            }
            if (field.getType().equalsIgnoreCase(Types.MAP_ADDRESS)) {
                mMapEditBoxAddress mapEditBoxAddress = (mMapEditBoxAddress) field.getObject();
                field.setAnswer(mapEditBoxAddress.getValue(), field);
            }


        }
    }

    public static boolean checkRequiredValues(Activity activity, JsonWorkflowList.Subform subForm) {
        for (int i = 0; i < subForm.getFields().size(); i++) {
            JsonWorkflowList.Field field = subForm.getFields().get(i);
            if (field.getType().equalsIgnoreCase(Types.EDITBOX_TYPE)) {
                mEditBox dynamicEditBox = (mEditBox) field.getObject();
                if (field.getRequired().equalsIgnoreCase("Y")) {
                    if (field.getRegex() != null && !field.getRegex().isEmpty()) {
                        if (!dynamicEditBox.isValid(dynamicEditBox.getValue(), field.getRegex(), field.getType())) {
                            dynamicEditBox.setError(activity.getString(R.string.invalid) + " " + field.getLabel().replace("*", ""));
                            return false;
                        }
                    }
                    if (field.getMinLength() > 0 && dynamicEditBox.getValue().length() < field.getMinLength()) {
                        dynamicEditBox.setError(activity.getString(R.string.invalid) + " " + field.getLabel().replace("*", ""));
                        return false;
                    }
                    if (dynamicEditBox.getValue().isEmpty()) {
                        dynamicEditBox.setError(activity.getString(R.string.please_enter) + " " + field.getLabel().replace("*", ""));
                        return false;
                    }
                } else {
                    if (field.getRegex() != null && !field.getRegex().isEmpty() && !dynamicEditBox.getValue().isEmpty()) {
                        if (!dynamicEditBox.isValid(dynamicEditBox.getValue(), field.getRegex(), field.getType())) {
                            dynamicEditBox.setError(activity.getString(R.string.invalid) + " " + field.getLabel().replace("*", ""));
                            return false;
                        }
                    }
                    if (!dynamicEditBox.getValue().isEmpty() && field.getMinLength() > 0 &&
                            dynamicEditBox.getValue().length() < field.getMinLength()) {
                        dynamicEditBox.setError(activity.getString(R.string.invalid) + " " + field.getLabel().replace("*", ""));
                        return false;
                    }
                }
            }
            if (field.getType().equalsIgnoreCase(Types.EDITBOX_EMAIL)) {
                mEditBox dynamicEditBox = (mEditBox) field.getObject();
                if (field.getRequired().equalsIgnoreCase("Y")) {
                    if (!dynamicEditBox.isValid(dynamicEditBox.getValue(), field.getRegex(), field.getType())) {
                        dynamicEditBox.setError(activity.getString(R.string.invalid) + " " + field.getLabel().replace("*", ""));
                        return false;
                    }
                } else {
                    if (!dynamicEditBox.getValue().isEmpty() &&
                            field.getRegex() != null && !field.getRegex().isEmpty()
                            &&
                            !dynamicEditBox.isValid(dynamicEditBox.getValue(), field.getRegex(), field.getType())) {
                        dynamicEditBox.setError(activity.getString(R.string.invalid) + " " + field.getLabel().replace("*", ""));
                        return false;
                    }
                }
            }


            if (field.getType().equalsIgnoreCase(Types.EDITBOX_NUMBER_TYPE)) {
                mEditBox dynamicEditBox = (mEditBox) field.getObject();
                if (field.getRequired().equalsIgnoreCase("Y")) {
                    if (field.getMinLength() > 0 && dynamicEditBox.getValue().length() < field.getMinLength()) {
                        dynamicEditBox.setError(activity.getString(R.string.invalid) + " " + field.getLabel().replace("*", ""));
                        return false;
                    }
                } else {
                    if (!dynamicEditBox.getValue().isEmpty() && field.getMinLength() > 0
                            && dynamicEditBox.getValue().length() < field.getMinLength()) {
                        dynamicEditBox.setError(activity.getString(R.string.invalid) + " " + field.getLabel().replace("*", ""));
                        return false;
                    }
                }
            }

            if (field.getType().equalsIgnoreCase(Types.OTP_RECIEVER)) {
                mOTPReciever mOTPReciever = (mOTPReciever) field.getObject();
                if (field.getRequired().equalsIgnoreCase("Y")) {
                    if (field.getMinLength() > 0 && mOTPReciever.getValue().length() < field.getMinLength()) {
                        mOTPReciever.setError(activity.getString(R.string.invalid) + " " + field.getLabel().replace("*", ""));
                        return false;
                    }
                } else {
                    if (!mOTPReciever.getValue().isEmpty() && field.getMinLength() >0
                            && mOTPReciever.getValue().length() < field.getMinLength()) {
                        mOTPReciever.setError(activity.getString(R.string.invalid) + " " + field.getLabel().replace("*", ""));
                        return false;
                    }
                }
            }

          /*  if (field.getType().equalsIgnoreCase(Types.EXACT_EDITBOX_NUMBER_TYPE)) {
                mEditBox dynamicEditBox = (mEditBox) field.getObject();
                if (field.getRequired().equalsIgnoreCase("Y")) {
                    if (dynamicEditBox.getValue().length() != field.getMaxLength()) {
                        dynamicEditBox.setError(activity.getString(R.string.invalid) + field.getLabel().replace("*", ""));
                        return false;
                    }
                } else {
                    if (!dynamicEditBox.getValue().equalsIgnoreCase("") &&
                            dynamicEditBox.getValue().length() != field.getMaxLength()) {
                        dynamicEditBox.setError(activity.getString(R.string.invalid) + field.getLabel().replace("*", ""));
                        return false;
                    }
                }
            }*/

            if (field.getType().equalsIgnoreCase(Types.DROPDOWN) || field.getType().equalsIgnoreCase(Types.CITY) || field.getType().equalsIgnoreCase(Types.STATE)) {
                mSpinner spinner = (mSpinner) field.getObject();
                if (field.getRequired().equalsIgnoreCase("Y")) {
                    if (spinner.getValue().isEmpty()) {
                        Toast.makeText(activity, activity.getString(R.string.please_select) + " " + field.getLabel(), Toast.LENGTH_LONG).show();
                        return false;
                    }
                }
            }

            if (field.getType().equalsIgnoreCase(Types.CHOICE)) {
                mSingleChoice singleChoice = (mSingleChoice) field.getObject();
                if (field.getRequired().equalsIgnoreCase("Y")) {
                    if (singleChoice.getValue().isEmpty()) {
                        Toast.makeText(activity, activity.getString(R.string.please_select) + " " + field.getLabel(), Toast.LENGTH_LONG).show();
                        return false;
                    }
                }
            }
            if (field.getType().equalsIgnoreCase(Types.MUTIPLE_CHOICE)) {
                mMultipleChoice multipleChoice = (mMultipleChoice) field.getObject();
                if (field.getRequired().equalsIgnoreCase("Y")) {
                    if (multipleChoice.getValue().isEmpty()) {
                        Toast.makeText(activity, activity.getString(R.string.please_select) + " " + field.getLabel(), Toast.LENGTH_LONG).show();
                        //multipleChoice.setError("Please enter  " + field.getLabel().replace("*", ""));
                        return false;
                    }
                }
            }
            if (field.getType().equalsIgnoreCase(Types.CHECKBOX)) {
                mCheckbox checkbox = (mCheckbox) field.getObject();
                if (field.getRequired().equalsIgnoreCase("Y")) {
                    if (!checkbox.getValue()) {
                        Toast.makeText(activity, "Please select " + " " + field.getLabel(), Toast.LENGTH_LONG).show();
                        return false;
                    }
                }
            }

            if (field.getType().equalsIgnoreCase(Types.DATE)) {
                mDate date = (mDate) field.getObject();
                if (field.getRequired().equalsIgnoreCase("Y")) {
                    if (date.getValue().isEmpty()) {
                        //Toast.makeText(activity, "test", Toast.LENGTH_LONG).show();
                        date.setError("Please select  " + field.getLabel().replace("*", ""));
                        return false;
                    }
                }
            }
            if (field.getType().equalsIgnoreCase(Types.TIME)) {
                mTime time = (mTime) field.getObject();
                if (field.getRequired().equalsIgnoreCase("Y")) {
                    if (time.getValue().isEmpty()) {
                        time.setError("Please select  " + field.getLabel().replace("*", ""));
                        return false;
                    }
                }
            }

        /*    if (field.getType().equalsIgnoreCase(Types.SIGNATURE)) {
                mSignature signature = (mSignature) field.getObject();
                if (field.getRequired().equalsIgnoreCase("Y")) {
                    if (field.getImageSavedModelList() == null || field.getImageSavedModelList().size() == 0) {
                        Toast.makeText(activity, activity.getString(R.string.please_select) + " " + field.getLabel(), Toast.LENGTH_LONG).show();
                        return false;
                    }*/
                   /* if (signature.getValue().equalsIgnoreCase("")) {
                        Toast.makeText(activity, activity.getString(R.string.please_select) + " " + field.getLabel(), Toast.LENGTH_LONG).show();
                        return false;
                    }*/
            //     }
               /* if (field.getImageSavedModelList() == null || field.getImageSavedModelList().size() == 0) {
                    field.setAnswer("", field);
                }*/
            //  }
           /* if (field.getType().equalsIgnoreCase(Types.SNAP)) {

                if (field.getRequired().equalsIgnoreCase("Y")) {
                    if (field.getImageSavedModelList() == null || field.getImageSavedModelList().size() == 0) {
                        Toast.makeText(activity, activity.getString(R.string.please_select) + " " + field.getLabel(), Toast.LENGTH_LONG).show();
                        return false;
                    }
                }
            }*/

           /* if (field.getType().equalsIgnoreCase(Types.GALLERY)) {
                if (field.getRequired().equalsIgnoreCase("Y")) {
                    if (field.getImageSavedModelList() == null || field.getImageSavedModelList().size() == 0) {
                        Toast.makeText(activity, activity.getString(R.string.please_select) + " " + field.getLabel(), Toast.LENGTH_LONG).show();
                        return false;
                    }

                }
            }*/

            if (field.getType().equalsIgnoreCase(Types.PDF_FILE)
                    || field.getType().equalsIgnoreCase(Types.WORD_FILE)
                    || field.getType().equalsIgnoreCase(Types.EXCEL_FILE)
                    || field.getType().equalsIgnoreCase(Types.AUDIO_FILE)
                    || field.getType().equalsIgnoreCase(Types.VIDEO_FILE)
                    || field.getType().equalsIgnoreCase(Types.ZIP_FILE)
                    || field.getType().equalsIgnoreCase(Types.PPT_FILE)
                    || field.getType().equalsIgnoreCase(Types.TEXT_FILE)
                    || field.getType().equalsIgnoreCase(Types.GALLERY)
                    || field.getType().equalsIgnoreCase(Types.CAMERA)
                    || field.getType().equalsIgnoreCase(Types.CROP_CAMERA)
                    || field.getType().equalsIgnoreCase(Types.SIGNATURE)) {
                if (field.getRequired().equalsIgnoreCase("Y")) {
                    if (field.getFileSavedModelList() == null || field.getFileSavedModelList().size() == 0) {
                        Toast.makeText(activity, activity.getString(R.string.please_select) + " " + field.getLabel(), Toast.LENGTH_LONG).show();
                        return false;
                    }
                    if (field.getFileSavedModelList().size() < field.getMinLength()) {
                        Toast.makeText(activity, activity.getString(R.string.please_select) + " " + activity.getString(R.string.atleast) + " " + field.getMinLength() + " " + field.getLabel(), Toast.LENGTH_LONG).show();
                        return false;
                    }
                }
            }

            if (field.getType().equalsIgnoreCase(Types.SEARCH_EDITTEXT_TYPE)) {
                mSearchEditBox searchEditBox = (mSearchEditBox) field.getObject();
                if (field.getRequired().equalsIgnoreCase("Y")) {
                    if (searchEditBox.getValue().isEmpty()) {
                        Toast.makeText(activity, "Please select " + field.getLabel(), Toast.LENGTH_LONG).show();
                        return false;
                    }
                }
            }
            if (field.getType().equalsIgnoreCase(Types.MAP_ADDRESS)) {
                mMapEditBoxAddress mapEditBoxAddress = (mMapEditBoxAddress) field.getObject();
                if (field.getRequired().equalsIgnoreCase("Y")) {
                    if (mapEditBoxAddress.getValue().isEmpty()) {
                        Toast.makeText(activity, "Please enter " + field.getLabel(), Toast.LENGTH_LONG).show();
                        return false;
                    }
                }
            }


        }
        return true;
    }
/*

    public static void setImage(int requestCode, int resultCode, Intent data, JsonWorkflowList.Subform subForm, Activity context) {
        try {
            String userId = "123";//DynamicControlRendererActivity.sharedPreference.getInteger(Constant.USER_ID) + "";
            if (requestCode == Types.SIGNATURE_CODE) {
                for (int fieldIdx = 0; fieldIdx < subForm.getFields().size(); fieldIdx++) {
                    JsonWorkflowList.Field field = subForm.getFields().get(fieldIdx);
                    if (field.getType().equalsIgnoreCase(Types.SIGNATURE)) {
                        mSignature signature = (mSignature) field.getObject();
                        if (signature.isImageClicked()) {
                            signature.setImageClicked(false);
                            byte[] bytesArray = data.getByteArrayExtra(context.getString(R.string.signature_bytes_array));
                            Bitmap bitmap = BitmapFactory.decodeByteArray(bytesArray, 0, bytesArray.length);

                            if (DynamicControlRendererActivity.dataObject.containsKey(field.getName())) {
                                DynamicControlRendererActivity.dataObject.removeProperty(field.getName());
                                if (field.getImageSavedModelList() != null && field.getImageSavedModelList().size() > 0) {
                                    for (int i = 0; i < field.getImageSavedModelList().size(); i++) {
                                        field.getImageSavedModelList().remove(i);
                                    }
                                }
                            }
                            String name = userId + "_" + field.getName() + "_"
                                    + System.currentTimeMillis() + ".png";
                            ImageSavedModel imageSavedModel = new ImageSavedModel();
                            imageSavedModel.setImageName(name);
                            imageSavedModel.setImageNameKey(field.getName());
                            imageSavedModel.setBitmap(null);
                            imageSavedModel.setByteArray(bytesArray);
                            field.setBitMapString(BitMapToString(bitmap));
                            field.setImageSavedModelList(imageSavedModel);
                            signature.setImage(imageSavedModel.getBitmap());
                            DynamicControlRendererActivity.dataObject.setProperty(field.getName(), field.getImageSavedModelList());
                            String x = "";
                        }
                    }
                }
            }
            if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
                for (int fieldIdx = 0; fieldIdx < subForm.getFields().size(); fieldIdx++) {
                    JsonWorkflowList.Field field = subForm.getFields().get(fieldIdx);
                    if (field.getType().equalsIgnoreCase(Types.SNAP)) {
                        if (resultCode == context.RESULT_OK) {
                            mPhoto photo = (mPhoto) field.getObject();
                            if (photo.isImageClicked()) {
                                photo.setImageClicked(false);
                                CropImage.ActivityResult result = CropImage.getActivityResult(data);
                                Uri resultUri = result.getUri();
                                Bitmap bitmap = getBitmapFromUri(context, resultUri);
                                String name = userId + "_" + field.getName() + "_"
                                        + System.currentTimeMillis() + ".jpg";
                                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                                bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                                byte[] bytesArray = stream.toByteArray();
                                ImageSavedModel imageSavedModel = new ImageSavedModel();
                                imageSavedModel.setImageName(name);
                                imageSavedModel.setImageNameKey(field.getName());
                                imageSavedModel.setBitmap(bitmap);
                                imageSavedModel.setByteArray(bytesArray);
                                field.setBitMapString(BitMapToString(bitmap));
                                field.setImageSavedModelList(imageSavedModel);
                                photo.bindImageAdapter(field.getImageSavedModelList());
                                DynamicControlRendererActivity.dataObject.setProperty(field.getName(), field.getImageSavedModelList());
                            }

                        } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                        }
                    }

                    if (field.getType().equalsIgnoreCase(Types.GALLERY)) {
                        if (resultCode == context.RESULT_OK) {
                            mGallery mGallery = (mGallery) field.getObject();
                            if (mGallery.isImageClicked()) {
                                mGallery.setImageClicked(false);

                                CropImage.ActivityResult result = CropImage.getActivityResult(data);
                                Uri resultUri = result.getUri();
                                Bitmap bitmap = getBitmapFromUri(context, resultUri);
                                String name = userId + "_" + field.getName() + "_"
                                        + System.currentTimeMillis() + ".jpg";
                                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                                bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                                byte[] bytesArray = stream.toByteArray();
                                ImageSavedModel imageSavedModel = new ImageSavedModel();
                                imageSavedModel.setImageName(name);
                                imageSavedModel.setImageNameKey(field.getName());
                                imageSavedModel.setBitmap(bitmap);
                                imageSavedModel.setByteArray(bytesArray);
                                field.setBitMapString(BitMapToString(bitmap));
                                field.setImageSavedModelList(imageSavedModel);
                                mGallery.bindImageAdapter(field.getImageSavedModelList());
                                DynamicControlRendererActivity.dataObject.setProperty(field.getName(), field.getImageSavedModelList());
                            }

                        }
                    } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                    }


                }
            }
        } catch (Exception ex) {
            String x = ex.getMessage();
        }

    }*/

    public static void setFile(Intent data, JsonWorkflowList.Subform subForm, Activity context) {


        FileSavedModel fileSavedModel;// new FileSavedModel();
        String selectedFileName = "";
        Cursor cursor = null;
        // File myFile;
        String displayName;
        for (int fieldIdx = 0; fieldIdx < subForm.getFields().size(); fieldIdx++) {
            JsonWorkflowList.Field field = subForm.getFields().get(fieldIdx);
            if (field.getType().equalsIgnoreCase(Types.PDF_FILE)
                    || field.getType().equalsIgnoreCase(Types.WORD_FILE)

                    || field.getType().equalsIgnoreCase(Types.EXCEL_FILE)
                    || field.getType().equalsIgnoreCase(Types.AUDIO_FILE)
                    || field.getType().equalsIgnoreCase(Types.VIDEO_FILE)
                    || field.getType().equalsIgnoreCase(Types.ZIP_FILE)
                    || field.getType().equalsIgnoreCase(Types.PPT_FILE)
                    || field.getType().equalsIgnoreCase(Types.TEXT_FILE)
                    || field.getType().equalsIgnoreCase(Types.GALLERY)
                    || field.getType().equalsIgnoreCase(Types.CAMERA)
                    || field.getType().equalsIgnoreCase(Types.CROP_CAMERA)
                    || field.getType().equalsIgnoreCase(Types.SIGNATURE)) {

                if (mFile.controlName != null && mFile.controlName == field.getName()) {
                    mFile mPdfFile = null;
                    //Uri uri = null;
                    if (field.getType().equalsIgnoreCase(Types.CAMERA)) {
                        try {
                            data = new Intent();
                            data.setData(mFile.fileUri);
                            mFile.fileUri = null;
                        } catch (Exception ex) {
                            String x = "";
                        }
                    }

                    if (field.getType().equalsIgnoreCase(Types.CROP_CAMERA)) {
                        try {
                            CropImage.ActivityResult result = CropImage.getActivityResult(data);
                            data = new Intent();
                            data.setData(result.getUri());
                            mFile.fileUri = null;
                        } catch (Exception ex) {
                            String x = "";
                        }
                    }
                    if (field.getType().equalsIgnoreCase(Types.SIGNATURE)) {
                        try {
                            String path = data.getStringExtra(context.getString(R.string.signature_bytes_array));
                            data = new Intent();
                            data.setData(Uri.parse(path));
                            mFile.fileUri = null;
                        } catch (Exception ex) {
                            String x = "";
                        }
                    }
                    if (null != data) {
                        if (data.getData() != null) {
                            mPdfFile = (mFile) field.getObject();
                            Uri uri = data.getData();
                            String uriString = uri.toString();
                            File myFile = new File(uriString);
                            String path = FileUtilsPath.getPath(context, uri); //myFile.getAbsolutePath();
                            if (uri.toString().startsWith("content://")) {
                                try {
                                    cursor = context.getContentResolver().query(uri, null, null, null, null);
                                    if (cursor != null && cursor.moveToFirst()) {
                                        String fileName = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                                        fileSavedModel = new FileSavedModel();
                                        fileSavedModel.setFileName(fileName);
                                        fileSavedModel.setFilePath(path);
                                        field.setFileSavedModelList(fileSavedModel);

                                    }
                                } finally {
                                    cursor.close();
                                }
                            } else if (uri.toString().startsWith("file://")) {
                                mPdfFile = (mFile) field.getObject();
                                String fileName = selectedFileName + myFile.getName();
                                fileSavedModel = new FileSavedModel();
                                fileSavedModel.setFileName(fileName);
                                fileSavedModel.setFilePath(path);
                                field.setFileSavedModelList(fileSavedModel);
                                //selectedFileName.add(displayName);
                            }

                        } else {
                            if (data.getClipData() != null) {
                                mPdfFile = (mFile) field.getObject();
                                ClipData mClipData = data.getClipData();
                                int totalCount;
                                if (field.getFileSavedModelList() != null) {
                                    totalCount = mClipData.getItemCount() + field.getFileSavedModelList().size();
                                } else {
                                    totalCount = mClipData.getItemCount();
                                }
                                if (totalCount > field.getMaxLength()) {
                                    Toast.makeText(context, "You can select max " + field.getMaxLength() + " file", Toast.LENGTH_LONG).show();
                                    return;
                                }
                                for (int i = 0; i < mClipData.getItemCount(); i++) {
                                    ClipData.Item item = mClipData.getItemAt(i);
                                    Uri uri = item.getUri();
                                    String uriString = uri.toString();
                                    File myFile = new File(uriString);
                                    // String path = myFile.getAbsolutePath();
                                    String path = FileUtilsPath.getPath(context, uri); //myFile.getAbsolutePath();
                                    if (uri.toString().startsWith("content://")) {
                                        try {
                                            cursor = context.getContentResolver().query(uri, null, null, null, null);
                                            if (cursor != null && cursor.moveToFirst()) {
                                                String fileName = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                                                fileSavedModel = new FileSavedModel();
                                                fileSavedModel.setFileName(fileName);
                                                fileSavedModel.setFilePath(path);
                                                field.setFileSavedModelList(fileSavedModel);

                                            }
                                        } finally {
                                            cursor.close();
                                        }
                                    } else if (uri.toString().startsWith("file://")) {
                                        mPdfFile = (mFile) field.getObject();
                                        String fileName = selectedFileName + myFile.getName();
                                        fileSavedModel = new FileSavedModel();
                                        fileSavedModel.setFileName(fileName);
                                        fileSavedModel.setFilePath(path);
                                        field.setFileSavedModelList(fileSavedModel);
                                    }
                                }
                            }
                        }
                    }

                    if (mPdfFile != null) {
                        mPdfFile.bindFileAdapter(field.getFileSavedModelList());
                        Permissions.dataObject.setProperty(field.getName(), field.getFileSavedModelList());
                    }
                    mFile.controlName = null;
                    break;
                }

            }

        }

    }


    public static String BitMapToString(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] b = baos.toByteArray();
        String temp = Base64.encodeToString(b, Base64.DEFAULT);
        return temp;
    }

    public static Bitmap getBitmapFromUri(Context context, Uri uri)
            throws FileNotFoundException {
        final InputStream imageStream = context.getContentResolver()
                .openInputStream(uri);
        try {
            return BitmapFactory.decodeStream(imageStream);
        } finally {
        }
    }
}
