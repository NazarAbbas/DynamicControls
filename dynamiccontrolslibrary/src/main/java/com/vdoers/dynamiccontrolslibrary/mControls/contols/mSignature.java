package com.vdoers.dynamiccontrolslibrary.mControls.contols;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.vdoers.dynamiccontrolslibrary.R;
import com.vdoers.dynamiccontrolslibrary.mControls.SignatureActivity;
import com.vdoers.dynamiccontrolslibrary.mControls.models.ImageSavedModel;

import java.util.List;



public class mSignature extends LinearLayout implements View.OnClickListener {
    private ImageView image;
    private mCircleButton button;
    private Activity context;
    private TextView tvHeading;
    private JsonWorkflowList.Field field;
    List<ImageSavedModel> imageSavedModelList = null;
    private boolean isImageClicked;

  /*  public boolean isSignatureDone() {
        return isSignatureDone;
    }

    public void setSignatureDone(boolean signatureDone) {
        isSignatureDone = signatureDone;
    }
*/
  //  private boolean isSignatureDone;


    public mSignature(Context context) {
        super(context);
    }
    public boolean isImageClicked() {
        return isImageClicked;
    }

    public void setImageClicked(boolean imageClicked) {
        this.isImageClicked = imageClicked;
    }

    public mSignature(Activity context, JsonWorkflowList.Field field) {
        super(context);
        setOrientation(LinearLayout.VERTICAL);
        this.context = context;
        this.field = field;
        initUI();
        setClickListener();
        showUI(field);

    }

    private void setClickListener() {
        button.setOnClickListener(this);
    }

    private void showUI(JsonWorkflowList.Field field) {
        tvHeading.setText(field.getLabel());
       /* if (field.getBitMapString() != null && field.getBitMapString() != "") {
            image.setImageBitmap(StringToBitMap(field.getBitMapString()));
        } else {
            image.setImageBitmap(null);
        }*/

        if (field.getImageSavedModelList() != null && field.getImageSavedModelList().size() > 0) {
            this.imageSavedModelList = field.getImageSavedModelList();
            image.setImageBitmap(imageSavedModelList.get(0).getBitmap());
        } else {
            image.setImageBitmap(null);
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


    public void setImage(Bitmap bitmap) {
        image.setImageBitmap(bitmap);
    }


    private void initUI() {
        LinearLayout topLayout = (LinearLayout) context.getLayoutInflater().inflate(R.layout.dynamic_signature, null);
        image = (ImageView) topLayout.findViewById(R.id.img);
        button = (mCircleButton) topLayout.findViewById(R.id.btn);
        tvHeading = (TextView) topLayout.findViewById(R.id.tv_heading);
        addView(topLayout);

    }


    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.btn){
            setImageClicked(true);
            Intent intent = new Intent(context, SignatureActivity.class);
            context.startActivityForResult(intent, Types.SIGNATURE_CODE);
        }
       /* switch (v.getId()) {
            case R.id.btn:
                setImageClicked(true);
                Intent intent = new Intent(context, SignatureActivity.class);
                context.startActivityForResult(intent, Types.SIGNATURE_CODE);
                break;
            default:
                break;
        }*/
    }

}

