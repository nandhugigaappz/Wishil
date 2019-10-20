package com.wishill.wishill.api.recommendedColleges.trendingScholerships;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by nandhu on 9/10/2016.
 */
public class TrendingScholarshipsListData {
    @SerializedName("scholarshipid") @Expose private String scholarshipid;
    @SerializedName("scholarshipname") @Expose private String scholarshipname;
    @SerializedName("scholarshipcategory") @Expose private String scholarshipcategory;
    @SerializedName("award") @Expose private String award;
    @SerializedName("location") @Expose private String location;

    public String getScholarshipid() {
        return scholarshipid;
    }

    public void setScholarshipid(String scholarshipid) {
        this.scholarshipid = scholarshipid;
    }

    public String getScholarshipname() {
        return scholarshipname;
    }

    public void setScholarshipname(String scholarshipname) {
        this.scholarshipname = scholarshipname;
    }

    public String getScholarshipcategory() {
        return scholarshipcategory;
    }

    public void setScholarshipcategory(String scholarshipcategory) {
        this.scholarshipcategory = scholarshipcategory;
    }

    public String getAward() {
        return award;
    }

    public void setAward(String award) {
        this.award = award;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
