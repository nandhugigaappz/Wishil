package com.wishill.wishill.api.recommendedColleges.getCollegeVideo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nandhu on 9/10/2016.
 */
public class CollegeVideoResponse {
    @SerializedName("success") @Expose private String status;
    @SerializedName("recom") @Expose private List<CollegeVideoListData> catList = new ArrayList<CollegeVideoListData>();

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<CollegeVideoListData> getCatList() {
        return catList;
    }

    public void setCatList(List<CollegeVideoListData> catList) {
        this.catList = catList;
    }
}
