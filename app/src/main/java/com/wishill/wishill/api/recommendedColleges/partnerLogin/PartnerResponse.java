package com.wishill.wishill.api.recommendedColleges.partnerLogin;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by altoopa on 9/10/2016.
 */
public class PartnerResponse {
    @SerializedName("success") @Expose private String success;
    @SerializedName("message") @Expose private String message;
    @SerializedName("data") @Expose private PartnerDetailsData data;

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

    public PartnerDetailsData getData() {
        return data;
    }

    public void setData(PartnerDetailsData data) {
        this.data = data;
    }
}
