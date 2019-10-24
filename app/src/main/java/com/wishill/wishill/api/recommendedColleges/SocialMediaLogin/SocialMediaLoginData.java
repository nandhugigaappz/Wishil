package com.wishill.wishill.api.recommendedColleges.SocialMediaLogin;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by altoopa on 9/10/2016.
 */
public class SocialMediaLoginData {
    @SerializedName("userID") @Expose private String userID;
    @SerializedName("shareCode") @Expose private String shareCode;
    @SerializedName("referralCode") @Expose private String referral;

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getShareCode() {
        return shareCode;
    }

    public void setShareCode(String shareCode) {
        this.shareCode = shareCode;
    }

    public String getReferral() {
        return referral;
    }

    public void setReferral(String referral) {
        this.referral = referral;
    }
}
