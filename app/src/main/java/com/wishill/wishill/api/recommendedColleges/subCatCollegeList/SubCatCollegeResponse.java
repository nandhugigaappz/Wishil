package com.wishill.wishill.api.recommendedColleges.subCatCollegeList;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nandhu on 9/10/2016.
 */
public class SubCatCollegeResponse {

    @SerializedName("success") @Expose private Integer status;
    @SerializedName("recom") @Expose private List<SubCatCollegeListData> catList = new ArrayList<SubCatCollegeListData>();

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public List<SubCatCollegeListData> getCatList() {
        return catList;
    }

    public void setCatList(List<SubCatCollegeListData> catList) {
        this.catList = catList;
    }
}
