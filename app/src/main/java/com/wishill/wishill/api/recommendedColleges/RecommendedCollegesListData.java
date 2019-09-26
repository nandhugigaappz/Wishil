package com.wishill.wishill.api.recommendedColleges;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by altoopa on 9/10/2016.
 */
public class RecommendedCollegesListData {
    @SerializedName("collegeid") @Expose private String collegeId;
    @SerializedName("collegename") @Expose private String collegeName;
    @SerializedName("college_img") @Expose private String collegeImage;
    @SerializedName("city") @Expose private String city;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCollegeId() {
        return collegeId;
    }

    public void setCollegeId(String collegeId) {
        this.collegeId = collegeId;
    }

    public String getCollegeName() {
        return collegeName;
    }

    public void setCollegeName(String collegeName) {
        this.collegeName = collegeName;
    }

    public String getCollegeImage() {
        return collegeImage;
    }

    public void setCollegeImage(String collegeImage) {
        this.collegeImage = collegeImage;
    }
}
