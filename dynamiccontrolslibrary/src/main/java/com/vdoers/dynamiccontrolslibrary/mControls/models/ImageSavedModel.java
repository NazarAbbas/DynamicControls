package com.vdoers.dynamiccontrolslibrary.mControls.models;

import android.graphics.Bitmap;

public class ImageSavedModel {
    private byte[] byteArray;
    private String imageName;
    private Bitmap bitmap;
    private String imageNameKey;

    public String getImageNameKey() {
        return imageNameKey;
    }

    public void setImageNameKey(String imageNameKey) {
        this.imageNameKey = imageNameKey;
    }



    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }


    public byte[] getByteArray() {
        return byteArray;
    }

    public void setByteArray(byte[] byteArray) {
        this.byteArray = byteArray;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }


}
