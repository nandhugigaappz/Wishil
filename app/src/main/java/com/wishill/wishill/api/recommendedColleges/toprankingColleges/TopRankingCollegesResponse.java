package com.wishill.wishill.api.recommendedColleges.toprankingColleges;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nandhu on 9/10/2016.
 */
public class TopRankingCollegesResponse {
    @SerializedName("recom") @Expose private List<TopRankingCollegesListData> topRankingCollegeList = new ArrayList<TopRankingCollegesListData>();

    public List<TopRankingCollegesListData> getTopRankingCollegeList() {
        return topRankingCollegeList;
    }

    public void setTopRankingCollegeList(List<TopRankingCollegesListData> topRankingCollegeList) {
        this.topRankingCollegeList = topRankingCollegeList;
    }
}
