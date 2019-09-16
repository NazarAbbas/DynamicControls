package com.vdoers.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StateCityList {

    @SerializedName("CityId")
    @Expose
    private int cityId;
    @SerializedName("CityName")
    @Expose
    private String cityName;
    @SerializedName("StateId")
    @Expose
    private int stateId;
    @SerializedName("StateName")
    @Expose
    private String stateName;
    @SerializedName("ModifiedDate")
    @Expose
    private String modifiedDate;
    @SerializedName("Action")
    @Expose
    private String action;

    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public int getStateId() {
        return stateId;
    }

    public void setStateId(int stateId) {
        this.stateId = stateId;
    }

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    public String getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(String modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

}
