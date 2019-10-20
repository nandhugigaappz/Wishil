package com.wishill.wishill.api.recommendedColleges.getProfile;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by nandhu on 9/10/2016.
 */
public class ProfileResponse {
    @SerializedName("profileUrl") @Expose private String profileUrl;
    @SerializedName("data") @Expose private ProfileDetailsData profileData;

    public String getProfileUrl() {
        return profileUrl;
    }

    public void setProfileUrl(String profileUrl) {
        this.profileUrl = profileUrl;
    }

    public ProfileDetailsData getProfileData() {
        return profileData;
    }

    public void setProfileData(ProfileDetailsData profileData) {
        this.profileData = profileData;
    }
}
