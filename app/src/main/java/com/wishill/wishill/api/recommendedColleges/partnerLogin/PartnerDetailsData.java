package com.wishill.wishill.api.recommendedColleges.partnerLogin;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by altoopa on 9/10/2016.
 */
public class PartnerDetailsData {
    @SerializedName("userID") @Expose private String userId;
    @SerializedName("otpVerify") @Expose private String otpVerify;
    @SerializedName("user_mob") @Expose private String mobileNumber;
    @SerializedName("partnertypeID") @Expose private String partnertypeID;

    public String getPartnertypeID() {
        return partnertypeID;
    }

    public void setPartnertypeID(String partnertypeID) {
        this.partnertypeID = partnertypeID;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getOtpVerify() {
        return otpVerify;
    }

    public void setOtpVerify(String otpVerify) {
        this.otpVerify = otpVerify;
    }


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
