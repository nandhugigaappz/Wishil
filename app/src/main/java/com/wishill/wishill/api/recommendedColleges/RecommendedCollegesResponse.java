package com.wishill.wishill.api.recommendedColleges;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nandhu on 9/10/2016.
 */
public class RecommendedCollegesResponse {
    @SerializedName("recom") @Expose private List<RecommendedCollegesListData> recommendedCollegeList = new ArrayList<RecommendedCollegesListData>();

    public List<RecommendedCollegesListData> getRecommendedCollegeList() {
        return recommendedCollegeList;
    }

    public void setRecommendedCollegeList(List<RecommendedCollegesListData> recommendedCollegeList) {
        this.recommendedCollegeList = recommendedCollegeList;
    }
}
