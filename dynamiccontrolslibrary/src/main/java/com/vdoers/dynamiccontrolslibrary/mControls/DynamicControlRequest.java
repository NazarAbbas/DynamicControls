package com.vdoers.dynamiccontrolslibrary.mControls;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DynamicControlRequest {
    @SerializedName("UserId")
    @Expose
    private int userId;
    @SerializedName("AppVersion")
    @Expose
    private String appVersion;
    @SerializedName("DeviceId")
    @Expose
    private String deviceId;
    @SerializedName("DeviceTime")
    @Expose
    private String deviceTime;
    @SerializedName("TimeZone")
    @Expose
    private String timeZone;
    @SerializedName("Latitude")
    @Expose
    private String latitude;
    @SerializedName("Longitude")
    @Expose
    private String longitude;
    @SerializedName("Medium")
    @Expose
    private int medium;
    @SerializedName("WorkflowId")
    @Expose
    private int workflowId;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getDeviceTime() {
        return deviceTime;
    }

    public void setDeviceTime(String deviceTime) {
        this.deviceTime = deviceTime;
    }

    public String getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public int getMedium() {
        return medium;
    }

    public void setMedium(int medium) {
        this.medium = medium;
    }

    public int getWorkflowId() {
        return workflowId;
    }

    public void setWorkflowId(int workflowId) {
        this.workflowId = workflowId;
    }

}
