package com.wishill.wishill.api.recommendedColleges.SignUpWithMobile;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by altoopa on 9/10/2016.
 */
public class SignUpMobileResponse {
    @SerializedName("success") @Expose private String  success;
    @SerializedName("message") @Expose private String message;
    @SerializedName("data") @Expose private SignUpMobileDataData data;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public SignUpMobileDataData getData() {
        return data;
    }

    public void setData(SignUpMobileDataData data) {
        this.data = data;
    }
}
