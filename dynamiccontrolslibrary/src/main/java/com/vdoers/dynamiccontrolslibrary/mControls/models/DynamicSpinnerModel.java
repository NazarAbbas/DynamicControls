package com.vdoers.dynamiccontrolslibrary.mControls.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DynamicSpinnerModel {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("value")
    @Expose
    private String value;
    @SerializedName("gotoId")
    @Expose
    private String gotoId;

    private boolean isSelected;

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getGotoId() {
        return gotoId;
    }

    public void setGotoId(String gotoId) {
        this.gotoId = gotoId;
    }
}
