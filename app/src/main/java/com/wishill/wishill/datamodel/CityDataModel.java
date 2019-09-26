package com.wishill.wishill.datamodel;

/*
 * Created by nithinp on 11-04-2018.
 */

import com.google.gson.annotations.Expose;

public class CityDataModel {
    private String cityId;
    @Expose private String cityName;

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }
}
