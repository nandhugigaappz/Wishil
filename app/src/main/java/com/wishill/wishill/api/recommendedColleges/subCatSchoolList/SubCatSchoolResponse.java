package com.wishill.wishill.api.recommendedColleges.subCatSchoolList;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nandhu on 9/10/2016.
 */
public class SubCatSchoolResponse {
    @SerializedName("success") @Expose private Integer status;
    @SerializedName("data") @Expose private List<SubCatSchoolListData> schoolList = new ArrayList<SubCatSchoolListData>();

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public List<SubCatSchoolListData> getSchoolList() {
        return schoolList;
    }

    public void setSchoolList(List<SubCatSchoolListData> schoolList) {
        this.schoolList = schoolList;
    }
}
