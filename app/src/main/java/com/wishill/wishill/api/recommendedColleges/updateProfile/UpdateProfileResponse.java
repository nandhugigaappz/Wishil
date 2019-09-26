package com.wishill.wishill.api.recommendedColleges.updateProfile;

/*
 * Created by nithinp on 20-02-2018.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UpdateProfileResponse {
    @SerializedName("success") @Expose private String success;
    @SerializedName("message") @Expose private String message;

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
}
