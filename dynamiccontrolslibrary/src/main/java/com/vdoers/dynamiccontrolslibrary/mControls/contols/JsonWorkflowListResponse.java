package com.vdoers.dynamiccontrolslibrary.mControls.contols;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class JsonWorkflowListResponse {
    @SerializedName("UserID")
    @Expose
    private Integer userID;
    @SerializedName("ResponseCode")
    @Expose
    private Integer responseCode;
    @SerializedName("ResponseMessage")
    @Expose
    private String responseMessage;
    @SerializedName("Count")
    @Expose
    private Integer count;
    @SerializedName("JsonWorkflowList")
    @Expose
    private List<JsonWorkflowList> jsonWorkflowList = null;

    public Integer getUserID() {
        return userID;
    }

    public void setUserID(Integer userID) {
        this.userID = userID;
    }

    public Integer getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(Integer responseCode) {
        this.responseCode = responseCode;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public void setResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public List<JsonWorkflowList> getJsonWorkflowList() {
        return jsonWorkflowList;
    }

    public void setJsonWorkflowList(List<JsonWorkflowList> jsonWorkflowList) {
        this.jsonWorkflowList = jsonWorkflowList;
    }
}

