package com.wishill.wishill.api.recommendedColleges.ScholarshipList;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by nandhu on 9/10/2016.
 */
public class ScholarshipListData {
    @SerializedName("scholarshipID") @Expose private String scholarshipId;
    @SerializedName("title") @Expose private String scholarshipTitle;
    @SerializedName("category") @Expose private String category;
    @SerializedName("award") @Expose private String award;
    @SerializedName("mob") @Expose private String mobile;
    @SerializedName("open_on") @Expose private String open;
    @SerializedName("closed") @Expose private String close;

/*   "scholarshipID": "85",
            "title": "Made Easy National Scholarship Test 2019",
            "award": "INRUp to 100% tuition fee rebate for Made Easy Coaching Classes",
            "category": "International",
            "scholar_type": "45",
            "open_on": "2018-09-14",
            "closed": "2019-02-25",
            "available": "Multiple",
            "mob": "011-45124612"*/

    public String getScholarshipId() {
        return scholarshipId;
    }

    public void setScholarshipId(String scholarshipId) {
        this.scholarshipId = scholarshipId;
    }

    public String getScholarshipTitle() {
        return scholarshipTitle;
    }

    public void setScholarshipTitle(String scholarshipTitle) {
        this.scholarshipTitle = scholarshipTitle;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getAward() {
        return award;
    }

    public void setAward(String award) {
        this.award = award;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getOpen() {
        return open;
    }

    public void setOpen(String open) {
        this.open = open;
    }

    public String getClose() {
        return close;
    }

    public void setClose(String close) {
        this.close = close;
    }
}
