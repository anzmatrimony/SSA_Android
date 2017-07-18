package com.ssa.models;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StatusModel {

    @SerializedName("statusCode")
    @Expose
    private String statusCode;
    @SerializedName("status")
    @Expose
    private String status;

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
