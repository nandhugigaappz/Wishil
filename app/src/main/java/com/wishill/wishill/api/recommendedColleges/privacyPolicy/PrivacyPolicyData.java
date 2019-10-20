package com.wishill.wishill.api.recommendedColleges.privacyPolicy;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by nandhu on 9/10/2016.
 */
public class PrivacyPolicyData {
    @SerializedName("head") @Expose private String head;
    @SerializedName("privacy1") @Expose private String privacy;

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public String getPrivacy() {
        return privacy;
    }

    public void setPrivacy(String privacy) {
        this.privacy = privacy;
    }
}
