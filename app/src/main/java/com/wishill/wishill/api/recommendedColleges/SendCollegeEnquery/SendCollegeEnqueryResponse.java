package com.wishill.wishill.api.recommendedColleges.SendCollegeEnquery;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by altoopa on 9/10/2016.
 */
public class SendCollegeEnqueryResponse {
    @SerializedName("success") @Expose private Integer success;
    @SerializedName("message") @Expose private String message;

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
