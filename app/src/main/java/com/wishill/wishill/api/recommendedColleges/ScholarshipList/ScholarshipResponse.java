package com.wishill.wishill.api.recommendedColleges.ScholarshipList;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nandhu on 9/10/2016.
 */
public class ScholarshipResponse {

    @SerializedName("success") @Expose private Integer status;
    @SerializedName("recom") @Expose private List<ScholarshipListData> scholarshipList = new ArrayList<ScholarshipListData>();

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public List<ScholarshipListData> getScholarshipList() {
        return scholarshipList;
    }

    public void setScholarshipList(List<ScholarshipListData> scholarshipList) {
        this.scholarshipList = scholarshipList;
    }
}
