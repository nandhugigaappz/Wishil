package com.wishill.wishill.api.recommendedColleges.schoolDetails;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by nandhu on 9/10/2016.
 */
public class SchoolDetailsData {
    @SerializedName("schoolImgpath") @Expose private String schoolImgPath;
    @SerializedName("logoImgpath") @Expose private String logoImgPath;
    @SerializedName("wishCount") @Expose private String wishCount;
    @SerializedName("followCount") @Expose private String followCount;
    @SerializedName("detailUrl") @Expose private String detailUrl;
    @SerializedName("basicDetail") @Expose private SchoolBasicDetailsData schoolBasicDetailsData;

    public String getDetailUrl() {
        return detailUrl;
    }

    public void setDetailUrl(String detailUrl) {
        this.detailUrl = detailUrl;
    }

    public String getSchoolImgPath() {
        return schoolImgPath;
    }

    public void setSchoolImgPath(String schoolImgPath) {
        this.schoolImgPath = schoolImgPath;
    }

    public String getLogoImgPath() {
        return logoImgPath;
    }

    public void setLogoImgPath(String logoImgPath) {
        this.logoImgPath = logoImgPath;
    }

    public String getWishCount() {
        return wishCount;
    }

    public void setWishCount(String wishCount) {
        this.wishCount = wishCount;
    }

    public String getFollowCount() {
        return followCount;
    }

    public void setFollowCount(String followCount) {
        this.followCount = followCount;
    }

    public SchoolBasicDetailsData getSchoolBasicDetailsData() {
        return schoolBasicDetailsData;
    }

    public void setSchoolBasicDetailsData(SchoolBasicDetailsData schoolBasicDetailsData) {
        this.schoolBasicDetailsData = schoolBasicDetailsData;
    }
}
