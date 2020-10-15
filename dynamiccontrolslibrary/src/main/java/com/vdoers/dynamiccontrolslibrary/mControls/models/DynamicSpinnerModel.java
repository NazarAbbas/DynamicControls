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


    @SerializedName("enableEdit")
    @Expose
    private boolean enableEdit;

    @SerializedName("EditFieldName")
    @Expose
    private String editFieldName;

    public String getEditFieldName() {
        return editFieldName;
    }

    public void setEditFieldName(String editFieldName) {
        this.editFieldName = editFieldName;
    }

    public boolean isEnableEdit() {
        return enableEdit;
    }

    public void setEnableEdit(boolean enableEdit) {
        this.enableEdit = enableEdit;
    }

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
