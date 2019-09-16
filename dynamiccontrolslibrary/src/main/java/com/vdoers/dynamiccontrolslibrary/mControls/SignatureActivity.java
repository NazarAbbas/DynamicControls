package com.vdoers.dynamiccontrolslibrary.mControls;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.vdoers.dynamiccontrolslibrary.BuildConfig;
import com.vdoers.dynamiccontrolslibrary.R;
import com.vdoers.dynamiccontrolslibrary.Utils.Permissions;
import com.vdoers.dynamiccontrolslibrary.Utils.ThemeColor;
import com.williamww.silkysignature.views.SignaturePad;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class SignatureActivity extends Permissions implements View.OnClickListener {

    private SignaturePad signaturePad;
    private Button btnDone, btnClear;
    private boolean isSignatured = false;
    private boolean TakeSign = false;
    private int signatureCodeType;
    // private ImageView imgSignature;
    public static SignatureActivity signatureActivity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signature);
        //getLayoutInflater().inflate(R.layout.activity_signature, frameContainer);
        signatureActivity = this;
        signatureCodeType = getIntent().getIntExtra(Constant.SIGNATURE_CODE_TYPE, 0);
        initUI();
        setClickListener();
    }

    private void setClickListener() {
        btnDone.setOnClickListener(this);
        btnClear.setOnClickListener(this);
    }

    private void initUI() {
        signaturePad = (SignaturePad) findViewById(R.id.signaturePad);
        btnDone = (Button) findViewById(R.id.btn_done);
        btnDone.setBackgroundColor(ThemeColor.themeColor);
        btnClear = (Button) findViewById(R.id.btn_clear);
        btnClear.setBackgroundColor(ThemeColor.themeColor);
        signaturePad.setOnSignedListener(new SignaturePad.OnSignedListener() {
            @Override
            public void onStartSigning() {
            }

            @Override
            public void onSigned() {
                isSignatured = true;
            }

            @Override
            public void onClear() {
            }
        });
    }

    private Bitmap adjustOpacity(Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        Bitmap dest = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        int[] pixels = new int[width * height];
        bitmap.getPixels(pixels, 0, width, 0, 0, width, height);
        dest.setPixels(pixels, 0, width, 0, 0, width, height);
        return dest;
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.btn_done){
            if (!isSignatured) {
                Toast.makeText(this, getString(R.string.take_signature), Toast.LENGTH_LONG).show();
                return;
            } else {
                savebitmap(signaturePad.getSignatureBitmap());
            }
        }
        else if(v.getId()==R.id.btn_clear){
            signaturePad.clear();
        }
       /* switch (v.getId()) {
            case R.id.btn_done:


                break;
            case R.id.btn_clear:

                break;
        }*/
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    public void savebitmap(Bitmap bmp) {
        try {
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            bmp.compress(Bitmap.CompressFormat.PNG, 100, bytes);
            File dir = new File(Environment.getExternalStorageDirectory(), BuildConfig.APPLICATION_ID + "Images");
            if (!dir.exists()) {
                dir.mkdirs();
            }
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
                    Locale.getDefault()).format(new Date());
            File f = new File(dir.getPath() + File.separator
                    + "Signature_" + timeStamp + ".png");
            f.createNewFile();
            FileOutputStream fo = new FileOutputStream(f);
            fo.write(bytes.toByteArray());
            fo.close();
            String path = Uri.fromFile(f).toString();
            Intent intent = new Intent();
            intent.putExtra(getString(R.string.signature_bytes_array), path);
            setResult(Activity.RESULT_OK, intent);
            finish();
        } catch (Exception ex) {
            String x = ex.getMessage();
        }
    }
}