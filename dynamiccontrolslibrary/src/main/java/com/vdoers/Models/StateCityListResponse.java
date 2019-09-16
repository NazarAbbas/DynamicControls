package com.vdoers.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class StateCityListResponse {
    @SerializedName("UserID")
    @Expose
    private int userID;
    @SerializedName("ResponseCode")
    @Expose
    private int responseCode;
    @SerializedName("ResponseMessage")
    @Expose
    private String responseMessage;
    @SerializedName("Count")
    @Expose
    private int count;
    @SerializedName("StateCityList")
    @Expose
    private List<StateCityList> stateCityList = null;

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public void setResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<StateCityList> getStateCityList() {
        return stateCityList;
    }

    public void setStateCityList(List<StateCityList> stateCityList) {
        this.stateCityList = stateCityList;
    }

}
