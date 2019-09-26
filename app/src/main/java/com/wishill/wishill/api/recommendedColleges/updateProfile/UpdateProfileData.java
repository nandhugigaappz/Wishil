package com.wishill.wishill.api.recommendedColleges.updateProfile;

/*
 * Created by nithinp on 20-02-2018.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UpdateProfileData {
    @SerializedName("cityID") @Expose private String cityID;
    @SerializedName("cityName") @Expose private String cityName;

    public String getCityID() {
        return cityID;
    }

    public void setCityID(String cityID) {
        this.cityID = cityID;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }
}
