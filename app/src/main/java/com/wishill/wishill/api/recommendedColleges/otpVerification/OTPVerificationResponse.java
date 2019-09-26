package com.wishill.wishill.api.recommendedColleges.otpVerification;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by altoopa on 9/10/2016.
 */
public class OTPVerificationResponse {
    @SerializedName("success") @Expose private Integer success;
    @SerializedName("message") @Expose private String message;
    @SerializedName("recomm") @Expose private List<OTPVerificationData> dataList = new ArrayList<OTPVerificationData>();

    public List<OTPVerificationData> getDataList() {
        return dataList;
    }

    public void setDataList(List<OTPVerificationData> dataList) {
        this.dataList = dataList;
    }

    public Integer getSuccess() {
        return success;
    }

    public void setSuccess(Integer success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
