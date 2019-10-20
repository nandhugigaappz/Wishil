package com.wishill.wishill.api.recommendedColleges.SocialMediaLogin;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by nandhu on 9/10/2016.
 */
public class SocialMediaLoginResponse {
    @SerializedName("success") @Expose private String  success;
    @SerializedName("message") @Expose private String message;
    @SerializedName("data") @Expose private SocialMediaLoginData data;

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

    public SocialMediaLoginData getData() {
        return data;
    }

    public void setData(SocialMediaLoginData data) {
        this.data = data;
    }
}
