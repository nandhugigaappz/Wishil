package com.wishill.wishill.api.recommendedColleges.toprankingColleges;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by altoopa on 9/10/2016.
 */
public class TopRankingCollegesListData {
    @SerializedName("collegeid") @Expose private String collegeId;
    @SerializedName("collegename") @Expose private String collegeName;
    @SerializedName("college_img") @Expose private String collegeImg;
    @SerializedName("city") @Expose private String city;
    @SerializedName("followCount") @Expose private String followStatus;

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

    public String getCollegeImg() {
        return collegeImg;
    }

    public void setCollegeImg(String collegeImg) {
        this.collegeImg = collegeImg;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getFollowStatus() {
        return followStatus;
    }

    public void setFollowStatus(String followStatus) {
        this.followStatus = followStatus;
    }
}
