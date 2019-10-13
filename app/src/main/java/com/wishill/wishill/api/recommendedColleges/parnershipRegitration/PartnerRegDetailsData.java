package com.wishill.wishill.api.recommendedColleges.parnershipRegitration;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by altoopa on 9/10/2016.
 */
public class PartnerRegDetailsData {
    @SerializedName("userID") @Expose private String userID;
    @SerializedName("otpVerify") @Expose private String otpVerify;

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getOtpVerify() {
        return otpVerify;
    }

    public void setOtpVerify(String otpVerify) {
        this.otpVerify = otpVerify;
    }
}
