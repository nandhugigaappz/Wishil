package com.wishill.wishill.api.recommendedColleges.addPartnerCollege;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by nandhu on 9/10/2016.
 */
public class AddPartnerCollegeResponse {
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
