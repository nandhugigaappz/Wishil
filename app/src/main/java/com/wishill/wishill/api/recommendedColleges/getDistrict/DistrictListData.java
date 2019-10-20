package com.wishill.wishill.api.recommendedColleges.getDistrict;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by nandhu on 9/10/2016.
 */
public class DistrictListData {
    @SerializedName("city_id") @Expose private String cityId;
    @SerializedName("city") @Expose private String city;

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
