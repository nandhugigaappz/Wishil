package com.wishill.wishill.api.recommendedColleges.getDistrict;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nandhu on 9/10/2016.
 */
public class DistrictListResponse {
    @SerializedName("recom") @Expose private List<DistrictListData> catList = new ArrayList<DistrictListData>();

    public List<DistrictListData> getCatList() {
        return catList;
    }

    public void setCatList(List<DistrictListData> catList) {
        this.catList = catList;
    }
}
