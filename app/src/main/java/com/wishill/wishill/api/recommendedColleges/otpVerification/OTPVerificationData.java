package com.wishill.wishill.api.recommendedColleges.otpVerification;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by altoopa on 9/10/2016.
 */
public class OTPVerificationData {
    @SerializedName("partnerid") @Expose private String partnerId;

    public String getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(String partnerId) {
        this.partnerId = partnerId;
    }
}
