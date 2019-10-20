package com.wishill.wishill.api.recommendedColleges.studyabrodcountires;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nandhu on 9/10/2016.
 */
public class StudyAbrodCountiresResponse {
    @SerializedName("success") @Expose private Integer status;
    @SerializedName("recom") @Expose private List<StudyAbrodCountiresListData> catList = new ArrayList<StudyAbrodCountiresListData>();

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public List<StudyAbrodCountiresListData> getCatList() {
        return catList;
    }

    public void setCatList(List<StudyAbrodCountiresListData> catList) {
        this.catList = catList;
    }
}
