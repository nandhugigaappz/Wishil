package com.wishill.wishill.api.recommendedColleges.subCatSchoolList;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by nandhu on 9/10/2016.
 */
public class SubCatSchoolListData {
    @SerializedName("school_id") @Expose private String schoolId;
    @SerializedName("school_name") @Expose private String schoolName;
    @SerializedName("school_img") @Expose private String schoolImg;
    @SerializedName("e_year") @Expose private String schoolYear;

    public String getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(String schoolId) {
        this.schoolId = schoolId;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public String getSchoolImg() {
        return schoolImg;
    }

    public void setSchoolImg(String schoolImg) {
        this.schoolImg = schoolImg;
    }

    public String getSchoolYear() {
        return schoolYear;
    }

    public void setSchoolYear(String schoolYear) {
        this.schoolYear = schoolYear;
    }
}
