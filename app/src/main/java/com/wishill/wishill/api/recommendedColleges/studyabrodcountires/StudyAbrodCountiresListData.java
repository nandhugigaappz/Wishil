package com.wishill.wishill.api.recommendedColleges.studyabrodcountires;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by altoopa on 9/10/2016.
 */
public class StudyAbrodCountiresListData {
    @SerializedName("countryID") @Expose private String countryID;
    @SerializedName("countryName") @Expose private String countryName;
    @SerializedName("categoryImg") @Expose private String categoryImg;

    public String getCountryID() {
        return countryID;
    }

    public void setCountryID(String countryID) {
        this.countryID = countryID;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getCategoryImg() {
        return categoryImg;
    }

    public void setCategoryImg(String categoryImg) {
        this.categoryImg = categoryImg;
    }
}
